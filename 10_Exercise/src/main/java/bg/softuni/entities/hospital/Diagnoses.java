package bg.softuni.entities.hospital;

import jakarta.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnoses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comments;

    public Diagnoses() {
    }
}
