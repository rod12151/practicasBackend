package practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract;

import practicas.gestion_personal.api.models.response.dataResponse.DataGenericGrafic;

import java.util.Map;

public interface DataContractAbstract {
    Map<String,Object> countAllContractService();
    Map<String,Object> countContractService(String code);
    DataGenericGrafic countAllContractRegimeLaboralForService(String codeSer);
    DataGenericGrafic countAllContractWorkConditionForService(String codeSer);

    DataGenericGrafic countAllContractOfServices();
    DataGenericGrafic countAllContractOfRegimes();
    DataGenericGrafic countAllContractOfWorks();

}
