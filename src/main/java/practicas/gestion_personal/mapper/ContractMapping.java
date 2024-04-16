package practicas.gestion_personal.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.ContractResponse;
import practicas.gestion_personal.domain.entities.ContractEntity;

@Component
public class ContractMapping {
    public ContractResponse entityToResponse(ContractEntity entity){

        ModelMapper modelMapper=new ModelMapper();
        //response.getUser().setFullName(entity.getUser().getName()+" "+entity.getUser().getLastName());

        return modelMapper.map(entity,ContractResponse.class);

    }
}
