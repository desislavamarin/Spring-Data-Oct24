package sofuni.exam.service.Impl;

import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sofuni.exam.models.dto.MoonImportDTO;
import sofuni.exam.models.dto.MoonImportRootDTO;
import sofuni.exam.models.entity.Moon;
import sofuni.exam.models.enums.Type;
import sofuni.exam.repository.DiscovererRepository;
import sofuni.exam.repository.MoonRepository;
import sofuni.exam.repository.PlanetRepository;
import sofuni.exam.service.MoonService;
import sofuni.exam.util.ValidationUtil;
import sofuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MoonServiceImpl implements MoonService {

    private static final String MOON_PATH = "src/main/resources/files/xml/moons.xml";

    private final MoonRepository moonRepository;
    private final DiscovererRepository discovererRepository;
    private final PlanetRepository planetRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public MoonServiceImpl(MoonRepository moonRepository, DiscovererRepository discovererRepository,
                           PlanetRepository planetRepository,
                           ModelMapper modelMapper, XmlParser xmlParser,
                           ValidationUtil validationUtil) {
        this.moonRepository = moonRepository;
        this.discovererRepository = discovererRepository;
        this.planetRepository = planetRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.moonRepository.count() > 0;
    }

    @Override
    public String readMoonsFileContent() throws IOException {
        return Files.readString(Path.of(MOON_PATH));
    }

    @Override
    public String importMoons() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        MoonImportRootDTO moonImportRootDTO = this.xmlParser
                .fromFile(MOON_PATH, MoonImportRootDTO.class);
        for (MoonImportDTO moonImportDTO : moonImportRootDTO.getMoonImportDTO()) {
            if (this.moonRepository.findByName(moonImportDTO.getName()).isPresent() ||
                    !this.validationUtil.isValid(moonImportDTO)) {
                sb.append("Invalid moon").append(System.lineSeparator());
                continue;
            }

            Moon moon = this.modelMapper.map(moonImportDTO, Moon.class);
            moon.setDiscoverer(this.discovererRepository
                    .findById((long) moonImportDTO.getDiscovererId()).get());
            moon.setPlanet(this.planetRepository
                    .findById((long) moonImportDTO.getPlanetId()).get());

            this.moonRepository.saveAndFlush(moon);

            sb.append(String.format("Successfully imported moon %s",
                            moonImportDTO.getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String exportMoons() {
        StringBuilder sb = new StringBuilder();
        this.moonRepository.findByRadiusBetweenAndPlanetTypeOrderByNameAsc(700, 2000, Type.GAS_GIANT)
                .forEach(moon -> {
                    sb.append(String.format("\"***Moon %s is a natural satellite of %s and has a radius of %.2f km.\n" +
                                    "****Discovered by %s %s\n" +
                                    ". . .\"", moon.getName(), moon.getPlanet().getName(), moon.getRadius(),
                                    moon.getDiscoverer().getFirstName(), moon.getDiscoverer().getLastName()))
                            .append(System.lineSeparator());
                });
        return sb.toString();
    }
}
