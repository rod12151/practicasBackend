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
    boolean changeStatus(String dni);
    void deleteRoleUser(String dni,String role);

    boolean haveRole(String dni,String role);
    Set<UserResponse> findByNameContains(String query);
}
