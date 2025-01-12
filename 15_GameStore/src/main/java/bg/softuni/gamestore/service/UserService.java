package bg.softuni.gamestore.service;

import bg.softuni.gamestore.data.entities.User;
import bg.softuni.gamestore.service.dtos.UserCreateDTO;
import bg.softuni.gamestore.service.dtos.UserLoginDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String registerUser(UserCreateDTO userCreateDTO);

    String loginUser(UserLoginDTO userLoginDTO);

    User getUser();

    boolean isLoggedIn();

    boolean isAdmin();

    String logout();
}
