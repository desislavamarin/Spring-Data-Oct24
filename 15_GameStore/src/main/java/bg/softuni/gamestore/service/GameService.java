package bg.softuni.gamestore.service;

import bg.softuni.gamestore.service.dtos.*;

import java.util.Set;

public interface GameService {
    String addGame(GameCreateDTO gameCreateDTO);

    String editGame(GameEditDTO gameEditDTO);

    String deleteGame(int id);

    Set<GameViewDTO> getAllGames();

    GameDetailDTO getDetailGame(String title);

    String getOwnedGames();
}
