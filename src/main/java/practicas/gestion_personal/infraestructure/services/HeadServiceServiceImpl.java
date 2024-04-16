package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.HeadServiceCreateRequest;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.domain.entities.*;
import practicas.gestion_personal.domain.repositories.*;
import practicas.gestion_personal.infraestructure.abstract_services.HeadServiceService;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;
import practicas.gestion_personal.mapper.HeadServiceMapping;
import practicas.gestion_personal.utils.IdDuplicate;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.ServiceWithBoss;
import practicas.gestion_personal.utils.UserDuplicate;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class HeadServiceServiceImpl implements HeadServiceService {
    private final UserService userService;
    private final ContractRepository contractRepository;
    private final AssignmentUserServiceRepository assignmentUserServiceRepository;
    private ServiceRepository serviceRepository;
    private UserRepository userRepository;
    private HeadServiceRepository headServiceRepository;
    private RoleRepository roleRepository;
    private HeadServiceMapping headServiceMapping;
    private static final String ROLE_JEFE = "ROLE_JEFE";

    /* @Override
    public HeadServiceResponse create(HeadServiceCreateRequest request) {
       UserEntity user = userRepository.findByDniAndStatusIsTrue(request.getDniUser()).orElseThrow(() -> new IdNotFoundException("User"));
        ServiceEntity service = serviceRepository.findByCode(request.getCodeService()).orElseThrow(() -> new IdNotFoundException("Service"));
        RoleEntity rol = roleRepository.findByName(ROLE_JEFE).orElseThrow();

        List<HeadServiceEntity> listBoss = headServiceRepository.findByServiceAndStatusOrderByFinishDateDesc(service, true);
        boolean haveRole = userService.haveRole(request.getDniUser(), ROLE_JEFE);
        if (haveRole) {
            throw new UserDuplicate(request.getDniUser(), "ya es Jefe en otro servicio");
        }
        if (listBoss.size() == 1) {
            HeadServiceEntity jefeanterior = listBoss.get(0);
            jefeanterior.setStatus(false);
            jefeanterior.setFinishDate(LocalDate.now());
            String dniUserAnterior = jefeanterior.getUser().getDni();

            userService.deleteRoleUser(dniUserAnterior, ROLE_JEFE);


        }

        List<RoleEntity> roles = user.getRoles();
        roles.add(rol);
        user.setRoles(roles);
        userRepository.save(user);
        service.setHeadAssigment(true);
        serviceRepository.save(service);
        HeadServiceEntity bossCreate = HeadServiceEntity.builder()
                .service(service)
                .user(user)
                .status(true)
                .startDate(request.getStartDate())
                .finishDate(request.getFinishDate()).build();
        headServiceRepository.save(bossCreate);
        return headServiceMapping.headServiceEntityToResponse(bossCreate);

return null;
    }*/

    @Override
    public HeadServiceResponse createBoss(HeadServiceCreateRequest request) {
        String codeService = request.getCodeService();
        String dni = request.getDniUser();

        UserEntity user = userRepository.findByDniAndStatusIsTrue(dni).orElseThrow(() -> new IdNotFoundException("User"));
        ServiceEntity service = serviceRepository.findByCode(codeService).orElseThrow(() -> new IdNotFoundException("Service"));

        Optional<HeadServiceEntity> bossActual = headServiceRepository.buscarBossActual(codeService);
        boolean haveRole = userService.haveRole(dni, ROLE_JEFE);

        Optional<AssignmentUserServiceEntity> userAssignmentService = assignmentUserServiceRepository.selectByDniCoDeServiceExist(dni, codeService);

        if (haveRole) {
            throw new UserDuplicate(dni, "ya es Jefe De Otro servicio");
        } else if (bossActual.isPresent()) {
            UserEntity dataUser = bossActual.orElseThrow().getUser();
            String nameBoss = dataUser.getName() + " " + dataUser.getLastName();
            String dniBoss = dataUser.getDni();
            String dataBoss = "nombres : " + nameBoss + " dni: " + dniBoss;
            throw new ServiceWithBoss(codeService, dataBoss);

        } else if (userAssignmentService.isEmpty()) {
            throw new UserDuplicate(dni,"no está asignado al servicio con codigo : "+codeService);

        }else{
            RoleEntity rol = roleRepository.findByName(ROLE_JEFE).orElseThrow();
            List<RoleEntity> roles = user.getRoles();
            roles.add(rol);
            user.setRoles(roles);
            userRepository.save(user);
            service.setHeadAssigment(true);
            serviceRepository.save(service);
            HeadServiceEntity bossCreate = HeadServiceEntity.builder()
                    .service(service)
                    .user(user)
                    .status(true)
                    .startDate(request.getStartDate())
                    .finishDate(request.getFinishDate()).build();
            headServiceRepository.save(bossCreate);
            return headServiceMapping.headServiceEntityToResponse(bossCreate);
        }

    }


    @Override
    public Set<HeadServiceResponse> findByStatus(Boolean status) {

        List<HeadServiceEntity> list = headServiceRepository.findAllByStatusOrderByIdHeadService(status);
        Set<HeadServiceResponse> response = new HashSet<>();
        for (HeadServiceEntity res : list) {
            HeadServiceResponse aux = headServiceMapping.headServiceEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteHeadService(String dniUser, String codeService) {
        UserEntity user = userRepository.findByDni(dniUser).orElseThrow(() -> new IdNotFoundException("user"));
        ServiceEntity service = serviceRepository.findByCode(codeService).orElseThrow(() -> new IdNotFoundException("service"));
        Map<String, Object> response = new HashMap<>();
        if (user != null && service != null) {
            Optional<HeadServiceEntity> headService = headServiceRepository.findByServiceCodeAndStatusAndUserDni(codeService, true, dniUser);
            if (headService.isPresent()) {
                headService.orElseThrow().setStatus(false);
                headService.orElseThrow().setFinishDate(LocalDate.now());
                String userAnterior = headService.get().getUser().getDni();
                service.setHeadAssigment(false);
                serviceRepository.save(service);
                userService.deleteRoleUser(userAnterior, ROLE_JEFE);

                response.put("status", "ok");
                response.put("message", "se eliminó al jefe del servicio");


            } else {
                response.put("status", "error");
                response.put("message", "El usuario no es jefe del servicio");

            }
        }


        return response;
    }

    @Override
    public List<HeadServiceResponse> findByService(String code, boolean status) {
        ServiceEntity service = serviceRepository.findByCode(code).orElseThrow(() -> new IdNotFoundException("service"));
        List<HeadServiceEntity> bossList = headServiceRepository.findByServiceAndStatusOrderByFinishDateDesc(service, status);
        List<HeadServiceResponse> response = new ArrayList<>();
        for (HeadServiceEntity res : bossList) {
            HeadServiceResponse aux = headServiceMapping.headServiceEntityToResponse(res);
            response.add(aux);
        }
        return response;

    }
}
