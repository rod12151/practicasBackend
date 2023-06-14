package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.infraestructure.abstract_services.ContractService;

import java.time.LocalDate;

@RestController
@RequestMapping("contract")
@AllArgsConstructor
public class ContractController {
    private ContractService contractService;
    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody ContractRequest request){
        return ResponseEntity.ok(contractService.createContract(request));
    }
    @GetMapping("filter/{dni}")
    public  ResponseEntity<?> findByDniUser(@PathVariable String dni){
        return ResponseEntity.ok(contractService.findByUserDni(dni));
    }
    @GetMapping("filter/regime")
    public  ResponseEntity<?> findByCodeLaborRegime(@RequestParam String code){
        return ResponseEntity.ok(contractService.findByLaborRegimeCode(code));
    }
    @GetMapping("filter/condition")
    public  ResponseEntity<?> findByCodeWorkCondition(@RequestParam String code){
        return ResponseEntity.ok(contractService.findByWorkConditionCode(code));
    }
    @GetMapping("/filter/after")
    public ResponseEntity<?> filterByStartDateAfter(@RequestParam LocalDate date){
        return ResponseEntity.ok(contractService.findByStartDateAfter(date));
    }
    @GetMapping("/filter/before")
    public ResponseEntity<?> filterByFinishDateBefore(@RequestParam LocalDate date){
        return ResponseEntity.ok(contractService.findByFinishDateBefore(date));
    }
    @GetMapping("/filter/between")
    public ResponseEntity<?> filterByStartDateAndFinishDate(@RequestParam LocalDate startDate,@RequestParam LocalDate finishDate){
        return ResponseEntity.ok(contractService.findByStartDateAndFinishDate(startDate, finishDate));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestParam Long id ,@RequestBody ContractRequest request){
        return ResponseEntity.ok(contractService.updateContract(id,request));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }

}
