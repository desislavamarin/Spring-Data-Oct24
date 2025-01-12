package bg.softuni.gamestore.service.impls;

import bg.softuni.gamestore.data.entities.User;
import bg.softuni.gamestore.data.repositories.UserRepository;
import bg.softuni.gamestore.service.UserService;
import bg.softuni.gamestore.service.dtos.UserCreateDTO;
import bg.softuni.gamestore.service.dtos.UserLoginDTO;
import bg.softuni.gamestore.service.utils.ValidatorUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    private User user;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String registerUser(UserCreateDTO userCreateDTO) {
        if(!userCreateDTO.getPassword().equals(userCreateDTO.getConfirmPassword())){
            return "Passwords do not match";
        }

        if (validatorUtil.isValid(userCreateDTO)) {
            return validatorUtil.validate(userCreateDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        if (this.userRepository.findByEmail(userCreateDTO.getEmail()).isPresent()) {
            return "Email already exists";
        }

        User user = this.modelMapper.map(userCreateDTO, User.class);
        setRootUserAdmin(user);

        this.userRepository.saveAndFlush(user);
        return String.format("%s was registered%n", user.getFullName());
    }

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {

        Optional<User> user = this.userRepository.findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        if (user.isEmpty()) {
            return "Incorrect username / password";
        } else {
            this.user = user.get();
            return String.format("Successfully logged in %s%n", getUser().getFullName());
        }

    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public boolean isLoggedIn() {
        return this.user != null;
    }

    @Override
    public boolean isAdmin() {
        return this.isLoggedIn() && this.user.isAdmin();
    }

    @Override
    public String logout() {
        if (isLoggedIn()) {
            String output = String.format("User %s successfully logged out", user.getFullName());
            this.user = null;
            return output;
        }
        return "Cannot log out. No user was logged in.";
    }

    private void setRootUserAdmin(User user) {
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }
    }
}
