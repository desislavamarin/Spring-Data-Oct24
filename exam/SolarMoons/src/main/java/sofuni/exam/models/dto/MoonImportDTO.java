package sofuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "moon")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoonImportDTO {

    @XmlElement
    private String name;
    @XmlElement
    private String discovered;
    @XmlElement(name = "distance_from_planet")
    private Long distanceFromPlanet;
    @XmlElement
    private double radius;
    @XmlElement(name = "discoverer_id")
    private int discovererId;
    @XmlElement(name = "planet_id")
    private int planetId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscovered() {
        return discovered;
    }

    public void setDiscovered(String discovered) {
        this.discovered = discovered;
    }

    public Long getDistanceFromPlanet() {
        return distanceFromPlanet;
    }

    public void setDistanceFromPlanet(Long distanceFromPlanet) {
        this.distanceFromPlanet = distanceFromPlanet;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getDiscovererId() {
        return discovererId;
    }

    public void setDiscovererId(int discovererId) {
        this.discovererId = discovererId;
    }

    public int getPlanetId() {
        return planetId;
    }

    public void setPlanetId(int planetId) {
        this.planetId = planetId;
    }
}
