package bg.softuni.entities.football;

import jakarta.persistence.*;

@Entity
@Table(name = "town")
public class Towns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @ManyToOne(optional = false)
    private Country country;

    public Towns() {
    }
}
