package practicas.gestion_personal.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;

@Component

public class HeadServiceMapping {
    ModelMapper modelMapper=new ModelMapper();
    public HeadServiceMapping() {
    }
    public HeadServiceResponse headServiceEntityToResponse(HeadServiceEntity headService){

        //response.getUser().setFullName(headService.getUser().getName()+" "+headService.getUser().getLastName());


    return modelMapper.map(headService,HeadServiceResponse.class);

}}
