package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;

import java.util.Optional;

public interface LaborRegimeRepository extends JpaRepository<LaborRegimeEntity,Long> {
      Optional<LaborRegimeEntity> findByCode(String code);
}
