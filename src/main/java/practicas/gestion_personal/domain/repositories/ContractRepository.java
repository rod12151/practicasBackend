package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import practicas.gestion_personal.domain.entities.ContractEntity;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;


public interface ContractRepository extends JpaRepository<ContractEntity,Long> {
    Set<ContractEntity> findAllByUser_Dni(String userDni);

    Set<ContractEntity> findByLaborRegime_Code(String laborRegimeCode);
    Set<ContractEntity> findByWorkCondition_Code(String workConditionCode);
    Set<ContractEntity> findAllByStartDateIsAfter(LocalDate startDate);
    Set<ContractEntity> findAllByFinishDateIsBefore(LocalDate startDate);
    Set<ContractEntity> findAllByStartDateIsAfterAndFinishDateIsBeforeOrderByStartDate(LocalDate startDate, LocalDate finishDate);

    List<ContractEntity> findByUser_DniAndStatusOrderByIdContractDesc(String userDni, boolean status);

}
