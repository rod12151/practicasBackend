package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long> {
    Optional<ServiceEntity> findByCode(String code);
}