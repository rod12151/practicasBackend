package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;

import java.util.List;
import java.util.Optional;

public interface WorkConditionRepository extends JpaRepository<WorkConditionEntity,Long> {
      Optional<WorkConditionEntity> findByCode(String code);
      List<WorkConditionEntity> findByNameContains(String name);

      @Query("select distinct cl.code,cl.name as nombre from condicionLaboral cl")
      String[] codeRegimen();
}
