package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.UserRequest;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.ContractEntity;
import practicas.gestion_personal.domain.entities.RoleEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.repositories.ContractRepository;
import practicas.gestion_personal.domain.repositories.RoleRepository;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;
import practicas.gestion_personal.mapper.UserMapping;
import practicas.gestion_personal.utils.IdDuplicate;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;

import java.util.*;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ContractRepository contractRepository;
    private UserMapping userMapping;

    private PasswordEncoder passwordEncoder;
    private static final String ROLE_USER = "ROLE_USER";
    @Override
    public UserResponse findByDni(String dni) {
        UserEntity userDb=userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));
    return userMapping.userEntityToResponse(userDb);
    }

    @Override
    public Set<UserResponse> findAll() {
        var users=userRepository.findAllByStatusIsTrue();
        Set<UserResponse> response= new HashSet<>();
        for(UserEntity res:users){
            UserResponse aux=userMapping.userEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }


    @Override
    public Set<UserResponse> findByServiceCode(String code) {
        return null;
    }

    @Override
    public UserResponse updateUser(String dni, UserRequest request) {
        UserEntity userUpdate=userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));

        userUpdate.setDni(request.getDni());
        userUpdate.setName(request.getName());
        userUpdate.setLastName(request.getLastName());
        userUpdate.setProfession(request.getProfession());
        userRepository.save(userUpdate);
        return userMapping.userEntityToResponse(userUpdate);

    }

    @Override
    public UserResponse createUser(UserRequest request) {
        var o = userRepository.findByDni(request.getDni());
        RoleEntity rol = roleRepository.findByName(ROLE_USER).orElseThrow();
        List<RoleEntity>roles=new ArrayList<>();
        roles.add(rol);
        if (o.isPresent()){
            throw new IdDuplicate("DNI");
        }
        UserEntity userCreate=UserEntity.builder()
                .dni(request.getDni())
                .name(request.getName())
                .lastName(request.getLastName())
                .username(request.getDni()+"@hospital.huanta.pe")
                .profession(request.getProfession())
                .genero(request.getGenero())
                .birthDate(request.getBirthDate())
                .password(passwordEncoder.encode(request.getDni()))
                .status(true)
                .roles(roles)
                .build();
        userRepository.save(userCreate);
        return userMapping.userEntityToResponse(userCreate);

    }

    @Override
    public void changeStatus(String dni) {
        List<ContractEntity> contract = contractRepository.findByUserDniAndStatusOrderByIdContractDesc(dni,true);
        UserEntity userDb = userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));
 
        if (contract.isEmpty()){
            userDb.setStatus(false);
            deleteRoleUser(dni,ROLE_USER);
            userRepository.save(userDb);
        }
        else{
            throw new UserDuplicate(dni, "tiene un contrato vigente, imposible eliminar");
        }


    }
    @Override
    public void deleteRoleUser(String dni,String role){
        UserEntity userDb = userRepository.findByDni(dni).orElseThrow(()-> new IdNotFoundException("User"));
        List< RoleEntity> roles= userDb.getRoles();
        roles.removeIf(objet -> Objects.equals(objet.getName(), role));
        userDb.setRoles(roles);
        userRepository.save(userDb);

    }

    @Override
    public boolean haveRole(String dni, String role) {
        UserEntity userDb = userRepository.findByDni(dni).orElseThrow(()-> new IdNotFoundException("User"));
        List< RoleEntity> roles= userDb.getRoles();
        return roles.stream()
                .anyMatch( object->role.equals(object.getName()));

    }

    @Override
    public Set<UserResponse> findByNameContains(String query) {
        var users=userRepository.findByNameContainsOrLastNameContaining(query,query);
        Set<UserResponse> response= new HashSet<>();
        for(UserEntity res:users){
            UserResponse aux=userMapping.userEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }

    @Override
    public Set<UserResponse> findAllByWithoutContract(String query) {
        List<UserEntity> users=userRepository.getUsersWithoutContractActive(query,query);
        Set<UserResponse> response= new HashSet<>();
        for(UserEntity res:users){
            UserResponse aux=userMapping.userEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }

    @Override
    public UserResponse findByStatusAndNameContains(boolean status, String name, String lastName) {
        return null;
    }

    @Override
    public Set<UserResponse> findUserNotAssignments(String filter) {
        List<UserEntity> users = userRepository.getUsuariosNoAsignadosActivos(filter,filter);
        Set<UserResponse> response= new HashSet<>();
        for(UserEntity res:users){
            UserResponse aux=userMapping.userEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }

    @Override
    public Set<UserResponse> findBossNotAssignments(String filter) {
        List<UserEntity> users = userRepository.getJefesNoAsignadosActivos(filter,filter);
        Set<UserResponse> response= new HashSet<>();
        for(UserEntity res:users){
            UserResponse aux=userMapping.userEntityToResponse(res);
            response.add(aux);
        }
        return response;
    }
}
