package bg.softuni.gamestore.service.dtos;

import jakarta.validation.constraints.Pattern;

public class UserCreateDTO {

    @Pattern(regexp = "/^((\\w+)|(\\w+.\\w+))@\\w+\\.\\w+$", message = "Invalid email")
    private String email;
    @Pattern(regexp = "/^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = "Invalid password")
    private String password;
    @Pattern(regexp = "", message = "Invalid confirm password")
    private String confirmPassword;
    private String fullName;

    public UserCreateDTO() {}

    public UserCreateDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
