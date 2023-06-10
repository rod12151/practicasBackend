package practicas.gestion_personal.mapper;

import org.modelmapper.ModelMapper;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.UserEntity;

public class UserMapping {
    public UserResponse userEntityToResponse(UserEntity user){
        ModelMapper modelMapper=new ModelMapper();
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setFullName(user.getName()+" "+user.getLastName());
        return userResponse;
    }
}
