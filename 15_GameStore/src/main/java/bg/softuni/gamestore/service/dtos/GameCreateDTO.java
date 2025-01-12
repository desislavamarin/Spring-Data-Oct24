package bg.softuni.gamestore.service.dtos;

import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameCreateDTO {

    @Length(min = 3, max = 100, message = "Title must be between 3 and 100 symbols")
    private String title;

    @Positive(message = "Price must be positive number")
    private BigDecimal price;

    @Positive(message = "Price must be positive number")
    private double size;

    @Length(min = 11, max = 11, message = "Trailer must be 11 symbols length")
    private String trailer;

    private String imageUrl;

    @Length(min = 20, message = "Description must be minimum 20 symbols length")
    private String description;

    private LocalDate releaseDate;

    public GameCreateDTO() {
    }

    public GameCreateDTO(
            String title, BigDecimal price, double size,
            String trailer, String imageUrl,
            String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.imageUrl = imageUrl;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
