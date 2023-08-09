package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.AssignmentUserServiceEntity;

import java.util.List;

public interface AssignmentUserServiceRepository extends JpaRepository<AssignmentUserServiceEntity,Long> {
    List<AssignmentUserServiceEntity> findByService_CodeAndStatus(String service_code, boolean status);
    List<AssignmentUserServiceEntity> findByStatusOrderByService(boolean status);
    List<AssignmentUserServiceEntity> findByUser_DniAndService_CodeAndStatus(String user_dni, String service_code, boolean status);
    List<AssignmentUserServiceEntity> findByUser_DniAndStatusOrderByFinishDateDesc(String user_dni, boolean status);


}
