package practicas.gestion_personal.domain.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.enums.Genero;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByDni(String dni);

    Optional<UserEntity> findByDniAndStatusIsTrue(String dni);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByNameContainsOrLastNameContaining(String name, String lastName);

    List<UserEntity> findAllByStatusIsTrue();

    @Query("select u from usuario u left Join contrato c on u.idUser = c.user.idUser and c.status=true" +
            " where ( c.user.idUser is null and u.status=true) and (u.name LIKE %:filterName% or u.lastName LIKE %:filterLastName%) ")
    List<UserEntity> getUsersWithoutContractActive(@Param("filterName") String filterName, @Param("filterLastName")String filterLastName);

    /*@Query("SELECT u FROM usuario u LEFT JOIN usuarioServicio us" +
            " ON u.idUser= us.user.idUser WHERE" +
            "(us.idAssignment IS NULL or us.status=false)" +
            " and u.status=true and (u.name LIKE %:filterName% or u.lastName LIKE %:filterLastName%)")*/
    @Query("select u from usuario u inner join contrato c on u.idUser=c.user.idUser and c.status=true " +
            "left join usuarioServicio us on u.idUser=us.user.idUser and us.status=true " +
            "where us.idAssignment is null and (u.name LIKE %:filterName% or u.lastName LIKE %:filterLastName%)")
    List<UserEntity> getUsuariosNoAsignadosActivos(@Param("filterName") String filterName, @Param("filterLastName") String filterLastName);

    @Query("select u from usuario u inner join contrato c on u.idUser=c.user.idUser and c.status=true " +
            "left join jefeServicio js on u.idUser=js.user.idUser and js.status=true " +
            "where js.idHeadService is null and (u.name LIKE %:filterName% or u.lastName LIKE %:filterLastName%)")
    List<UserEntity> getJefesNoAsignadosActivos(@Param("filterName") String filterName, @Param("filterLastName") String filterLastName);

    @Query("select count(u.idUser) from usuario u where u.genero=:gender and u.status=true")
    Integer countGender(@Param("gender") Genero gender);

    @Query("select count(u.idUser) from usuario u inner join usuarioServicio us " +
            "ON u.idUser=us.user.idUser " +
            "where u.genero=:gender and (us.status=true and us.service.code=:service)")
    Integer countGenderService(@Param("gender") Genero gender, @Param("service") String service);


}
