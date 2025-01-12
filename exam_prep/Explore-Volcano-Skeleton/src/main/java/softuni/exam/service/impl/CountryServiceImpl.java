package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRIES_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository,
                              ModelMapper modelMapper, Gson gson,
                              ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        CountryImportDTO[] countryImportDTOS = this.gson
                .fromJson(readCountriesFromFile(), CountryImportDTO[].class);
        for (CountryImportDTO countryImportDTO : countryImportDTOS) {
            if (this.countryRepository.findByName(countryImportDTO.getName()).isPresent() ||
                !this.validationUtil.isValid(countryImportDTO)) {
                sb.append("Invalid country").append(System.lineSeparator());
                continue;
            }

            this.countryRepository.saveAndFlush(this.modelMapper
                    .map(countryImportDTO, Country.class));
            sb.append(String.format("Successfully imported country %s - %s",
                    countryImportDTO.getName(), countryImportDTO.getCapital()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }
}
