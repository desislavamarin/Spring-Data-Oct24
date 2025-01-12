package bg.softuni.gamestore.service.impls;

import bg.softuni.gamestore.data.entities.Game;
import bg.softuni.gamestore.data.entities.User;
import bg.softuni.gamestore.data.repositories.GameRepository;
import bg.softuni.gamestore.service.GameService;
import bg.softuni.gamestore.service.UserService;
import bg.softuni.gamestore.service.dtos.*;
import bg.softuni.gamestore.service.utils.ValidatorUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpls implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtils;
    private final UserService userService;

    @Autowired
    public GameServiceImpls(GameRepository gameRepository, ModelMapper modelMapper, ValidatorUtil validatorUtils, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validatorUtils = validatorUtils;
        this.userService = userService;
    }

    @Override
    public String addGame(GameCreateDTO gameCreateDTO) {
        if (!this.validatorUtils.isValid(gameCreateDTO)) {
            return this.validatorUtils.validate(gameCreateDTO)
                    .stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        Game game = this.modelMapper.map(gameCreateDTO, Game.class);
        this.gameRepository.saveAndFlush(game);

        return String.format("Added %s%n", game.getTitle());
    }

    @Override
    public String editGame(GameEditDTO gameEditDTO) {
        if (!this.validatorUtils.isValid(gameEditDTO)) {
            this.validatorUtils.validate(gameEditDTO)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        Optional<Game> optionalGame = this.gameRepository.findById(gameEditDTO.getId());
        if (optionalGame.isEmpty()) {
            return "No such game found";
        }

        Game game = optionalGame.get();
        if (gameEditDTO.getPrice() != null) {
            game.setPrice(gameEditDTO.getPrice());
        }
        if (gameEditDTO.getSize() != null) {
            game.setSize(gameEditDTO.getSize());
        }

        this.gameRepository.saveAndFlush(game);

        return String.format("Edited %s%n", game.getTitle());
    }

    @Override
    public String deleteGame(int id) {
        Optional<Game> game = this.gameRepository.findById(id);
        if (game.isEmpty()) {
            return "No such game found";
        }

        this.gameRepository.delete(game.get());
        return String.format("Deleted %s", game.get().getTitle());
    }

    @Override
    public Set<GameViewDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GameViewDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public GameDetailDTO getDetailGame(String title) {
        Optional<Game> game = this.gameRepository.findByTitle(title);

        return game.map(value -> this.modelMapper.map(value, GameDetailDTO.class)).orElse(new GameDetailDTO());
    }

    @Override
    public String getOwnedGames() {

        if (!this.userService.isLoggedIn()) {
            return "No logged in user";
        }

        return this.userService.getUser()
                .getGames()
                .stream()
                .map(g -> this.modelMapper
                        .map(g, GameOwnedDTO.class))
                .map(GameOwnedDTO::getTitle)
                .collect(Collectors.joining("\n"));
    }
}
