package sofuni.exam.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.DiscovererImportDTO;
import sofuni.exam.models.entity.Discoverer;
import sofuni.exam.repository.DiscovererRepository;
import sofuni.exam.service.DiscovererService;
import sofuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DiscovererServiceImpl implements DiscovererService {

    private static final String DISCOVERER_PATH = "src/main/resources/files/json/discoverers.json";

    private final DiscovererRepository discovererRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;


    @Autowired
    public DiscovererServiceImpl(DiscovererRepository discovererRepository,
                                 ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.discovererRepository = discovererRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.discovererRepository.count() > 0;
    }

    @Override
    public String readDiscovererFileContent() throws IOException {
        return Files.readString(Path.of(DISCOVERER_PATH));
    }

    @Override
    public String importDiscoverers() throws IOException {
        StringBuilder sb = new StringBuilder();
        DiscovererImportDTO[] discovererImportDTOS = this.gson
                .fromJson(readDiscovererFileContent(), DiscovererImportDTO[].class);

        for (DiscovererImportDTO discovererImportDTO : discovererImportDTOS) {
            if (this.discovererRepository.findByFirstName(discovererImportDTO.getFirstName()).isPresent() ||
                    this.discovererRepository.findByLastName(discovererImportDTO.getLastName()).isPresent() ||
                    !this.validationUtil.isValid(discovererImportDTO)) {
                sb.append("Invalid discoverer").append(System.lineSeparator());
                continue;
            }

            this.discovererRepository.saveAndFlush(this.modelMapper
                    .map(discovererImportDTO, Discoverer.class));
            sb.append(String.format("Successfully imported discoverer %s %s",
                            discovererImportDTO.getFirstName(), discovererImportDTO.getLastName()))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
