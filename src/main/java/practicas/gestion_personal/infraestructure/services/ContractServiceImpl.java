package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.domain.entities.ContractEntity;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;
import practicas.gestion_personal.domain.repositories.ContractRepository;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.domain.repositories.WorkConditionRepository;
import practicas.gestion_personal.infraestructure.abstract_services.ContractService;
import practicas.gestion_personal.utils.IdNotFoundException;

import java.time.LocalDate;
import java.util.Set;
@Service
@AllArgsConstructor

public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;
    private UserRepository userRepository;

    private WorkConditionRepository workConditionRepository;
    private LaborRegimeRepository laborRegimeRepository;
    @Override
    @Transactional(readOnly = true)
    public Set<ContractEntity> findByUserDni(String dni) {
        UserEntity user =userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));

        return contractRepository.findAllByUser_Dni(dni);
    }
    @Override
    public Set<ContractEntity> findByLaborRegimeCode(String code) {
        LaborRegimeEntity laborRegime=laborRegimeRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("laborRegime"));
        return contractRepository.findByLaborRegime_Code(code);
    }
    @Override
    public Set<ContractEntity> findByWorkConditionCode(String code) {
        WorkConditionEntity workCondition=workConditionRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("workCondition"));

        return contractRepository.findByWorkCondition_Code(code);
    }
    @Override
    public Set<ContractEntity> findByStartDateAfter(LocalDate date) {
        var a = contractRepository.findAllByStartDateIsAfter(date);
        if(a.isEmpty()){
            return null;
        }
        return a;
    }
    @Override
    public Set<ContractEntity> findByFinishDateBefore(LocalDate date) {
        var a= contractRepository.findAllByFinishDateIsBefore(date);
        if(a.isEmpty()){
            return null;
        }
        return a;
    }
    @Override
    public Set<ContractEntity> findByStartDateAndFinishDate(LocalDate startDate, LocalDate finishDate) {
        var a= contractRepository.findAllByStartDateIsAfterAndFinishDateIsBeforeOrderByStartDate(startDate,finishDate);
        if(a.isEmpty()){
            return null;
        }
        return a;
    }

    @Override
    public ContractEntity createContract(ContractRequest request) {
        UserEntity userEntity=userRepository.findByDni(request.getDniUser()).orElseThrow(()->new IdNotFoundException("User"));
        WorkConditionEntity workCondition =workConditionRepository.findByCode(request.getCodeWorkCondition()).orElseThrow(()->new IdNotFoundException("WorkCondition"));
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(request.getCodeLaborRegime()).orElseThrow(()->new IdNotFoundException("laborRegime"));
        ContractEntity contract=ContractEntity.builder()
                .user(userEntity)
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .finishDate(request.getFinishDate())
                .laborRegime(laborRegime)
                .workCondition(workCondition)
                .build();
        contractRepository.save(contract);
        return contract;
    }
    @Override
    public ContractEntity updateContract(Long id, ContractRequest request) {
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

        return contractUpdate;
    }

    @Override
    public void deleteContract(Long id) {
        ContractEntity contract = contractRepository.findById(id).orElseThrow(()->new IdNotFoundException("contract"));
        contractRepository.delete(contract);

    }
}
