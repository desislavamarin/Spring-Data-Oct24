package bg.softuni.entities.football;

import jakarta.persistence.*;

@Entity
@Table(name = "color")
public class Color {

    @Id
    private int id;

    @Basic
    private String name;

    public Color() {
    }
}
