package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;

import java.util.Optional;

public interface WorkConditionRepository extends JpaRepository<WorkConditionEntity,Long> {
      Optional<WorkConditionEntity> findByCode(String code);
}
