package practicas.gestion_personal.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.AssignmentUserServiceResponse;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.domain.entities.AssignmentUserServiceEntity;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;

@Component
public class AssignmentUserServiceMapping {
    ModelMapper modelMapper=new ModelMapper();
    public AssignmentUserServiceMapping() {
    }
    public AssignmentUserServiceResponse assignmentUserServiceResponse(AssignmentUserServiceEntity assignmentUserService){


        AssignmentUserServiceResponse response = modelMapper.map(assignmentUserService,AssignmentUserServiceResponse.class);
        response.getUser().setFullName(assignmentUserService.getUser().getName()+" "+assignmentUserService.getUser().getLastName());


    return response;

}}
