package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByDni(String dni);
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findByNameContainsOrLastNameContaining(String name, String lastName);
    List<UserEntity> findAllByStatusIsTrue();


}
