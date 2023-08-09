package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import practicas.gestion_personal.api.models.request.SimpleRequest;
import practicas.gestion_personal.api.models.response.LaborRegimeResponse;
import practicas.gestion_personal.api.models.response.WorkConditionResponse;
import practicas.gestion_personal.domain.entities.WorkConditionEntity;
import practicas.gestion_personal.domain.repositories.WorkConditionRepository;
import practicas.gestion_personal.infraestructure.abstract_services.WorkConditionService;
import practicas.gestion_personal.utils.IdNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class WorkConditionImpl implements WorkConditionService {
    private WorkConditionRepository workConditionRepository;
    private ModelMapper modelMapper;
    @Override
    public WorkConditionResponse findByCode(String code) {
        WorkConditionEntity workCondition =workConditionRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("workCondition"));

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
        WorkConditionEntity workCondition = WorkConditionEntity.builder()
                .name(request.getName())
                .code(request.getCode())
                .description(request.getDescription())
                .build();
        workConditionRepository.save(workCondition);
        return modelMapper.map(workCondition, WorkConditionResponse.class);
    }

    @Override
    public WorkConditionResponse update(String code, SimpleRequest request) {
        WorkConditionEntity workConditionUpdate=workConditionRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("workCondition"));
        workConditionUpdate.setCode(request.getCode());
        workConditionUpdate.setName(request.getName());
        workConditionUpdate.setDescription(request.getDescription());
        workConditionRepository.save(workConditionUpdate);
        return modelMapper.map(workConditionUpdate, WorkConditionResponse.class);
    }

    @Override
    public void delete(String code) {
        WorkConditionEntity workCondition = workConditionRepository.findByCode(code).orElseThrow(()->new IdNotFoundException("workCondition"));
        workConditionRepository.delete(workCondition);

    }
}
