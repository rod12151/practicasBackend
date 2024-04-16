package practicas.gestion_personal.api.controllers.dataController;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practicas.gestion_personal.infraestructure.services.dataServiceImplements.DataGenderImp;

import java.util.Map;

@RestController
@RequestMapping("/data/gender")
@AllArgsConstructor
public class DataGenderController {
    private DataGenderImp dataGenderImp;
    @GetMapping()
    public ResponseEntity<Map<String,Object>> findUserAssignment(){
        return ResponseEntity.ok(dataGenderImp.countGender());

    }
    @GetMapping("/service")
    public ResponseEntity<Map<String,Object>> findUserAssignmentService(@RequestParam String code){
        return ResponseEntity.ok(dataGenderImp.countGenderService(code));

    }
}
