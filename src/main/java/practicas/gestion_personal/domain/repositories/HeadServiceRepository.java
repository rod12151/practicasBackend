package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;
import practicas.gestion_personal.domain.entities.ServiceEntity;

import java.util.List;
import java.util.Optional;

public interface HeadServiceRepository extends JpaRepository<HeadServiceEntity,Long> {
    List<HeadServiceEntity> findByServiceAndStatusOrderByFinishDateDesc(ServiceEntity service, Boolean status);

    List<HeadServiceEntity> findAllByStatusOrderByIdHeadService(Boolean status);


    Optional<HeadServiceEntity> findByServiceCodeAndStatusAndUserDni(String serviceCode, Boolean status, String userDni);


}
