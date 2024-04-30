package practicas.gestion_personal.infraestructure.services.dataServiceImplements;

import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.api.models.response.dataResponse.DataGenderGraphic;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.repositories.ServiceRepository;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.enums.Genero;
import practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract.DataGenderAbstract;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.ListForDatsGenderGraphics;

import java.util.*;

import static practicas.gestion_personal.enums.Genero.FEMENINO;
import static practicas.gestion_personal.enums.Genero.MASCULINO;

@Component
@AllArgsConstructor
public class DataGenderImp implements DataGenderAbstract {
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public Map<String, Object> countGender() {
        Map<String, Object> response = new HashMap<>();
        List<String> gender = new ArrayList<>();
        List<Integer> date = new ArrayList<>();
        Genero[] generos = {MASCULINO, FEMENINO};
        for (Genero tag : generos) {
            Integer num = userRepository.countGender(tag);
            gender.add(String.valueOf(tag));
            date.add(num);

        }
        response.put("generos", gender);
        response.put("date", date);
        return response;
    }

    @Override
    public DataGenderGraphic countAll() {
        DataGenderGraphic response = new DataGenderGraphic();
        ListForDatsGenderGraphics lists = new ListForDatsGenderGraphics();
        Genero[] generos = {MASCULINO, FEMENINO};
        for (Genero tag : generos) {
            Integer num = userRepository.countGender(tag);
            lists.getGenders().add(String.valueOf(tag));
            lists.getDats().add(num);

        }
        response.setDats(lists.getDats());
        response.setGenders(lists.getGenders());

        return response;
    }

    @Override
    public DataGenderGraphic countGenderService(String code) {
        //Enfer001
        Optional<ServiceEntity> service = serviceRepository.findByCode(code);
        if (service.isPresent()) {
            DataGenderGraphic response = new DataGenderGraphic();
            ListForDatsGenderGraphics lists = new ListForDatsGenderGraphics();
            Genero[] generos = {MASCULINO, FEMENINO};

            for (Genero tag : generos) {
                Integer num = userRepository.countGenderService(tag, code);
                lists.getGenders().add(String.valueOf(tag));
                lists.getDats().add(num);

            }
            response.setGenders(lists.getGenders());
            response.setDats(lists.getDats());
            return response;

        } else {
            throw new IdNotFoundException("Servicio");
        }


    }

    @Override
    public List<DataGenderGraphic> countAllGenderForService() {
        List<DataGenderGraphic> response = new ArrayList<>();
        List<String[]> codeServices = serviceRepository.codeServices();
        for (String[] service : codeServices) {
            String name = service[1];
            String code = service[0];

            DataGenderGraphic responseAux = new DataGenderGraphic();

            responseAux = (countGenderService(code));
            responseAux.setName(name);
            response.add(responseAux);

        }
        return response;
    }
}
