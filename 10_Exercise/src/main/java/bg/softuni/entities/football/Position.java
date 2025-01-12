package bg.softuni.entities.football;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Enumerated(EnumType.STRING)
    private PositionEnum id;

    @Basic
    private String description;

    public Position() {
    }

    public Position(PositionEnum id, String description) {
        this.id = id;
        this.description = description;
    }

    public PositionEnum getId() {
        return id;
    }

    public void setId(PositionEnum id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
