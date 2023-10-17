package practicas.gestion_personal.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
