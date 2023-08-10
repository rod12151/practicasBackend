package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.HeadServiceCreateRequest;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;
import practicas.gestion_personal.domain.entities.RoleEntity;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.repositories.HeadServiceRepository;
import practicas.gestion_personal.domain.repositories.RoleRepository;
import practicas.gestion_personal.domain.repositories.ServiceRepository;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.infraestructure.abstract_services.HeadServiceService;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;
import practicas.gestion_personal.mapper.HeadServiceMapping;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class HeadServiceServiceImpl implements HeadServiceService {
    private final UserService userService;
    private ServiceRepository serviceRepository;
    private UserRepository userRepository;
    private HeadServiceRepository headServiceRepository;
    private RoleRepository roleRepository;
    private HeadServiceMapping headServiceMapping;
    private static final String ROLE_JEFE = "ROLE_JEFE";

    @Override
    public HeadServiceResponse create(HeadServiceCreateRequest request) {
        UserEntity user = userRepository.findByDni(request.getDniUser()).orElseThrow(()-> new IdNotFoundException("User"));
        ServiceEntity service = serviceRepository.findByCode(request.getCodeService()).orElseThrow(()->new IdNotFoundException("Service"));
        RoleEntity rol = roleRepository.findByName(ROLE_JEFE).orElseThrow();

        List<HeadServiceEntity> listBoss=headServiceRepository.findByServiceAndStatusOrderByFinishDateDesc(service,true);
        boolean haveRole = userService.haveRole(request.getDniUser(), ROLE_JEFE);
        if(haveRole){
            throw new UserDuplicate(request.getDniUser(),"ya es Jefe en otro servicio");
        }
        if (listBoss.size()==1){
            HeadServiceEntity jefeanterior = listBoss.get(0);
            jefeanterior.setStatus(false);
            jefeanterior.setFinishDate(LocalDate.now());
            String userAnterior=jefeanterior.getUser().getDni();

            userService.deleteRoleUser(userAnterior,ROLE_JEFE);

        }

        List<RoleEntity> roles=user.getRoles();
        roles.add(rol);
        user.setRoles(roles);
        userRepository.save(user);
        HeadServiceEntity bossCreate=HeadServiceEntity.builder()
                .service(service)
                .user(user)
                .status(true)
                .startDate(request.getStartDate())
                .finishDate(request.getFinishDate()).build();
        headServiceRepository.save(bossCreate);
        return headServiceMapping.headServiceEntityToResponse(bossCreate);



    }


    @Override
    public Set<HeadServiceResponse> findByStatus(Boolean status) {

        List<HeadServiceEntity> list= headServiceRepository.findAllByStatus(status);
        Set<HeadServiceResponse> response= new HashSet<>();
        for(HeadServiceEntity res:list){
            HeadServiceResponse aux=headServiceMapping.headServiceEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }

    @Override
    public String deleteHeadService(String dniUser, String codeService) {
        UserEntity user = userRepository.findByDni(dniUser).orElseThrow(()-> new IdNotFoundException("user"));
        ServiceEntity service = serviceRepository.findByCode(codeService).orElseThrow(()->new IdNotFoundException("service"));
        Optional<HeadServiceEntity> headService= headServiceRepository.findByServiceCodeAndStatusAndUserDni(dniUser,true,codeService);
        if (headService.isPresent()){
            headService.orElseThrow().setStatus(false);
            headService.orElseThrow().setFinishDate(LocalDate.now());
            String userAnterior=headService.get().getUser().getDni();
            userService.deleteRoleUser(userAnterior,ROLE_JEFE);

        }else{
            return "El usuario no es jefe del servicio";
        }

        return "se eliminó al jefe del servicio";
    }

    @Override
    public List<HeadServiceResponse> findByService(String code, boolean status) {
        ServiceEntity service = serviceRepository.findByCode(code).orElseThrow(()-> new IdNotFoundException("service"));
        List<HeadServiceEntity> bossList = headServiceRepository.findByServiceAndStatusOrderByFinishDateDesc(service,status);
        List<HeadServiceResponse> response=new ArrayList<>();
        for(HeadServiceEntity res:bossList){
            HeadServiceResponse aux=headServiceMapping.headServiceEntityToResponse(res);
            response.add(aux);
        }
        return response;

    }
}
