package bg.softuni.entities.university;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teacher")
public class Teacher extends Person {

    @Basic
    private String email;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @OneToMany
    private List<Course> courseList;

    public Teacher() {
        super();
    }
}
