package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.LaborRegimeResponse;
import practicas.gestion_personal.api.models.response.WorkConditionResponse;
import practicas.gestion_personal.domain.entities.LaborRegimeEntity;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;
import practicas.gestion_personal.domain.repositories.LaborRegimeRepository;
import practicas.gestion_personal.infraestructure.abstract_services.LaborRegimeService;
import practicas.gestion_personal.utils.IdDuplicate;
import practicas.gestion_personal.utils.IdNotFoundException;


import java.util.*;


@Service
@AllArgsConstructor
public class LaborRegimeImpl implements LaborRegimeService {
    private LaborRegimeRepository laborRegimeRepository;
    private ModelMapper modelMapper;
    @Override
    public LaborRegimeResponse findByCode(String code) {
        LaborRegimeEntity laborRegime =laborRegimeRepository.findByCode(code).orElseThrow(getIdNotFoundExceptionSupplier());

        return modelMapper.map(laborRegime, LaborRegimeResponse.class);
    }

    @Override
    public Set<LaborRegimeResponse> findAll() {
        List<LaborRegimeEntity> entities=laborRegimeRepository.findAll();
        Set<LaborRegimeResponse> response =new HashSet<>();
        for (LaborRegimeEntity i:entities){
            response.add(modelMapper.map(i, LaborRegimeResponse.class));
        }
        return response;
    }

    @Override
    public LaborRegimeResponse create(SimpleRequest request) {
        Optional<LaborRegimeEntity> laborRe= laborRegimeRepository.findByCode(request.getCode());
        if( laborRe.isEmpty()){
        LaborRegimeEntity laborRegime = LaborRegimeEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
        laborRegimeRepository.save(laborRegime);
        return modelMapper.map(laborRegime, LaborRegimeResponse.class);

    }else {
            throw new IdDuplicate("codigo "+request.getCode());
        }
    }

    @Override
    public LaborRegimeResponse update(String code, SimpleRequest request) {

        LaborRegimeEntity laborRegimeUpdate=laborRegimeRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("laborRegime"));
        String codeNuevo=request.getCode();
        if (!code.equals(codeNuevo)){
            Optional<LaborRegimeEntity> laborRegimeAux=laborRegimeRepository.findByCode(codeNuevo);
            if (laborRegimeAux.isPresent()){
                throw new  IdDuplicate("código: "+ request.getCode());
            }else{
                laborRegimeUpdate.setCode(codeNuevo);
                laborRegimeUpdate.setName(request.getName());
                laborRegimeUpdate.setDescription(request.getDescription());

            }

        }else {
            laborRegimeUpdate.setName(request.getName());
            laborRegimeUpdate.setDescription(request.getDescription());
        }



        laborRegimeRepository.save(laborRegimeUpdate);
        return modelMapper.map(laborRegimeUpdate, LaborRegimeResponse.class);
    }

    @Override
    public void delete(String code) {
        LaborRegimeEntity laborRegime = laborRegimeRepository.findByCode(code).orElseThrow(getIdNotFoundExceptionSupplier());
        laborRegimeRepository.delete(laborRegime);

    }


    @Override
    public List<LaborRegimeResponse> findByName(String name) {
        var data = laborRegimeRepository.findByNameContains(name);
        List<LaborRegimeResponse> response= new ArrayList<>();
        for(LaborRegimeEntity res:data){
            LaborRegimeResponse aux=modelMapper.map(res,LaborRegimeResponse.class);
            response.add(aux);
        }
        return response;

}
