package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.UserRequest;
import practicas.gestion_personal.api.models.response.UserResponse;

import java.util.Set;

public interface UserService {
    UserResponse findByDni(String dni);
    Set<UserResponse> findAll();
    Set<UserResponse> findByServiceCode(String code);
    UserResponse updateUser(String dni, UserRequest request);
    UserResponse createUser(UserRequest request);
    void changeStatus(String dni);
    void deleteRoleUser(String dni,String role);

    boolean haveRole(String dni,String role);
    Set<UserResponse> findByNameContains(String query);
    Set<UserResponse> findAllByWithoutContract(String query);

    UserResponse findByStatusAndNameContains(boolean status,String name, String lastName);


    Set<UserResponse> findUserNotAssignments(String filter);
    Set<UserResponse> findBossNotAssignments(String filter);
}
