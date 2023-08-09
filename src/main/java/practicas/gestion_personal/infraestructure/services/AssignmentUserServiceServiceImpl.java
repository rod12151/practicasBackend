package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.AssignationRequest;
import practicas.gestion_personal.api.models.response.AssignmentUserServiceResponse;
import practicas.gestion_personal.domain.entities.*;
import practicas.gestion_personal.domain.repositories.*;
import practicas.gestion_personal.infraestructure.abstract_services.AssignmentUserServiceService;
import practicas.gestion_personal.mapper.AssignmentUserServiceMapping;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssignmentUserServiceServiceImpl implements AssignmentUserServiceService {
    private HeadServiceRepository headServiceRepository;
    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ContractRepository contractRepository;
    private AssignmentUserServiceRepository assignmentUserServiceRepository;

    private AssignmentUserServiceMapping assignmentUserServiceMapping;

    @Override
    public List<AssignmentUserServiceResponse> findAllByStatus(boolean status) {
        List<AssignmentUserServiceEntity> listAssignment = assignmentUserServiceRepository.findByStatusOrderByService(status);
        List<AssignmentUserServiceResponse> response = new ArrayList<>();
        for ( AssignmentUserServiceEntity entity: listAssignment){
            AssignmentUserServiceResponse aux= assignmentUserServiceMapping.assignmentUserServiceResponse(entity);
            response.add(aux);

        }
        return response;
    }

    @Override
    public List<AssignmentUserServiceResponse> findAllByCodeServiceAndStatus(String code, boolean status) {
        List<AssignmentUserServiceEntity> listAssignment = assignmentUserServiceRepository.findByService_CodeAndStatus(code,status);

        List<AssignmentUserServiceResponse> response = new ArrayList<>();
        for ( AssignmentUserServiceEntity entity: listAssignment){
            AssignmentUserServiceResponse aux= assignmentUserServiceMapping.assignmentUserServiceResponse(entity);
            response.add(aux);

        }
        return response;
    }

    @Override
    public List<AssignmentUserServiceResponse> findAllByUserAndStatus(String dniUser, boolean status) {
        List<AssignmentUserServiceEntity> listAssignment = assignmentUserServiceRepository.findByUser_DniAndStatusOrderByFinishDateDesc(dniUser, status);

        List<AssignmentUserServiceResponse> response = new ArrayList<>();
        for ( AssignmentUserServiceEntity entity: listAssignment){
            AssignmentUserServiceResponse aux= assignmentUserServiceMapping.assignmentUserServiceResponse(entity);
            response.add(aux);

        }
        return response;
    }

    @Override
    public AssignmentUserServiceResponse createAssigment(AssignationRequest request) {
        UserEntity user = userRepository.findByDni(request.getDniUser()).orElseThrow(()-> new IdNotFoundException("User"));
        ServiceEntity service = serviceRepository.findByCode(request.getCodeService()).orElseThrow(()->new IdNotFoundException("Service"));
        HeadServiceEntity boss= headServiceRepository.findByService_CodeAndStatusAndUser_Dni(request.getCodeService(),true,request.getDniBoss()).orElseThrow(()->new IdNotFoundException("HeadService"));
        List<ContractEntity> contracts = contractRepository.findByUser_DniAndStatusOrderByIdContractDesc(request.getDniUser(), true);
        List<AssignmentUserServiceEntity> assignment = assignmentUserServiceRepository.findByUser_DniAndService_CodeAndStatus(request.getDniUser(), request.getCodeService(), true);
        if(contracts.isEmpty()){
            throw new UserDuplicate(request.getDniUser(),"no tiene un contrato vigente");
        } else if (!assignment.isEmpty())  {
            throw new UserDuplicate(request.getDniUser(),"ya está asignado a otro serevicio");
        } else if (!Objects.equals(boss.getService().getCode(), request.getCodeService())) {
            throw new UserDuplicate(request.getDniBoss(),"yno es jefe del servicio");
        }
        {
            AssignmentUserServiceEntity assignmentCreate= AssignmentUserServiceEntity.builder()
                    .startDate(request.getStartDate())
                    .finishDate(request.getFinishDate())
                    .user(user)
                    .service(service)
                    .status(true)
                    .build();
            assignmentUserServiceRepository.save(assignmentCreate);

          return   assignmentUserServiceMapping.assignmentUserServiceResponse(assignmentCreate);
        }

    }

    @Override
    public boolean terminateAssignation(String codeService,String dniBoss, String dniUser) {
        List<AssignmentUserServiceEntity> assignmentUserService = assignmentUserServiceRepository.findByUser_DniAndService_CodeAndStatus(dniUser,codeService,true);
        Optional<HeadServiceEntity> boss = headServiceRepository.findByService_CodeAndStatusAndUser_Dni(codeService,true,dniBoss);
        if (!assignmentUserService.isEmpty()&& boss.isPresent()){
            AssignmentUserServiceEntity update = assignmentUserService.get(0);
            update.setFinishDate(LocalDate.now());
            update.setStatus(false);
            assignmentUserServiceRepository.save(update);
            return true;
        }
        return false;
    }
}
