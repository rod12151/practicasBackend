package practicas.gestion_personal.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.AssignationRequest;
import practicas.gestion_personal.api.models.response.AssignmentUserServiceResponse;
import practicas.gestion_personal.infraestructure.abstract_services.AssignmentUserServiceService;
import practicas.gestion_personal.utils.FilterAssign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/assignment")
@AllArgsConstructor
public class AssignmentUserServiceController {

    private AssignmentUserServiceService assignmentUserService;
    @PostMapping("/create")
    public ResponseEntity<AssignmentUserServiceResponse> createAssignment(@RequestBody AssignationRequest request){
        return  ResponseEntity.ok(assignmentUserService.createAssigment(request));

    }
    @GetMapping("/find/status")
    public ResponseEntity<List<AssignmentUserServiceResponse>> findAllStatus(@RequestParam boolean status){
        return  ResponseEntity.ok(assignmentUserService.findAllByStatus(status));

    }
    @GetMapping("/find/code/{code}")
    public ResponseEntity<List<AssignmentUserServiceResponse>> findAllCode(@PathVariable String code,@RequestParam boolean status){
        return  ResponseEntity.ok(assignmentUserService.findAllByCodeServiceAndStatus(code,status));

    }
    @GetMapping("/find/user/{dni}")
    public ResponseEntity<List<AssignmentUserServiceResponse>> findAllUser(@PathVariable String dni,@RequestParam boolean status){
        return  ResponseEntity.ok(assignmentUserService.findAllByUserAndStatus(dni,status));

    }
    @PutMapping("/update/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@RequestParam String codeService ,@RequestParam String dniBoss,@RequestParam String dniUser){

        boolean status= assignmentUserService.terminateAssignation(codeService, dniBoss, dniUser);
        String res = status ? "Terminado exitosamente" : "No se pudo terminar";
        int httpStatus = status ? HttpStatus.OK.value() : HttpStatus.BAD_REQUEST.value();
        Map<String, Object> response = new HashMap<>();

        response.put("status", status);
        response.put("message", res);
        response.put("code",httpStatus);

        return status? ResponseEntity.ok(response):ResponseEntity.badRequest().body(response);


    }
    @GetMapping("/filter")
    public ResponseEntity<Set<AssignmentUserServiceResponse>> findAllWhitFilter(@RequestParam String option , @RequestParam String filter){
        return ResponseEntity.ok(assignmentUserService.findAllWhitFilter(option,filter));
    }



}
