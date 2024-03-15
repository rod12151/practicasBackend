package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.LaborRegimeResponse;

import java.util.List;
import java.util.Set;

public interface LaborRegimeService {
    LaborRegimeResponse findByCode(String code);
    Set<LaborRegimeResponse> findAll();
    LaborRegimeResponse create(SimpleRequest request);
    LaborRegimeResponse update(String code, SimpleRequest request);
    void delete(String code);
    List<LaborRegimeResponse> findByName(String name);


}
