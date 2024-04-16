package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.api.models.response.ContractResponse;
import practicas.gestion_personal.domain.entities.ContractEntity;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;
import practicas.gestion_personal.domain.repositories.*;
import practicas.gestion_personal.infraestructure.abstract_services.ContractService;
import practicas.gestion_personal.mapper.ContractMapping;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor

public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private UserRepository userRepository;
    private WorkConditionRepository workConditionRepository;
    private LaborRegimeRepository laborRegimeRepository;

    private AssignmentUserServiceServiceImpl assignmentUserServiceService;

    private ContractMapping contractMapping;

    @Override
    @Transactional(readOnly = true)
    public Set<ContractResponse> findByUserDni(String dni) {


        Set<ContractResponse> response = new HashSet<>();
        var contract = contractRepository.selectIfDniContains(dni);
        for (ContractEntity res : contract) {
            ContractResponse aux = contractMapping.entityToResponse(res);
            response.add(aux);


        }
        return response;


    }

    @Override
    public Set<ContractResponse> findByLaborRegimeCode(String code) {


        Set<ContractResponse> response = new HashSet<>();
        Set<ContractEntity> contract = contractRepository.LaborRegimeContains(code);
        for (ContractEntity res : contract) {
            ContractResponse aux = contractMapping.entityToResponse(res);
            response.add(aux);


        }
        return response;


    }

    @Override
    public Set<ContractResponse> findByWorkConditionCode(String code) {


        Set<ContractResponse> response = new HashSet<>();
        Set<ContractEntity> contract = contractRepository.WorkConditionsContains(code);
        for (ContractEntity res : contract) {
            ContractResponse aux = contractMapping.entityToResponse(res);
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
        var contract = contractRepository.findAllByFinishDateIsBeforeAndStatusIsTrue(date);
        return getContractResponses(contract);
    }

    @Override
    public Set<ContractResponse> findByStartDateAndFinishDate(LocalDate startDate, LocalDate finishDate) {
        var contract = contractRepository.findAllByStartDateIsAfterAndFinishDateIsBeforeOrderByStartDate(startDate, finishDate);
        return getContractResponses(contract);
    }

    @Override
    public ContractResponse createContract(ContractRequest request) {
        UserEntity userEntity = userRepository.findByDni(request.getDniUser()).orElseThrow(() -> new IdNotFoundException("User"));
        WorkConditionEntity workCondition = workConditionRepository.findByCode(request.getCodeWorkCondition()).orElseThrow(() -> new IdNotFoundException("WorkCondition"));
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(request.getCodeLaborRegime()).orElseThrow(() -> new IdNotFoundException("laborRegime"));

        List<ContractEntity> contracts = contractRepository.findByUserDniAndStatusOrderByIdContractDesc(request.getDniUser(), true);
        userEntity.setStatus(true);
        if (contracts.isEmpty() && userEntity.isStatus()) {
            ContractEntity contract = ContractEntity.builder()
                    .user(userEntity)
                    .position(request.getPosition())
                    .startDate(request.getStartDate())
                    .finishDate(request.getFinishDate())
                    .laborRegime(laborRegime)
                    .workCondition(workCondition)
                    .salary(request.getSalary())
                    .status(true)
                    .build();
            contractRepository.save(contract);
            return contractMapping.entityToResponse(contract);
        } else {
            throw new UserDuplicate(request.getDniUser(), "ya tiene un contrato vigente, o el usuario esta inactivo");
        }
    }

    @Override
    public ContractResponse updateContract(Long id, ContractRequest request) {
        ContractEntity contractUpdate = contractRepository.findById(id).orElseThrow(() -> new IdNotFoundException("contract"));
        UserEntity userEntity = userRepository.findByDni(request.getDniUser()).orElseThrow(() -> new IdNotFoundException("User"));
        WorkConditionEntity workCondition = workConditionRepository.findByCode(request.getCodeWorkCondition()).orElseThrow(() -> new IdNotFoundException("workCondition"));
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(request.getCodeLaborRegime()).orElseThrow(() -> new IdNotFoundException("laborRegime"));
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
    public Map<String, Object> terminateContract(Long id) {
        Map<String, Object> result = new HashMap<>();
        ContractEntity contract = contractRepository.findById(id).orElseThrow(() -> new IdNotFoundException("contract"));
        String idUser = String.valueOf(contract.getUser().getIdUser());
        if (contract.isStatus()) {
            assignmentUserServiceService.forceTerminateAssign(idUser);
            contract.setStatus(false);
            contract.setFinishDate(LocalDate.now());
            contract.getUser().setStatus(false);
            contractRepository.save(contract);
            result.put("message", " el contrato del Usuario con DNI: " + contract.getUser().getDni() + " se actualizó a un estado terminado");
            result.put("status", true);
        } else {
            result.put("message", "el contrato del Usuario con DNI: " + contract.getUser().getDni() + " ya se encontraba en un estado terminado");
            result.put("status", false);
        }
        return result;
    }


    @Override
    public List<ContractResponse> listContractUser(String dni, boolean status) {

        Optional<UserEntity> user = userRepository.findByDni(dni);
        if (user.isPresent()) {
            List<ContractEntity> contracts = contractRepository.findByUserDniAndStatusOrderByIdContractDesc(dni, status);
            List<ContractResponse> response = new ArrayList<>();
            for (ContractEntity res : contracts) {
                ContractResponse aux = contractMapping.entityToResponse(res);
                response.add(aux);
            }
            return response;
        }
        {
            throw new IdNotFoundException("user");

        }

    }


    private Set<ContractResponse> getContractResponses(Set<ContractEntity> contract) {
        Set<ContractResponse> response = new HashSet<>();
        if (contract.isEmpty()) {
            return null;
        }
        for (ContractEntity res : contract) {
            ContractResponse aux = contractMapping.entityToResponse(res);
            response.add(aux);

        }
        return response;
    }
}
