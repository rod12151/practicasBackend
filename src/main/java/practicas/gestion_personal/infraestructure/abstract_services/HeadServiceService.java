package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.HeadServiceCreateRequest;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HeadServiceService {
    HeadServiceResponse create(HeadServiceCreateRequest request);
    Set<HeadServiceResponse> findByStatus(Boolean status);
    String deleteHeadService(String dniUser, String codeService);
    List<HeadServiceResponse> findByService(String code, boolean status);

}
