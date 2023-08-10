package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.UserRequest;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.RoleEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.repositories.RoleRepository;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;
import practicas.gestion_personal.mapper.UserMapping;
import practicas.gestion_personal.utils.IdDuplicate;
import practicas.gestion_personal.utils.IdNotFoundException;

import java.util.*;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private UserMapping userMapping;

    private PasswordEncoder passwordEncoder;
    @Override
    public UserResponse findByDni(String dni) {
        UserEntity userDb=userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));
    return userMapping.userEntityToResponse(userDb);
    }

    @Override
    public Set<UserResponse> findAll() {
        var users=userRepository.findAll();
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
        userUpdate.setBirthDate(request.getBirthDate());
        userRepository.save(userUpdate);
        return userMapping.userEntityToResponse(userUpdate);

    }

    @Override
    public UserResponse createUser(UserRequest request) {
        var o = userRepository.findByDni(request.getDni());
        RoleEntity rol = roleRepository.findByName("ROLE_USER").orElseThrow();
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
                .birthDate(request.getBirthDate())
                .password(passwordEncoder.encode(request.getDni()))
                .roles(roles)
                .build();
        userRepository.save(userCreate);
        return userMapping.userEntityToResponse(userCreate);

    }

    @Override
    public void deleteUser(String dni) {
        UserEntity userDb = userRepository.findByDni(dni).orElseThrow(()->new IdNotFoundException("user"));
        userRepository.delete(userDb);

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
}
