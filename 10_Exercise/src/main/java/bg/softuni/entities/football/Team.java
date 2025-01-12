package bg.softuni.entities.football;

import jakarta.persistence.*;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String logo;

    @Column(length = 3)
    private String initials;

    @ManyToOne
    private Color primaryKitColor;

    @ManyToOne
    private Color secondaryKitColor;

    @ManyToOne
    private Towns towns;

    @Basic
    private double budget;

    public Team() {
    }
}
