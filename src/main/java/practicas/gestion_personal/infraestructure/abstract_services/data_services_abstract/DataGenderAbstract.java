package practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract;

import java.util.List;
import java.util.Map;

public interface DataGenderAbstract {
    Map<String, Object> countGender();

    Map<String, Object> countGenderService(String code);

    List<Map<String, Object> > countAllGenderForService();
}
