package practicas.gestion_personal.infraestructure.services.dataServiceImplements;

import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.stereotype.Component;
import practicas.gestion_personal.domain.repositories.UserRepository;
import practicas.gestion_personal.enums.Genero;
import practicas.gestion_personal.infraestructure.abstract_services.data_services_abstract.DataGenderAbstract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static practicas.gestion_personal.enums.Genero.FEMENINO;
import static practicas.gestion_personal.enums.Genero.MASCULINO;

@Component
@AllArgsConstructor
public class DataGenderImp implements DataGenderAbstract {
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> countGender() {
        Map<String,Object> response = new HashMap<>();
        List<String> gender =  new ArrayList<>();
        List<Integer> date = new ArrayList<>();
        Genero[] generos={MASCULINO,FEMENINO};
        for (Genero tag : generos) {
            Integer num = userRepository.countGender(tag);
            gender.add(String.valueOf(tag));
            date.add(num);

        }
        response.put("generos",gender);
        response.put("date",date);
        return response;
    }

    @Override
    public Map<String, Object> countGenderService(String code) {
        //Enfer001
        Map<String,Object> response = new HashMap<>();
        List<String> gender =  new ArrayList<>();
        List<Integer> date = new ArrayList<>();
        Genero[] generos={MASCULINO,FEMENINO};
        for (Genero tag : generos) {
            Integer num = userRepository.countGenderService(tag,code);
            gender.add(String.valueOf(tag));
            date.add(num);

        }
        response.put("generos",gender);
        response.put("date",date);
        return response;
    }
}
