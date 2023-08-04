package practicas.gestion_personal;


import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import practicas.gestion_personal.api.models.response.HeadServiceResponse;
import practicas.gestion_personal.api.models.response.ServiceResponse;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.HeadServiceEntity;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.mapper.HeadServiceMapping;
import practicas.gestion_personal.mapper.ServiceMapping;
import practicas.gestion_personal.mapper.UserMapping;

import java.time.LocalDate;

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
        ServiceMapping serviceMapping=new ServiceMapping();
        ServiceEntity service = new ServiceEntity();
        service.setIdService(1L);
        service.setName("pharmacy");
        service.setCode("001FHDAC");
        service.setDescription("farmacia del hospital");

        ServiceResponse serviceResponse= serviceMapping.serviceEntityToResponse(service);

        assertEquals(service.getIdService(),serviceResponse.getId());
        assertEquals(service.getName(),serviceResponse.getName());
        assertEquals(service.getCode(),serviceResponse.getCode());
    }

    @Test
    public void testHeadServiceMapping(){
        HeadServiceMapping headServiceMapping=new HeadServiceMapping();
        HeadServiceEntity headService = new HeadServiceEntity();
        ServiceEntity service = new ServiceEntity();
        UserEntity user = new UserEntity();

        service.setIdService(2L);
        service.setCode("123456aa");
        service.setName("farmacia");

        user.setIdUser(1L);
        user.setDni("78953486");
        user.setName("Joe");
        user.setLastName("doe");
        user.setUsername("123456789@hospital");
        user.setPassword("qwerty");

        headService.setIdHeadService(1L);
        headService.setStartDate(LocalDate.parse("2020-07-25"));
        headService.setStatus(true);
        headService.setFinishDate(LocalDate.parse("2020-07-25"));

        headService.setService(service);
        headService.setUser(user);

        HeadServiceResponse response=headServiceMapping.HeadServiceEntityToResponse(headService);
        assertEquals(headService.getIdHeadService(),response.getIdHeadService());
        assertEquals(headService.getStartDate(),response.getStartDate());
        assertEquals(headService.getService().getIdService(),response.getService().getId());
        assertEquals(headService.getUser().getIdUser(),response.getUser().getIdUser());

    }
}
