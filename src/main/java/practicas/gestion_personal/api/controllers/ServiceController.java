package practicas.gestion_personal.api.controllers;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.ServiceRequest;

import practicas.gestion_personal.api.models.response.ServiceResponse;
import practicas.gestion_personal.infraestructure.abstract_services.ServiceService;

import java.util.Set;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {
    private ServiceService serviceService;
    @Operation(summary = "optener un servico mediante el codigo de servicio")
    //@Parameter(in = ParameterIn.HEADER,name = "Authorization",description = "Token de Autorizacion",required = true)
    @GetMapping("/{code}")
    public ResponseEntity<ServiceResponse> findByCode(
            @PathVariable String code){
        return ResponseEntity.ok(serviceService.findByCode(code));
    }
    @GetMapping()
    public ResponseEntity<Set<ServiceResponse>> findAll(){
        return ResponseEntity.ok(serviceService.findAll());
    }
    @GetMapping("services")
    public ResponseEntity<Set<ServiceResponse>> findAllStatusHeadAssignment(@RequestParam Boolean query){
        return ResponseEntity.ok(serviceService.findAllByHeadStatus(query));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create( @Valid @RequestBody ServiceRequest request){
        return ResponseEntity.ok(serviceService.create(request));
    }
    @PutMapping("/{code}")
    public ResponseEntity<ServiceResponse> update(@Valid @PathVariable String code,@RequestBody ServiceRequest request){
        return ResponseEntity.ok(serviceService.update(code,request));
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code){
        serviceService.delete(code);
        return ResponseEntity.noContent().build();
    }

}
