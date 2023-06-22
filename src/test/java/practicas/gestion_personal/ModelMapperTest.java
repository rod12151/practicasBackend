package practicas.gestion_personal;


import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import practicas.gestion_personal.api.models.response.ServiceResponse;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.mapper.UserMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class ModelMapperTest {
    @Test
    public void testUserMapping(){
        UserMapping userMapping = new UserMapping();
        UserEntity user =new UserEntity();
        user.setIdUser(1L);
        user.setDni("78953486");
        user.setName("Joe");
        user.setLastName("doe");
        user.setUsername(user.getDni()+"@hospital.huanta.com");
        UserResponse userResponse= userMapping.userEntityToResponse(user);

        assertEquals(user.getIdUser(),userResponse.getIdUser());
        assertEquals(user.getDni(),userResponse.getDni());
        assertEquals(user.getUsername(),userResponse.getUsername());
        assertEquals(user.getName()+" "+user.getLastName(),userResponse.getFullName());
    }
    @Test
    public void testServiceMapping(){
        ModelMapper modelMapper=new ModelMapper();
        ServiceEntity service = new ServiceEntity();
        service.setIdService(1L);
        service.setName("pharmacy");
        service.setCode("001FHDAC");
        service.setDescription("farmacia del hospital");

        ServiceResponse serviceResponse= modelMapper.map(service,ServiceResponse.class);

        assertEquals(service.getIdService(),serviceResponse.getId());
        assertEquals(service.getName(),serviceResponse.getName());
        assertEquals(service.getCode(),serviceResponse.getCode());
    }
}
