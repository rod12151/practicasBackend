package practicas.gestion_personal.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicas.gestion_personal.api.models.request.ContractRequest;
import practicas.gestion_personal.api.models.response.ContractResponse;
import practicas.gestion_personal.infraestructure.abstract_services.ContractService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/contract")
@AllArgsConstructor
public class ContractController {
    private ContractService contractService;
    @PostMapping("/create")
    public ResponseEntity<ContractResponse> create(@Valid @RequestBody ContractRequest request){
        return ResponseEntity.ok(contractService.createContract(request));
    }
    @GetMapping("/filter/{dni}")
    public  ResponseEntity<?> findByDniUser(@PathVariable String dni){
        return ResponseEntity.ok(contractService.findByUserDni(dni));
    }
    @GetMapping("/filter/regime")
    public  ResponseEntity<Set<ContractResponse>> findByCodeLaborRegime(@RequestParam String code){
        return ResponseEntity.ok(contractService.findByLaborRegimeCode(code));
    }
    @GetMapping("/filter/condition")
    public  ResponseEntity<Set<ContractResponse>> findByCodeWorkCondition(@RequestParam String code){
        return ResponseEntity.ok(contractService.findByWorkConditionCode(code));
    }
    @GetMapping("/filter/after")
    public ResponseEntity<Set<ContractResponse>> filterByStartDateAfter(@RequestParam LocalDate date){
        return ResponseEntity.ok(contractService.findByStartDateAfter(date));
    }
    @GetMapping("/filter/before")
    public ResponseEntity<Set<ContractResponse>> filterByFinishDateBefore(@RequestParam LocalDate date){
        return ResponseEntity.ok(contractService.findByFinishDateBefore(date));
    }
    @GetMapping("/filter/between")
    public ResponseEntity<Set<ContractResponse>> filterByStartDateAndFinishDate(@RequestParam LocalDate startDate,@RequestParam LocalDate finishDate){
        return ResponseEntity.ok(contractService.findByStartDateAndFinishDate(startDate, finishDate));
    }
    @PutMapping("/update")
    public ResponseEntity<ContractResponse> update(@Valid @RequestParam Long id ,@RequestBody ContractRequest request){
        return ResponseEntity.ok(contractService.updateContract(id,request));
    }
    @PutMapping("/terminate/{id}")
    public ResponseEntity<?> terminateContract(@PathVariable Long id){
        String res= contractService.terminateContract(id);
        return ResponseEntity.ok(res);
    }
    @GetMapping("/list/{dni}")
    public ResponseEntity<List<ContractResponse>> findByDniUserAndStatus(@PathVariable String dni, @RequestParam boolean status){
        return ResponseEntity.ok(contractService.listContractUser(dni,status));

    }

}
