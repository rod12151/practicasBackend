package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practicas.gestion_personal.domain.entities.AssignmentUserServiceEntity;

import java.util.List;
import java.util.Set;

public interface AssignmentUserServiceRepository extends JpaRepository<AssignmentUserServiceEntity,Long> {

    List<AssignmentUserServiceEntity> findByServiceCodeAndStatus(String service_code, boolean status);
    List<AssignmentUserServiceEntity> findByStatusOrderByService(boolean status);
    List<AssignmentUserServiceEntity> findByUserDniAndStatus(String user_dni, boolean status);
    List<AssignmentUserServiceEntity> findByUserDniAndServiceCodeAndStatus(String user_dni,String code_service, boolean status);
    List<AssignmentUserServiceEntity> findByUserDniAndStatusOrderByFinishDateDesc(String user_dni, boolean status);

    @Query(" select us from usuarioServicio us " +
            " join usuario u on us.user.idUser=u.idUser"+
            " join contrato c on u.idUser= c.user.idUser"+
            " join regimenLaboral lr on  c.laborRegime.idLaborRegime=lr.idLaborRegime " +
            "where us.status =true and c.status=true and lr.name Like %:filter%")
    Set<AssignmentUserServiceEntity> filterForLaborRegime(@Param("filter") String filter);
    @Query(" select us from usuarioServicio us " +
            "  join usuario u on us.user.idUser=u.idUser"+
            "  join contrato c on u.idUser=c.user.idUser"+
            "  join condicionLaboral wk on c.workCondition.idWorkCondition=wk.idWorkCondition" +
            "  where us.status =true and c.status=true and wk.name Like %:filter%")
    Set<AssignmentUserServiceEntity> filterForWorkCondition(@Param("filter") String filter);
    @Query(" select us from usuarioServicio us " +
            " join usuario u on us.user.idUser=u.idUser"+
            " join contrato c on u.idUser=c.user.idUser"+
            " join servicio s on us.service.idService=s.idService" +
            " where us.status =true and c.status=true and s.name Like %:filter%")
    Set<AssignmentUserServiceEntity> filterForService(@Param("filter") String filter);


}
