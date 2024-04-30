package practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract;

import practicas.gestion_personal.api.models.response.dataResponse.DataGenderGraphic;

import java.util.List;
import java.util.Map;

public interface DataGenderAbstract {
    Map<String, Object> countGender();
    DataGenderGraphic countAll();
    DataGenderGraphic countGenderService(String code);

    List<DataGenderGraphic> countAllGenderForService();
}
