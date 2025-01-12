package sofuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.exam.models.entity.Moon;
import sofuni.exam.models.enums.Type;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoonRepository extends JpaRepository<Moon, Long>{

    Optional<Moon> findByName(String name);

    List<Moon> findByRadiusBetweenAndPlanetTypeOrderByNameAsc(double from, double to, Type type);
}
