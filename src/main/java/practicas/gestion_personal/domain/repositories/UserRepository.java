package practicas.gestion_personal.domain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practicas.gestion_personal.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByDni(String dni);
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findByNameContainsOrLastNameContaining(String name, String lastName);
    List<UserEntity> findAllByStatusIsTrue();

    @Query("SELECT u FROM usuario u LEFT JOIN usuarioServicio us" +
            " ON u.idUser= us.user.idUser WHERE" +
            "(us.idAssignment IS NULL or us.status = false)" +
            " and u.status=true and (u.name LIKE %:filterName% or u.lastName LIKE %:filterLastName%)")
    List<UserEntity> getUsuariosNoAsignadosActivos(@Param("filterName") String filterName, @Param("filterLastName") String filterLastName );


}
