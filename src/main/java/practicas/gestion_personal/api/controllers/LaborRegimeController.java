package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.LaborRegimeResponse;
import practicas.gestion_personal.infraestructure.abstract_services.LaborRegimeService;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/laborRegime")
public class LaborRegimeController {
    private LaborRegimeService laborRegimeService;
    @GetMapping("/{code}")
    public ResponseEntity<LaborRegimeResponse> findByCode(@Valid @PathVariable String code){
        return ResponseEntity.ok(laborRegimeService.findByCode(code));
    }
    @GetMapping
    public ResponseEntity<Set<LaborRegimeResponse>> findAll(){
        return ResponseEntity.ok(laborRegimeService.findAll());
    }
    @PostMapping("/create")
    public ResponseEntity<LaborRegimeResponse> create(@Valid @RequestBody SimpleRequest request){
        return ResponseEntity.ok(laborRegimeService.create(request));
    }
    @PutMapping("/update/{code}")
    public ResponseEntity<LaborRegimeResponse> update(@Valid @PathVariable String code, @RequestBody SimpleRequest request){
        return ResponseEntity.ok(laborRegimeService.update(code, request));
    }
    @DeleteMapping({"/delete/{code}"})
    public ResponseEntity<Void> delete(@PathVariable String code){
        laborRegimeService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
