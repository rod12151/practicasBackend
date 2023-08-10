package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.SimpleRequest;

import practicas.gestion_personal.api.models.response.WorkConditionResponse;
import practicas.gestion_personal.infraestructure.abstract_services.WorkConditionService;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/workCondition")
public class WorkConditionController {
    private WorkConditionService workConditionService;
    @GetMapping("/{code}")
    public ResponseEntity<WorkConditionResponse> findByCode(@PathVariable String code){
        return ResponseEntity.ok(workConditionService.findByCode(code));
    }
    @GetMapping
    public ResponseEntity<Set<WorkConditionResponse>> findAll(){
    return ResponseEntity.ok(workConditionService.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<WorkConditionResponse> create(@Valid @RequestBody SimpleRequest request){
    return ResponseEntity.ok(workConditionService.create(request));
    }
    @PutMapping("/update/{code}")
    public ResponseEntity<WorkConditionResponse> update(@Valid @PathVariable String code, @RequestBody SimpleRequest request){
    return ResponseEntity.ok(workConditionService.update(code, request));
    }
    @DeleteMapping({"/delete/{code}"})
    public ResponseEntity<Void> delete(@PathVariable String code){
        workConditionService.delete(code);
    return ResponseEntity.noContent().build();
    }
}
