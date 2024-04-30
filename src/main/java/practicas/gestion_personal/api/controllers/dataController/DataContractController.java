package practicas.gestion_personal.api.controllers.dataController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practicas.gestion_personal.api.models.response.dataResponse.DataGenericGrafic;
import practicas.gestion_personal.infraestructure.services.dataServiceImplements.DataContractImpl;

@RestController
@RequestMapping("/data/contract")
@AllArgsConstructor
public class DataContractController {
    private final DataContractImpl dataContractImpl;
    @Operation(summary = "devuelve la cantidad de usuarios asignados a cada servicio")

    @GetMapping("/service")
    public ResponseEntity<DataGenericGrafic> findContractService(){
        return ResponseEntity.ok(dataContractImpl.countAllContractOfServices());

    }
    @Operation(summary = "devuelve la cantidad de usuarios asignados en cada regimen laboral pertenecientes a un servicio")


    @GetMapping("/regime")
    public ResponseEntity<DataGenericGrafic> findContractRegimeForService(@RequestParam String code){
        return ResponseEntity.ok(dataContractImpl.countAllContractRegimeLaboralForService(code));

    }
    @Operation(summary = "devuelve la cantidad de usuarios asignados en cada Regimen laboral")

    @GetMapping("/regime/all")
    public ResponseEntity<DataGenericGrafic> findAllContractRegime(){
        return ResponseEntity.ok(dataContractImpl.countAllContractOfRegimes());

    }
    @Operation(summary = "devuelve la cantidad de usuarios asignados en cada condicion laboral pertenecientes a un servicio")
    @GetMapping("/condition")
    public ResponseEntity<DataGenericGrafic> findContractWorkConditionForService(@RequestParam String code){
        return ResponseEntity.ok(dataContractImpl.countAllContractWorkConditionForService(code));

    }
    @Operation(summary = "devuelve la cantidad de usuarios asignados en cada condicion laboral")
    @GetMapping("/condition/all")
    public ResponseEntity<DataGenericGrafic> findAllContractWorkCondition(){
        return ResponseEntity.ok(dataContractImpl.countAllContractOfWorks());

    }
}
