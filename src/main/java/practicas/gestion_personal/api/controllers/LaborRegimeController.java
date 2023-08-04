package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.infraestructure.abstract_services.LaborRegimeService;

@RestController
@AllArgsConstructor
@RequestMapping("/laborRegime")
public class LaborRegimeController {
    private LaborRegimeService laborRegimeService;
    @GetMapping("/{code}")
    public ResponseEntity<?> findByCode(@PathVariable String code){
        return ResponseEntity.ok(laborRegimeService.findByCode(code));
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(laborRegimeService.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody SimpleRequest request){
        return ResponseEntity.ok(laborRegimeService.create(request));
    }
    @PutMapping("/update/{code}")
    public ResponseEntity<?> update(@Valid @PathVariable String code, @RequestBody SimpleRequest request){
        return ResponseEntity.ok(laborRegimeService.update(code, request));
    }
    @DeleteMapping({"/delete/{code}"})
    public ResponseEntity<?> delete(@PathVariable String code){
        laborRegimeService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
