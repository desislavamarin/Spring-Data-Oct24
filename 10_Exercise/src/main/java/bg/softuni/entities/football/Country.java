package bg.softuni.entities.football;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @Enumerated(EnumType.STRING)
    private CountryIsoCodes  id;

    @Basic(optional = false)
    private String name;

    @ManyToMany
    private Set<Continent> continent;

    public Country(Set<Continent> continent) {
        this.continent = new HashSet<>();
    }
}
