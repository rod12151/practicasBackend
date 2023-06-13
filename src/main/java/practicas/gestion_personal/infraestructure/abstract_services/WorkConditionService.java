package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.SimpleResponse;

import java.util.Set;

public interface WorkConditionService {
    SimpleResponse findByCode(String code);
    Set<SimpleResponse> findAll();
    SimpleResponse create(SimpleRequest request);
    SimpleResponse update(String code, SimpleRequest request);
    void delete(String code);


}
