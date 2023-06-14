package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.domain.entities.ContractEntity;

import java.time.LocalDate;
import java.util.Set;

public interface ContractService {
    Set<ContractEntity> findByUserDni(String dni);
    Set<ContractEntity> findByLaborRegimeCode(String code);
    Set<ContractEntity> findByWorkConditionCode(String code);
    Set<ContractEntity> findByStartDateAfter(LocalDate date);
    Set<ContractEntity> findByFinishDateBefore(LocalDate date);


    Set<ContractEntity> findByStartDateAndFinishDate(LocalDate startDate, LocalDate finishDate);
    ContractEntity createContract(ContractRequest request);
    ContractEntity updateContract(Long id,ContractRequest request);
    void deleteContract(Long id);

}
