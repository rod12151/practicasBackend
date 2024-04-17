package practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract;

import java.util.List;
import java.util.Map;

public interface DataContractAbstract {
    Map<String,Object> countAllContractService();
    Map<String,Object> countContractService(String code);
    Map<String,Object> countAllContractRegimeLaboral(String codeSer);
    Map<String,Object> countAllContractWorkCondition(String codeSer);
}
