package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;

import java.util.List;

import java.util.Optional;

public interface LaborRegimeRepository extends JpaRepository<LaborRegimeEntity,Long> {
      Optional<LaborRegimeEntity> findByCode(String code);
      List<LaborRegimeEntity> findByNameContains(String name);

      @Query("select distinct rl.code, rl.name as nombre from regimenLaboral rl")
     List<String[]>  codeRegimen();
}
