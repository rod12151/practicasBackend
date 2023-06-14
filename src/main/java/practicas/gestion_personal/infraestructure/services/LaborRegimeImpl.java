package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.SimpleResponse;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.infraestructure.abstract_services.LaborRegimeService;
import practicas.gestion_personal.utils.IdNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class LaborRegimeImpl implements LaborRegimeService {
    private LaborRegimeRepository laborRegimeRepository;
    private ModelMapper modelMapper;
    @Override
    public SimpleResponse findByCode(String code) {
        LaborRegimeEntity laborRegime =laborRegimeRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("laborRegime"));

        return modelMapper.map(laborRegime,SimpleResponse.class);
    }

    @Override
    public Set<SimpleResponse> findAll() {
        List<LaborRegimeEntity> entities=laborRegimeRepository.findAll();
        Set<SimpleResponse> response =new HashSet<>();
        for (LaborRegimeEntity i:entities){
            response.add(modelMapper.map(i,SimpleResponse.class));
        }
        return response;
    }

    @Override
    public SimpleResponse create(SimpleRequest request) {
        LaborRegimeEntity laborRegime = LaborRegimeEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
        laborRegimeRepository.save(laborRegime);
        return modelMapper.map(laborRegime,SimpleResponse.class);
    }

    @Override
    public SimpleResponse update(String code, SimpleRequest request) {
        LaborRegimeEntity laborRegimeUpdate=laborRegimeRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("laborRegime"));
        laborRegimeUpdate.setCode(request.getCode());
        laborRegimeUpdate.setName(request.getName());
        laborRegimeUpdate.setDescription(request.getDescription());
        laborRegimeRepository.save(laborRegimeUpdate);
        return modelMapper.map(laborRegimeUpdate,SimpleResponse.class);
    }

    @Override
    public void delete(String code) {
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("laborRegime"));
        laborRegimeRepository.delete(laborRegime);

    }
}
