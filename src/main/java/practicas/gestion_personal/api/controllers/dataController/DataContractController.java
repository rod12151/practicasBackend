package practicas.gestion_personal.api.controllers.dataController;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practicas.gestion_personal.api.models.response.dataResponse.DataGenericGrafic;
import practicas.gestion_personal.infraestructure.services.dataServiceImplements.DataContractImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data/contract")
@AllArgsConstructor
public class DataContractController {
    private final DataContractImpl dataContractImpl;

    @GetMapping("/service")
    public ResponseEntity<DataGenericGrafic> findContractService(){
        return ResponseEntity.ok(dataContractImpl.countAll());

    }
    @GetMapping("/regime")
    public ResponseEntity<DataGenericGrafic> findContractRegime(@RequestParam String code){
        return ResponseEntity.ok(dataContractImpl.countAllContractRegimeLaboral(code));

    }
    @GetMapping("/condition")
    public ResponseEntity<DataGenericGrafic> findContractWorkCondition(@RequestParam String code){
        return ResponseEntity.ok(dataContractImpl.countAllContractWorkCondition(code));

    }
}
