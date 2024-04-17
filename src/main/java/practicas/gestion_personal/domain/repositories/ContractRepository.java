package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practicas.gestion_personal.domain.entities.ContractEntity;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface ContractRepository extends JpaRepository<ContractEntity,Long> {
    Set<ContractEntity> findAllByUserDni(String userDni);

    Set<ContractEntity> findByLaborRegimeCode(String laborRegimeCode);
    Set<ContractEntity> findByWorkConditionCode(String workConditionCode);
    Set<ContractEntity> findAllByStartDateIsAfter(LocalDate startDate);
    Set<ContractEntity> findAllByFinishDateIsBeforeAndStatusIsTrue(LocalDate startDate);
    Set<ContractEntity> findAllByStartDateIsAfterAndFinishDateIsBeforeOrderByStartDate(LocalDate startDate, LocalDate finishDate);

    List<ContractEntity> findByUserDniAndStatusOrderByIdContractDesc(String userDni, boolean status);

    @Query("SELECT c FROM contrato c join usuario u on " +
            " c.user.idUser=u.idUser  where c.user.dni like %:dni% order by c.status asc "
    )
    Set<ContractEntity> selectIfDniContains(@Param("dni") String dni);

    @Query("SELECT c FROM contrato c join regimenLaboral rl on " +
            " c.laborRegime.code=rl.code  where c.laborRegime.code like %:code% order by c.status asc "
    )
    Set<ContractEntity> LaborRegimeContains(@Param("code") String code);

    @Query("SELECT c FROM contrato c join condicionLaboral cl on " +
            " c.workCondition.code=cl.code  where c.workCondition.code like %:code% order by c.status asc "
    )
    Set<ContractEntity> WorkConditionsContains(@Param("code") String code);

    /*metodos para extraer datos numericos*/
    //cuentra la cantidad de contratos asignados a un servicio
    @Query("SELECT count (c.idContract) FROM contrato c inner join usuarioServicio us on " +
            "c.user.idUser = us.user.idUser " +
            "WHERE (c.status=true and us.status=true) and us.service.code=:code")
    Integer countContractForService(@Param("code") String code);


    //cuentra la cantidad de contratos asignados a un servicio y que pertenescan a un regimen
    @Query("SELECT count (c.idContract) FROM contrato c inner join usuarioServicio us on " +
            "c.user.idUser = us.user.idUser " +
            "WHERE (c.status=true and us.status=true) and (c.laborRegime.code=:codeRl and us.service.code=:codeS) ")
    Integer countContractForRegime(@Param("codeRl") String codeRl,@Param("codeS") String codeS);
    //cuentra la cantidad de contratos asignados a un servicio y que pertenescan a un regimen
    @Query("SELECT count (c.idContract) FROM contrato c inner join usuarioServicio us on " +
            "c.user.idUser = us.user.idUser " +
            "WHERE (c.status=true and us.status=true) and (c.workCondition.code=:codeWc and us.service.code=:codeS) ")
    Integer countContractForWorkCondition(@Param("codeWc") String codeWc,@Param("codeS") String codeS);
/*
    @Query("SELECT count (c.idContract) FROM contrato c inner join usuarioServicio us on " +
            "c.user.idUser = us.user.idUser " +
            "WHERE (c.status=true and us.status=true) and c.workCondition.code=:code")
    Integer countContractForWorkCondition(@Param("code") String code);
    */


}
