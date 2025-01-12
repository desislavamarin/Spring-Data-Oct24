package bg.softuni.entities.dtos;

import bg.softuni.entities.User;

public class UserPublicInfoDTO {

    private String firstName;
    private String lastName;
    private int age;

    private UserPublicInfoDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
    }
}
