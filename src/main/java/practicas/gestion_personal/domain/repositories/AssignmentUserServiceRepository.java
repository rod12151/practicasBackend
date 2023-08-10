package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.AssignmentUserServiceEntity;

import java.util.List;

public interface AssignmentUserServiceRepository extends JpaRepository<AssignmentUserServiceEntity,Long> {
    List<AssignmentUserServiceEntity> findByServiceCodeAndStatus(String serviceCode, boolean status);
    List<AssignmentUserServiceEntity> findByStatusOrderByService(boolean status);
    List<AssignmentUserServiceEntity> findByUserDniAndServiceCodeAndStatus(String userDni, String serviceCode, boolean status);
    List<AssignmentUserServiceEntity> findByUser_DniAndStatusOrderByFinishDateDesc(String userDni, boolean status);


}
