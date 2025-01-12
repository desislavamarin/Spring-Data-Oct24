package bg.softuni.entities.hospital;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Basic
    private String address;

    @Basic
    private String email;

    @Column(name = "birth_of_date")
    private LocalDate birthDate;

    @Column(name = "picture_url")
    private String picture; //url

    @Column(name = "has_insurance")
    public boolean hasInsurance;

    @OneToMany(mappedBy = "patient")
    private List<Visitation> visitations;

    @OneToMany
    private Set<Diagnoses> diagnoses;

    @ManyToMany
    private Set<Medicament> medicaments;

    public Patient() {
        this.visitations = new ArrayList<>();
        this.diagnoses = new HashSet<>();
        this.medicaments = new HashSet<>();
    }

}
