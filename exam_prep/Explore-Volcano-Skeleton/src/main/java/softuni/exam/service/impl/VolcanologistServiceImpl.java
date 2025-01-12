package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.VolcanologistImportDTO;
import softuni.exam.models.dto.VolcanologistImportRootDTO;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private static final String VOLCANOLOGIST_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;
    private final VolcanoRepository volcanoRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository,
                                    VolcanoRepository volcanoRepository,
                                    ModelMapper modelMapper,
                                    XmlParser xmlParser, ValidationUtil validationUtil) {
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Path.of(VOLCANOLOGIST_PATH));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        VolcanologistImportRootDTO volcanologistImportRootDTO = this.xmlParser
                .fromFile(VOLCANOLOGIST_PATH, VolcanologistImportRootDTO.class);
        for (VolcanologistImportDTO volcanologistImportDTO : volcanologistImportRootDTO.getVolcanologistImportDTO()) {
            if (this.volcanologistRepository.findByFirstNameAndLastName(volcanologistImportDTO.getFirstName(), volcanologistImportDTO.getLastName()).isPresent() ||
                    this.volcanoRepository.findById((long) volcanologistImportDTO.getExploringVolcanoId()).isEmpty() ||
                    !this.validationUtil.isValid(volcanologistImportDTO)) {
                sb.append("Invalid volcanologist").append(System.lineSeparator());
                continue;
            }

            Volcanologist volcanologist = this.modelMapper.map(volcanologistImportDTO, Volcanologist.class);
            volcanologist.setVolcano(this.volcanoRepository
                    .findById((long) volcanologistImportDTO.getExploringVolcanoId()).get());
            this.volcanologistRepository.saveAndFlush(volcanologist);

            sb.append(String.format("Successfully imported volcanologist %s %s",
                    volcanologistImportDTO.getFirstName(), volcanologistImportDTO.getLastName()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }
}