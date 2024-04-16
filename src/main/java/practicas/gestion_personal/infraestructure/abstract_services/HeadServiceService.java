package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.HeadServiceCreateRequest;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;

import java.util.List;

import java.util.Map;
import java.util.Set;

public interface HeadServiceService {
    //HeadServiceResponse create(HeadServiceCreateRequest request);
    HeadServiceResponse createBoss(HeadServiceCreateRequest request);
    Set<HeadServiceResponse> findByStatus(Boolean status);
    Map<String,Object> deleteHeadService(String dniUser, String codeService);
    List<HeadServiceResponse> findByService(String code, boolean status);

}
