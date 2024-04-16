package practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract;

import java.util.Map;

public interface DataGenderAbstract {
    Map<String, Object> countGender();

    Map<String, Object> countGenderService(String code);

}
