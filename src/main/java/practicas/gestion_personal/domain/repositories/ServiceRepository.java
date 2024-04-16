package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practicas.gestion_personal.domain.entities.ServiceEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
    Optional<ServiceEntity> findByCode(String code);
    Set<ServiceEntity> findByHeadAssigment(Boolean headAssigment);
    Set<ServiceEntity> findByNameContains(String name);

    @Query("select distinct u.code from servicio u")
    String[] codeServices();

}
