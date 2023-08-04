package practicas.gestion_personal.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.HeadServiceCreateRequest;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.infraestructure.abstract_services.HeadServiceService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/boss")
@AllArgsConstructor
public class HeadServiceController {
    private HeadServiceService headServiceService;
    @PostMapping("/create")
    public ResponseEntity<HeadServiceResponse> createBoss(@RequestBody HeadServiceCreateRequest request){
        return ResponseEntity.ok(headServiceService.create(request));
    }
    @GetMapping("/find/{status}")
    public ResponseEntity<Set<HeadServiceResponse>> findByStatus(@PathVariable Boolean status){
        return ResponseEntity.ok(headServiceService.findByStatus(status));

    }

    @PutMapping("delete/{code}/{dni}")
    public ResponseEntity<?> deleteHeadService(@PathVariable String code,@PathVariable String dni){
        return ResponseEntity.ok(headServiceService.deleteHeadService(dni,code));

    }
    @GetMapping("/find/{code}/{status}")
    public ResponseEntity<List<HeadServiceResponse>> findByServiceAndStatus(@PathVariable String code, @PathVariable Boolean status){
        return ResponseEntity.ok(headServiceService.findByService(code,status));

    }
}
