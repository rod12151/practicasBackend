package practicas.gestion_personal.api.controllers.dataController;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practicas.gestion_personal.infraestructure.services.dataServiceImplements.DataContractImpl;

import java.util.Map;

@RestController
@RequestMapping("/data/contract")
@AllArgsConstructor
public class DataContractController {
    private final DataContractImpl dataContractImpl;

    @GetMapping("/service")
    public ResponseEntity<Map<String,Object>> findContractService(){
        return ResponseEntity.ok(dataContractImpl.countAllContractService());

    }
    @GetMapping("/regime")
    public ResponseEntity<Map<String,Object>> findContractRegime(@RequestParam String code){
        return ResponseEntity.ok(dataContractImpl.countAllContractRegimeLaboral(code));

    }
}
