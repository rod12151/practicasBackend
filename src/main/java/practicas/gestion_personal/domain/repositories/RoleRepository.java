package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByName(String name);
}
