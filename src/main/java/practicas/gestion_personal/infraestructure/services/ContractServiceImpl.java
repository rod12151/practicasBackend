package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.api.models.response.ContractResponse;
import practicas.gestion_personal.domain.entities.ContractEntity;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;
import practicas.gestion_personal.domain.repositories.ContractRepository;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.domain.repositories.WorkConditionRepository;
import practicas.gestion_personal.infraestructure.abstract_services.ContractService;
import practicas.gestion_personal.mapper.ContractMapping;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
@AllArgsConstructor

public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private UserRepository userRepository;

    private WorkConditionRepository workConditionRepository;
    private LaborRegimeRepository laborRegimeRepository;

    private ContractMapping contractMapping;
    @Override
    @Transactional(readOnly = true)
    public Set<ContractResponse> findByUserDni(String dni) {
        UserEntity user =userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));
        Set<ContractResponse> response=new HashSet<>();
        var contract= contractRepository.findAllByUser_Dni(dni);
        for (ContractEntity res:contract){
            ContractResponse aux =contractMapping.entityToResponse(res);
            response.add(aux);

        }
        return response;
    }
    @Override
    public Set<ContractResponse> findByLaborRegimeCode(String code) {
        LaborRegimeEntity laborRegime=laborRegimeRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("laborRegime"));
        Set<ContractResponse> response=new HashSet<>();
        var contract= contractRepository.findByLaborRegime_Code(code);
        for (ContractEntity res:contract){
            ContractResponse aux =contractMapping.entityToResponse(res);
            response.add(aux);

        }
        return response;
    }
    @Override
    public Set<ContractResponse> findByWorkConditionCode(String code) {
        WorkConditionEntity workCondition=workConditionRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("workCondition"));
        Set<ContractResponse> response=new HashSet<>();
        var contract= contractRepository.findByWorkCondition_Code(code);
        for (ContractEntity res:contract){
            ContractResponse aux =contractMapping.entityToResponse(res);
            response.add(aux);

        }
        return response;
    }
    @Override
    public Set<ContractResponse> findByStartDateAfter(LocalDate date) {
        var contract = contractRepository.findAllByStartDateIsAfter(date);
        return getContractResponses(contract);
    }
    @Override
    public Set<ContractResponse> findByFinishDateBefore(LocalDate date) {
        var contract= contractRepository.findAllByFinishDateIsBefore(date);
        return getContractResponses(contract);
    }

    @Override
    public Set<ContractResponse> findByStartDateAndFinishDate(LocalDate startDate, LocalDate finishDate) {
        var contract= contractRepository.findAllByStartDateIsAfterAndFinishDateIsBeforeOrderByStartDate(startDate,finishDate);
        return getContractResponses(contract);
    }

    @Override
    public ContractResponse createContract(ContractRequest request) {
        UserEntity userEntity = userRepository.findByDni(request.getDniUser()).orElseThrow(() -> new IdNotFoundException("User"));
        WorkConditionEntity workCondition = workConditionRepository.findByCode(request.getCodeWorkCondition()).orElseThrow(() -> new IdNotFoundException("WorkCondition"));
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(request.getCodeLaborRegime()).orElseThrow(() -> new IdNotFoundException("laborRegime"));

        List<ContractEntity> contracts = contractRepository.findByUser_DniAndStatusOrderByIdContractDesc(request.getDniUser(), true);

        if(contracts.isEmpty()) {
            ContractEntity contract = ContractEntity.builder()
                    .user(userEntity)
                    .position(request.getPosition())
                    .startDate(request.getStartDate())
                    .finishDate(request.getFinishDate())
                    .laborRegime(laborRegime)
                    .workCondition(workCondition)
                    .Salary(request.getSalary())
                    .status(true)
                    .build();
            contractRepository.save(contract);
            return contractMapping.entityToResponse(contract);
        }
        else {
            throw new UserDuplicate(request.getDniUser(), "ya tiene un contrato vigente,");
        }
    }
    @Override
    public ContractResponse updateContract(Long id, ContractRequest request) {
        ContractEntity contractUpdate = contractRepository.findById(id).orElseThrow(()->new IdNotFoundException("contract"));
        UserEntity userEntity=userRepository.findByDni(request.getDniUser()).orElseThrow(()->new IdNotFoundException("User"));
        WorkConditionEntity workCondition =workConditionRepository.findByCode(request.getCodeWorkCondition()).orElseThrow(()->new IdNotFoundException("workCondition"));
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(request.getCodeLaborRegime()).orElseThrow(()->new IdNotFoundException("laborRegime"));
        contractUpdate.setUser(userEntity);
        contractUpdate.setLaborRegime(laborRegime);
        contractUpdate.setWorkCondition(workCondition);
        contractUpdate.setPosition(request.getPosition());
        contractUpdate.setStartDate(request.getStartDate());
        contractUpdate.setFinishDate(request.getFinishDate());
        contractRepository.save(contractUpdate);

        return contractMapping.entityToResponse(contractUpdate);
    }

    @Override
    public String terminateContract(Long id) {
        ContractEntity contract = contractRepository.findById(id).orElseThrow(()->new IdNotFoundException("contract"));
        if(contract.isStatus()){
        contract.setStatus(false);
        contract.setFinishDate(LocalDate.now());
        contractRepository.save(contract);
        return " el contrato del Usuario con DNI: "+ contract.getUser().getDni()+ " se actualizó a un estado terminado";
    }
        return "el contrato del Usuario con DNI: "+ contract.getUser().getDni()+ " ya se encontraba en un estado terminado";

    }

    @Override
    public List<ContractResponse> listContractUser(String dni, boolean status) {
        UserEntity user = userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));
        List<ContractEntity> contracts = contractRepository.findByUser_DniAndStatusOrderByIdContractDesc(dni,status);
        List<ContractResponse> response = new ArrayList<>();
        for(ContractEntity res:contracts){
            ContractResponse aux=contractMapping.entityToResponse(res);
            response.add(aux);
        }
        return response;
    }


    private Set<ContractResponse> getContractResponses(Set<ContractEntity> contract) {
        Set<ContractResponse> response=new HashSet<>();
        if(contract.isEmpty()){
            return null;
        }
        for (ContractEntity res:contract){
            ContractResponse aux =contractMapping.entityToResponse(res);
            response.add(aux);

        }
        return response;
    }
}
