package practicas.gestion_personal.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.ServiceResponse;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;

@Component
public class ServiceMapping {
    public ServiceResponse serviceEntityToResponse(ServiceEntity service){
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper.map(service, ServiceResponse.class);
    }
}
