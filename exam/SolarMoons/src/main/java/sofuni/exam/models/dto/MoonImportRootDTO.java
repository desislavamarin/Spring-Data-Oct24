package sofuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "moons")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoonImportRootDTO {

    @XmlElement(name = "moon")
    private List<MoonImportDTO> moonImportDTO;

    public List<MoonImportDTO> getMoonImportDTO() {
        return moonImportDTO;
    }

    public void setMoonImportDTO(List<MoonImportDTO> moonImportDTO) {
        this.moonImportDTO = moonImportDTO;
    }
}
