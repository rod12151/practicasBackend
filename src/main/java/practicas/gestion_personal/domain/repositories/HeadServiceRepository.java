package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface HeadServiceRepository extends JpaRepository<HeadServiceEntity,Long> {
    List<HeadServiceEntity> findByServiceAndStatusOrderByFinishDateDesc(ServiceEntity service, Boolean status);
    List<HeadServiceEntity> findAllByStatus(Boolean status);
    Optional<HeadServiceEntity> findByService_CodeAndStatusAndUser_Dni(String service_code, Boolean status, String user_dni);

}
