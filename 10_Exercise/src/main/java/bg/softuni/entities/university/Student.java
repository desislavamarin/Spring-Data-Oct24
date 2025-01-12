package bg.softuni.entities.university;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "student")
public class Student extends Person {

    @Column(name = "average_grade")
    private Double averageGrade;

    @Basic
    private int attendance;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    public Student() {
        super();
    }
}
