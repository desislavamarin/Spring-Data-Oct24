package bg.softuni.entities.hospital;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "visitation")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private Instant date;

    @Basic
    private String comment;

    @ManyToOne
    private Patient patient;

    public Visitation() {
    }
}
