package practicas.gestion_personal.infraestructure.abstract_services;

import practicas.gestion_personal.api.models.request.AssignationRequest;
import practicas.gestion_personal.api.models.response.AssignmentUserServiceResponse;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public interface AssignmentUserServiceService {
    List<AssignmentUserServiceResponse> findAllByStatus(boolean status);
    List<AssignmentUserServiceResponse> findAllByCodeServiceAndStatus(String code,boolean status);
    List<AssignmentUserServiceResponse> findAllByUserAndStatus(String dniUser,boolean status);
    AssignmentUserServiceResponse createAssigment(AssignationRequest request);
    boolean terminateAssignation(String codeService,String dniBoss,String dniUser);
    Set<AssignmentUserServiceResponse> findAllWhitFilter(String assign, String filter);

}
