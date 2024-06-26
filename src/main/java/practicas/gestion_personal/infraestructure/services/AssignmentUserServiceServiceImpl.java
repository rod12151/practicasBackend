package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.AssignationRequest;
import practicas.gestion_personal.api.models.response.AssignmentUserServiceResponse;
import practicas.gestion_personal.domain.entities.*;
import practicas.gestion_personal.domain.repositories.*;
import practicas.gestion_personal.infraestructure.abstract_services.AssignmentUserServiceService;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;
import practicas.gestion_personal.mapper.AssignmentUserServiceMapping;
import practicas.gestion_personal.utils.FilterAssign;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class AssignmentUserServiceServiceImpl implements AssignmentUserServiceService {
    private HeadServiceRepository headServiceRepository;
    private UserRepository userRepository;
    private ServiceRepository serviceRepository;
    private ContractRepository contractRepository;
    private AssignmentUserServiceRepository assignmentUserServiceRepository;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private UserService userService;
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
        List<AssignmentUserServiceEntity> listAssignment = assignmentUserServiceRepository.findByServiceCodeAndStatus(code,status);

        List<AssignmentUserServiceResponse> response = new ArrayList<>();
        for ( AssignmentUserServiceEntity entity: listAssignment){
            AssignmentUserServiceResponse aux= assignmentUserServiceMapping.assignmentUserServiceResponse(entity);
            response.add(aux);

        }
        return response;
    }

    @Override
    public List<AssignmentUserServiceResponse> findAllByUserAndStatus(String dniUser, boolean status) {
        List<AssignmentUserServiceEntity> listAssignment = assignmentUserServiceRepository.findByUserDniAndStatusOrderByFinishDateDesc(dniUser, status);

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
        Optional<HeadServiceEntity> boss= headServiceRepository.findByServiceCodeAndStatusAndUserDni(request.getCodeService(),true,request.getDniBoss());
        List<ContractEntity> contracts = contractRepository.findByUserDniAndStatusOrderByIdContractDesc(request.getDniUser(), true);
        List<AssignmentUserServiceEntity> assignment = assignmentUserServiceRepository.findByUserDniAndStatus(request.getDniUser(), true);
        boolean isAdmin = userService.haveRole(request.getDniBoss(), ROLE_ADMIN);
        if(contracts.isEmpty()){
            throw new UserDuplicate(request.getDniUser(),"no tiene un contrato vigente");
        } else if (!assignment.isEmpty())  {
            throw new UserDuplicate(request.getDniUser(),"ya está asignado a otro serevicio");

        } else if (boss.isPresent()||isAdmin) {

       
            AssignmentUserServiceEntity assignmentCreate= AssignmentUserServiceEntity.builder()
                    .startDate(request.getStartDate())
                    .finishDate(request.getFinishDate())
                    .user(user)
                    .service(service)
                    .status(true)
                    .build();

            assignmentUserServiceRepository.save(assignmentCreate);
            return   assignmentUserServiceMapping.assignmentUserServiceResponse(assignmentCreate);

        }else{
            throw new UserDuplicate(request.getDniBoss(),"no es jefe del servicio,tampoco es administrador");
        }


    }

    @Override
    public boolean terminateAssignation(String codeService,String dniBoss, String dniUser) {
        List<AssignmentUserServiceEntity> assignmentUserService = assignmentUserServiceRepository.findByUserDniAndServiceCodeAndStatus(dniUser,codeService,true);
        Optional<HeadServiceEntity> boss = headServiceRepository.findByServiceCodeAndStatusAndUserDni(codeService,true,dniBoss);

        boolean isAdmin = userService.haveRole(dniBoss, ROLE_ADMIN);
        if (!assignmentUserService.isEmpty()&& (boss.isPresent()|| isAdmin)){
            AssignmentUserServiceEntity update = assignmentUserService.get(0);
            update.setFinishDate(LocalDate.now());
            update.setStatus(false);
            assignmentUserServiceRepository.save(update);
            return true;
        }
        return false;
    }

    @Override
    public Set<AssignmentUserServiceResponse> findAllWhitFilter(String assign, String filter) {

        Set<AssignmentUserServiceEntity> valor;
        Set<AssignmentUserServiceResponse> response = new HashSet<>();
        if(FilterAssign.valueOf(assign).getClass().isEnum()){
            switch (FilterAssign.valueOf(assign)) {
                case filterService -> valor = assignmentUserServiceRepository.filterForService(filter);

                case filterLaborRegime -> valor = assignmentUserServiceRepository.filterForLaborRegime(filter);
                case filterWorkCondition -> valor = assignmentUserServiceRepository.filterForWorkCondition(filter);
                default -> {
                    return response;
                }
            }
            for (AssignmentUserServiceEntity res:valor){

                response.add(assignmentUserServiceMapping.assignmentUserServiceResponse(res));
            }
        }
        return response;


    }

    @Override
    public void forceTerminateAssign(String id) {
        Optional<AssignmentUserServiceEntity> assignmentUserService=assignmentUserServiceRepository.selectByIdYStatus(id);
        if (assignmentUserService.isPresent()){
            AssignmentUserServiceEntity update = assignmentUserService.get();
            update.setStatus(false);
            update.setFinishDate(LocalDate.now());
            assignmentUserServiceRepository.save(update);
        }

    }

}