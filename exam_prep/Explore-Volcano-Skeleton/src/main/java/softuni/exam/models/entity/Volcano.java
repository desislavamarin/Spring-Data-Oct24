package softuni.exam.models.entity;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.enums.VolcanoType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "volcanoes")
public class Volcano extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Length(min = 2, max = 30)
    private String name;
    @Column(nullable = false)
    private int elevation;
    @Column(name = "volcano_type")
    @Enumerated(EnumType.STRING)
    private VolcanoType volcanoType;
    @Column(nullable = false, name = "is_active")
    private boolean isActive;
    @Column(name = "last_eruption")
    private LocalDate lastEruption;
    @ManyToOne
    @JoinColumn(name = "country_id" ,referencedColumnName = "id")
    private Country country;
    @OneToMany(mappedBy = "volcano", fetch = FetchType.EAGER)
    private Set<Volcanologist> volcanologists;

    public Volcano() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
