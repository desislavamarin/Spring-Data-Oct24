package bg.softuni.gamestore.controller;

import bg.softuni.gamestore.service.GameService;
import bg.softuni.gamestore.service.UserService;
import bg.softuni.gamestore.service.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public CommandLineRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while (!(line = reader.readLine()).equals("stop")) {
            String[] tokens = line.split("\\|");
            String command = "";

            switch (tokens[0]) {
                case "RegisterUser":
                    command = this.userService.registerUser(new UserCreateDTO(
                            tokens[1], tokens[2], tokens[3], tokens[4]
                    ));
                    break;
                case "LoginUser":
                    command = this.userService.loginUser(new UserLoginDTO(
                            tokens[1], tokens[2]
                    ));
                    break;
                case "Logout":
                    command = this.userService.logout();
                    break;
                case "AddGame":
                    command = this.gameService.addGame(new GameCreateDTO(
                            tokens[1], new BigDecimal(tokens[2]), Double.parseDouble(tokens[3]),
                            tokens[4], tokens[5], tokens[6], LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                    break;
                case "EditGame":
                    GameEditDTO gameEditDTO = new GameEditDTO();
                    gameEditDTO.setId(Integer.parseInt(tokens[1]));

                    Arrays.stream(tokens).skip(2).forEach(value -> {
                        String[] split = value.split("=");
                        String field = split[0];
                        if ("price".equals(field)) {
                            gameEditDTO.setPrice(new BigDecimal(split[1]));
                        } else if ("size".equals(field)) {
                            gameEditDTO.setSize(Double.parseDouble(split[1]));
                        }
                    });

                    command = this.gameService.editGame(gameEditDTO);
                    break;
                case "DeleteGame":
                    command = this.gameService.deleteGame(Integer.parseInt(tokens[1]));
                    break;
                case "AllGames":
                    command = this.gameService.getAllGames()
                            .stream().map(GameViewDTO::toString)
                            .collect(Collectors.joining("\n"));
                    break;
                case "DetailGame":
                    command = this.gameService.getDetailGame(tokens[1]).toString();
                    break;
                case "OwnedGames":
                    command = this.gameService.getOwnedGames();
                    break;
            }

            System.out.println(command);
        }
    }
}
