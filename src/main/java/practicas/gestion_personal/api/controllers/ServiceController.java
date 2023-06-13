package practicas.gestion_personal.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.ServiceRequest;

import practicas.gestion_personal.infraestructure.abstract_services.ServiceService;

@RestController
@RequestMapping("service/")
@AllArgsConstructor
public class ServiceController {
    private ServiceService serviceService;
    @GetMapping("{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code){
        return ResponseEntity.ok(serviceService.findByCode(code));
    }
    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(serviceService.findAll());
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ServiceRequest request){
        return ResponseEntity.ok(serviceService.create(request));
    }
    @PutMapping("{code}")
    public ResponseEntity<?> update(@PathVariable String code,@RequestBody ServiceRequest request){
        return ResponseEntity.ok(serviceService.update(code,request));
    }
    @DeleteMapping("{code}")
    public ResponseEntity<?> delete(@PathVariable String code){
        serviceService.delete(code);
        return ResponseEntity.noContent().build();
    }

}
