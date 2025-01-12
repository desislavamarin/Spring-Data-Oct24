package bg.softuni.entities.university;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    private String name;

    @Basic
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private Teacher teacher;

    @ManyToMany
    @JoinTable
            (name = "mapping_table_name",
            joinColumns = @JoinColumn(name = "course_to_mapping"),
            inverseJoinColumns = @JoinColumn(name = "student_to_mapping"))
    private Set<Student> students;

    @Basic
    private int credits;
}
