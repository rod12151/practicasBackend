package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.ServiceEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
    Optional<ServiceEntity> findByCode(String code);
    Set<ServiceEntity> findByHeadAssigment(Boolean headAssigment);
    Set<ServiceEntity> findByNameContains(String name);

}
