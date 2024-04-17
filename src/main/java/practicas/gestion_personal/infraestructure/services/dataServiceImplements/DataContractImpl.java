package practicas.gestion_personal.infraestructure.services.dataServiceImplements;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.repositories.ContractRepository;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.domain.repositories.ServiceRepository;
import practicas.gestion_personal.domain.repositories.WorkConditionRepository;
import practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract.DataContractAbstract;
import practicas.gestion_personal.utils.IdNotFoundException;

import java.util.*;

@Component
@AllArgsConstructor
public class DataContractImpl implements DataContractAbstract {
    private final ServiceRepository serviceRepository;
    private final ContractRepository contractRepository;
    private final LaborRegimeRepository laborRegimeRepository;
    private final WorkConditionRepository workConditionRepository;

    @Override
    public Map<String, Object> countAllContractService() {
         Map<String,Object> response = new HashMap<>();
         List<String> names=new ArrayList<>();
         List<String> codes =new ArrayList<>();
         List<Integer> dats=new ArrayList<>();

        List<String[]> Services=serviceRepository.codeServices();
        for(String[] tag:Services){
            String name = tag[1];
            String code = tag[0];
            Map<String,Object> ser=new HashMap<>();
            ser=countContractService(code);
            Integer data = (Integer) ser.get("data");
            names.add(name);
            codes.add(code);
            dats.add(data);

        }
        response.put("nombres",names);
        response.put("codigos",codes);
        response.put("datos",dats);


        return response;

    }

    @Override
    public Map<String, Object> countContractService(String code) {
        Map<String,Object> response = new HashMap<>();

         Integer data = contractRepository.countContractForService(code);
        response.put("code",code);
        response.put("data",data);


        return response;
    }

    @Override
    public Map<String, Object> countAllContractRegimeLaboral(String codeSer) {
        Optional<ServiceEntity> service = serviceRepository.findByCode(codeSer);
        if (service.isPresent()){
            Map<String,Object> response = new HashMap<>();
            List<String> nameRegimes =  new ArrayList<>();
            List<String> codeRegimes =  new ArrayList<>();
            List<Integer> data = new ArrayList<>();
            List<String[]> codeLaborRegime=laborRegimeRepository.codeRegimen();

            for(String[] tag:codeLaborRegime){
                String code=tag[0];
                String name = tag[1];
                Integer count=contractRepository.countContractForRegime(code,codeSer);
                nameRegimes.add(name);
                data.add(count);
            }
            response.put("nameRegimes",nameRegimes);
            response.put("data",data);
            response.put("code",codeRegimes);

            return response;
        }else {
            throw new IdNotFoundException("Servicio");
        }

    }

    @Override
    public Map<String, Object> countAllContractWorkCondition(String codeSer) {
        Optional<ServiceEntity> service = serviceRepository.findByCode(codeSer);
        if (service.isPresent()){
            Map<String,Object> response = new HashMap<>();
            List<String> nameRegimes =  new ArrayList<>();
            List<String> codeRegimes =  new ArrayList<>();
            List<Integer> data = new ArrayList<>();
            List<String[]> codeWorkCondition=workConditionRepository.codeRegimen();

            for(String[] tag:codeWorkCondition){
                String code=tag[0];
                String name = tag[1];
                Integer count=contractRepository.countContractForWorkCondition(code,codeSer);
                nameRegimes.add(name);
                data.add(count);
                codeRegimes.add(code);
            }
            response.put("nombres",nameRegimes);
            response.put("datos",data);
            response.put("codigos",codeRegimes);

            return response;
        }else {
            throw new IdNotFoundException("Servicio");
        }
    }


}
