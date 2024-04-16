package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;
import practicas.gestion_personal.domain.entities.ServiceEntity;

import java.util.List;
import java.util.Optional;

public interface HeadServiceRepository extends JpaRepository<HeadServiceEntity,Long> {
    List<HeadServiceEntity> findByServiceAndStatusOrderByFinishDateDesc(ServiceEntity service, Boolean status);

    List<HeadServiceEntity> findAllByStatusOrderByIdHeadService(Boolean status);


    Optional<HeadServiceEntity> findByServiceCodeAndStatusAndUserDni(String serviceCode, Boolean status, String userDni);


    @Query("select js from jefeServicio js where js.service.code=:service and js.status=true ")
    Optional<HeadServiceEntity> buscarBossActual(@Param("service") String service);

    @Query("select js from jefeServicio js where js.user.dni=:dni and js.status=true ")
    Optional<HeadServiceEntity> findBossByDni(@Param("dni") String dni);


}
