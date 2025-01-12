package sofuni.exam.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.PlanetImportDTO;
import sofuni.exam.models.entity.Planet;
import sofuni.exam.models.enums.Type;
import sofuni.exam.repository.PlanetRepository;
import sofuni.exam.service.PlanetService;
import sofuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlanetServiceImpl implements PlanetService {

    private static final String PLANET_PATH = "src/main/resources/files/json/planets.json";

    private final PlanetRepository planetRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public PlanetServiceImpl(PlanetRepository planetRepository,
                             ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.planetRepository = planetRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.planetRepository.count() > 0;
    }

    @Override
    public String readPlanetsFileContent() throws IOException {
        return Files.readString(Path.of(PLANET_PATH));
    }

    @Override
    public String importPlanets() throws IOException {
        StringBuilder sb = new StringBuilder();

        PlanetImportDTO[] planetImportDTOS = this.gson
                .fromJson(readPlanetsFileContent(), PlanetImportDTO[].class);

        for (PlanetImportDTO planetImportDTO : planetImportDTOS) {
            if (this.planetRepository.findByName(planetImportDTO.getName()).isPresent() ||
                    !this.validationUtil.isValid(planetImportDTO)) {
                sb.append("Invalid planet").append(System.lineSeparator());
                continue;
            }

            Planet planet = this.modelMapper.map(planetImportDTO, Planet.class);
            planet.setType(Type.valueOf(planetImportDTO.getType()));
            this.planetRepository.saveAndFlush(this.modelMapper
                    .map(planetImportDTO, Planet.class));
            sb.append(String.format("Successfully imported planet %s",
                            planetImportDTO.getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
