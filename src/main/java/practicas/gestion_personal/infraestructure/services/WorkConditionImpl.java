package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.api.models.response.WorkConditionResponse;
import practicas.gestion_personal.domain.entities.UserEntity;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;
import practicas.gestion_personal.domain.repositories.WorkConditionRepository;
import practicas.gestion_personal.infraestructure.abstract_services.WorkConditionService;
import practicas.gestion_personal.utils.IdDuplicate;
import practicas.gestion_personal.utils.IdNotFoundException;
import practicas.gestion_personal.utils.UserDuplicate;


import java.lang.reflect.Type;
import java.util.*;


@Service
@AllArgsConstructor
public class WorkConditionImpl implements WorkConditionService {
    private WorkConditionRepository workConditionRepository;
    private ModelMapper modelMapper;
    @Override
    public WorkConditionResponse findByCode(String code) {
        WorkConditionEntity workCondition =workConditionRepository.findByCode(code).orElseThrow(getIdNotFoundExceptionSupplier());

        return modelMapper.map(workCondition, WorkConditionResponse.class);
    }

    @Override
    public Set<WorkConditionResponse> findAll() {
        List<WorkConditionEntity> entities=workConditionRepository.findAll();
        Set<WorkConditionResponse> response =new HashSet<>();
        for (WorkConditionEntity i:entities){
            response.add(modelMapper.map(i, WorkConditionResponse.class));
        }
        return response;
    }

    @Override
    public WorkConditionResponse create(SimpleRequest request) {
        Optional<WorkConditionEntity> work =workConditionRepository.findByCode(request.getCode());
        if (work.isEmpty()) {
            WorkConditionEntity workCondition = WorkConditionEntity.builder()
                    .name(request.getName())
                    .code(request.getCode())
                    .description(request.getDescription())
                    .build();
            workConditionRepository.save(workCondition);
            return modelMapper.map(workCondition, WorkConditionResponse.class);
        }else {
            throw new IdDuplicate("codigo "+request.getCode());
        }
    }

    @Override
    public WorkConditionResponse update(String code, SimpleRequest request) {
        WorkConditionEntity workConditionUpdate=workConditionRepository.findByCode(code).orElseThrow(getIdNotFoundExceptionSupplier());
        workConditionUpdate.setCode(request.getCode());
        workConditionUpdate.setName(request.getName());
        workConditionUpdate.setDescription(request.getDescription());
        workConditionRepository.save(workConditionUpdate);
        return modelMapper.map(workConditionUpdate, WorkConditionResponse.class);
    }

    @Override
    public void delete(String code) {
        WorkConditionEntity workCondition = workConditionRepository.findByCode(code).orElseThrow(getIdNotFoundExceptionSupplier());
        workConditionRepository.delete(workCondition);

    }


    @Override
    public List<WorkConditionResponse> findByName(String name) {
        var data = workConditionRepository.findByNameContains(name);
        List<WorkConditionResponse> response= new ArrayList<>();
        for(WorkConditionEntity res:data){
            WorkConditionResponse aux=modelMapper.map(res,WorkConditionResponse.class);
            response.add(aux);
        }
        return response;
    }

}
