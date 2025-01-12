package bg.softuni.gamestore.data.repositories;

import bg.softuni.gamestore.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByTitle(String title);
}
