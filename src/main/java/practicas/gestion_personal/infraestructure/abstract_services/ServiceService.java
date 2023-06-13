package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.ServiceRequest;
import practicas.gestion_personal.api.models.response.ServiceResponse;

import java.util.Set;

public interface ServiceService {
    ServiceResponse findByCode(String code);
    Set<ServiceResponse> findAll();
    ServiceResponse create(ServiceRequest request);
    void delete(String code);

    ServiceResponse update(String code,ServiceRequest request);
}
