package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanoImportDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.enums.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private static final String VOLCANO_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;
    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository,
                              CountryRepository countryRepository,
                              Gson gson, ValidationUtil validationUtil,
                              ModelMapper modelMapper) {
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANO_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();

        VolcanoImportDTO[] volcanoImportDTOS = this.gson
                .fromJson(readVolcanoesFileContent(), VolcanoImportDTO[].class);
        for (VolcanoImportDTO volcanoImportDTO : volcanoImportDTOS) {
            if (this.volcanoRepository.findByName(volcanoImportDTO.getName()).isPresent() ||
                    !this.validationUtil.isValid(volcanoImportDTO)) {
                sb.append("Invalid volcano").append(System.lineSeparator());
                continue;
            }

            Volcano volcano = this.modelMapper.map(volcanoImportDTO, Volcano.class);
            volcano.setVolcanoType(VolcanoType.valueOf(volcanoImportDTO.getVolcanoType()));
            volcano.setCountry(this.countryRepository.findById((long) volcanoImportDTO.getCountry()).get());
            this.volcanoRepository.save(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s",
                            volcano.getName(), volcano.getVolcanoType().toString()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {
        StringBuilder sb = new StringBuilder();
        this.volcanoRepository.findAllByElevationAfterAndActiveIsAndLastEruptionIsNotNullOrderByElevationDesc(3000, true)
                .forEach(v -> {
                    sb.append(String.format("Volcano: %s\n" +
                            "   *Located in: %s\n" +
                            "   **Elevation: %d\n" +
                            "   ***Last eruption on: %s\n" +
                            ". . .", v.getName(), v.getCountry(), v.getElevation(), v.getLastEruption()))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }
}