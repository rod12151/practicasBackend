package practicas.gestion_personal.infraestructure.abstract_services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.api.models.response.ContractResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ContractService {
    Set<ContractResponse> findByUserDni(String dni);
    Set<ContractResponse> findByLaborRegimeCode(String code);
    Set<ContractResponse> findByWorkConditionCode(String code);
    Set<ContractResponse> findByStartDateAfter(LocalDate date);
    Set<ContractResponse> findByFinishDateBefore(LocalDate date);


    Set<ContractResponse> findByStartDateAndFinishDate(LocalDate startDate, LocalDate finishDate);
    ContractResponse createContract(ContractRequest request);
    ContractResponse updateContract(Long id,ContractRequest request);
    Map<String,Object> terminateContract(Long id);
    List<ContractResponse> listContractUser(String dni,boolean status);


}
