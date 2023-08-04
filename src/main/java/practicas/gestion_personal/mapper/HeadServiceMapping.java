package practicas.gestion_personal.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;

@Component
public class HeadServiceMapping {
    ModelMapper modelMapper=new ModelMapper();
    public HeadServiceMapping() {
    }
    public HeadServiceResponse HeadServiceEntityToResponse(HeadServiceEntity headService){

    HeadServiceResponse response = modelMapper.map(headService,HeadServiceResponse.class);
    response.getUser().setFullName(headService.getUser().getName()+" "+headService.getUser().getLastName());


    return response;

}}
