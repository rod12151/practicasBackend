package practicas.gestion_personal.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.SimpleRequest;

import practicas.gestion_personal.infraestructure.abstract_services.WorkConditionService;

@RestController
@AllArgsConstructor
@RequestMapping("workCondition")
public class WorkConditionController {
    private WorkConditionService workConditionService;
    @GetMapping("{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code){
        return ResponseEntity.ok(workConditionService.findByCode(code));
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
    return ResponseEntity.ok(workConditionService.findAll());
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody SimpleRequest request){
    return ResponseEntity.ok(workConditionService.create(request));
    }
    @PutMapping("update/{code}")
    public ResponseEntity<?> update(@PathVariable String code, @RequestBody SimpleRequest request){
    return ResponseEntity.ok(workConditionService.update(code, request));
    }
    @DeleteMapping({"delete/{code}"})
    public ResponseEntity<?> delete(@PathVariable String code){
        workConditionService.delete(code);
    return ResponseEntity.noContent().build();
    }
}
