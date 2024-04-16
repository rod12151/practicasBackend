package practicas.gestion_personal.infraestructure.services.dataServiceImplements;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.repositories.ContractRepository;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.domain.repositories.ServiceRepository;
import practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract.DataContractAbstract;
import practicas.gestion_personal.utils.IdNotFoundException;

import java.util.*;

@Component
@AllArgsConstructor
public class DataContractImpl implements DataContractAbstract {
    private final ServiceRepository serviceRepository;
    private final ContractRepository contractRepository;
    private final LaborRegimeRepository laborRegimeRepository;

    @Override
    public Map<String, Object> countAllContractService() {
        Map<String,Object> response = new HashMap<>();
        List<String> service =  new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        String[] codeService=serviceRepository.codeServices();
        for(String tag:codeService){
            Integer valor= contractRepository.countContractForService(tag);
            service.add(tag);
            data.add(valor);
        }
        response.put("servicios",service);
        response.put("data",data);
        return response;

    }

    @Override
    public Map<String, Object> countContractService(String code) {

        return Map.of();
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
}
