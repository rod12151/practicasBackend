package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.HeadServiceCreateRequest;
import practicas.gestion_personal.api.models.request.UserRequest;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.infraestructure.abstract_services.HeadServiceService;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping(path = "/{dni}")
    public ResponseEntity<UserResponse> findByDni(@PathVariable String dni){
        return ResponseEntity.ok(userService.findByDni(dni));

    }
    @GetMapping()
    public ResponseEntity<Set<UserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll());

    }
    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni){
        userService.deleteUser(dni);
        return ResponseEntity.noContent().build();
    }

}
