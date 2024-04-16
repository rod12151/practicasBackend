package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.UserRequest;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.infraestructure.abstract_services.UserService;

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
    @GetMapping(path = "/share")
    public ResponseEntity<Set<UserResponse>> findNameContains(@RequestParam String query){
        return ResponseEntity.ok(userService.findByNameContains(query));

    }
    @GetMapping(path = "/no/contract")
    public ResponseEntity<Set<UserResponse>> findByWithoutContract(@RequestParam String query){
        return ResponseEntity.ok(userService.findAllByWithoutContract (query));

    }
    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PutMapping("/{dni}")
    public boolean change(@PathVariable String dni){
        userService.changeStatus(dni);
        return true;
    }
    @GetMapping("/user/assign")
    public ResponseEntity<Set<UserResponse>> findUserAssignment(@RequestParam String query){
         return ResponseEntity.ok(userService.findUserNotAssignments(query));

    }
    @GetMapping("/boss/assign")
    public ResponseEntity<Set<UserResponse>> findBossAssignment(@RequestParam String query) {
        return ResponseEntity.ok(userService.findBossNotAssignments(query));
    }

    @PutMapping("/user/update")
    public ResponseEntity<UserResponse> updateUser( @Valid @RequestParam String dni,@RequestBody UserRequest user){
        return ResponseEntity.ok(userService.updateUser(dni, user));
    }

}
