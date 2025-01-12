package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistImportRootDTO {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistImportDTO> volcanologistImportDTO;

    public List<VolcanologistImportDTO> getVolcanologistImportDTO() {
        return volcanologistImportDTO;
    }

    public void setVolcanologistImportDTO(List<VolcanologistImportDTO> volcanologistImportDTO) {
        this.volcanologistImportDTO = volcanologistImportDTO;
    }
}
