package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.UserRequest;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;
import practicas.gestion_personal.mapper.UserMapping;

import java.util.HashSet;

import java.util.Set;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserMapping userMapping;
    @Override
    public UserResponse findByDni(String dni) {
        UserEntity userDb=userRepository.findByDni(dni).orElseThrow();
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
        UserEntity userUpdate=userRepository.findByDni(dni).orElseThrow();
        userUpdate.setDni(request.getDni());
        userUpdate.setName(request.getName());
        userUpdate.setLastName(request.getLastName());
        userUpdate.setEmail(request.getEmail());
        userUpdate.setProfession(request.getProfession());
        userUpdate.setBirthDate(request.getBirthDate());
        userRepository.save(userUpdate);
        return userMapping.userEntityToResponse(userUpdate);

    }

    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity userCreate=UserEntity.builder()
                .dni(request.getDni())
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .profession(request.getProfession())
                .birthDate(request.getBirthDate())
                .password(request.getPassword())
                .build();
        userRepository.save(userCreate);
        return userMapping.userEntityToResponse(userCreate);

    }

    @Override
    public void deleteUser(String dni) {
        UserEntity userDb = userRepository.findByDni(dni).orElseThrow();
        userRepository.delete(userDb);

    }
}
