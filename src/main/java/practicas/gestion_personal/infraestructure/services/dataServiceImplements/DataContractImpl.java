package practicas.gestion_personal.infraestructure.services.dataServiceImplements;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.dataResponse.DataGenericGrafic;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.repositories.ContractRepository;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.domain.repositories.ServiceRepository;
import practicas.gestion_personal.domain.repositories.WorkConditionRepository;
import practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract.DataContractAbstract;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.ListForDatsGraphics;

import javax.xml.crypto.Data;
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
        response.put("names",names);
        response.put("codes",codes);
        response.put("dats",dats);


        return response;

    }
    @Override
    public DataGenericGrafic countAll() {
        DataGenericGrafic response = new DataGenericGrafic();
        ListForDatsGraphics lists=new ListForDatsGraphics();
        List<String[]> Services=serviceRepository.codeServices();
        for(String[] tag:Services){
            String name = tag[1];
            String code = tag[0];
            Map<String,Object> ser=new HashMap<>();
            ser=countContractService(code);
            Integer data = (Integer) ser.get("data");
            lists.getNames().add(name);

            lists.getCodes().add(code);

            lists.getDats().add(data);


        }
        response.setCodes(lists.getCodes());
        response.setDats(lists.getDats());
        response.setNames(lists.getNames());

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
    public DataGenericGrafic countAllContractRegimeLaboral(String codeSer) {
        Optional<ServiceEntity> service = serviceRepository.findByCode(codeSer);
        if (service.isPresent()){
            DataGenericGrafic response = new DataGenericGrafic();
            ListForDatsGraphics lists=new ListForDatsGraphics();
            List<String[]> codeLaborRegime=laborRegimeRepository.codeRegimen();

            for(String[] tag:codeLaborRegime){
                String code=tag[0];
                String name = tag[1];
                Integer count=contractRepository.countContractForRegime(code,codeSer);
                lists.getDats().add(count);
                lists.getNames().add(name);
                lists.getCodes().add(code);
            }
            response.setCodes(lists.getCodes());
            response.setNames(lists.getNames());
            response.setDats(lists.getDats());

            return response;
        }else {
            throw new IdNotFoundException("Servicio");
        }

    }

    @Override
    public DataGenericGrafic countAllContractWorkCondition(String codeSer) {
        Optional<ServiceEntity> service = serviceRepository.findByCode(codeSer);
        if (service.isPresent()){
            DataGenericGrafic response = new DataGenericGrafic();
            ListForDatsGraphics lists=new ListForDatsGraphics();
            List<String[]> codeLaborRegime=laborRegimeRepository.codeRegimen();

            for(String[] tag:codeLaborRegime){
                String code=tag[0];
                String name = tag[1];
                Integer count=contractRepository.countContractForWorkCondition(code,codeSer);
                lists.getDats().add(count);
                lists.getNames().add(name);
                lists.getCodes().add(code);
            }
            response.setCodes(lists.getCodes());
            response.setNames(lists.getNames());
            response.setDats(lists.getDats());

            return response;

        }else {
            throw new IdNotFoundException("Servicio");
        }


    }




}
