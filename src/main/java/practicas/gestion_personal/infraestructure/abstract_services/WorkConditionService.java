package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.LaborRegimeResponse;
import practicas.gestion_personal.api.models.response.WorkConditionResponse;

import java.util.Set;

public interface WorkConditionService {
    WorkConditionResponse findByCode(String code);
    Set<WorkConditionResponse> findAll();
    WorkConditionResponse create(SimpleRequest request);
    WorkConditionResponse update(String code, SimpleRequest request);
    void delete(String code);


}
