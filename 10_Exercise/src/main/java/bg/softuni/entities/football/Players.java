package bg.softuni.entities.football;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Players {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    private String name;

    @Column(name = "squad_number", nullable = false)
    private int squadNumber;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Position position;

    @Column(name = "is_injured", nullable = false)
    private boolean isInjured;

    public Players() {
    }
}
