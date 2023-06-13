package practicas.gestion_personal.infraestructure.services;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practicas.gestion_personal.api.models.request.ServiceRequest;
import practicas.gestion_personal.api.models.response.ServiceResponse;
import practicas.gestion_personal.api.models.response.UserResponse;
import practicas.gestion_personal.domain.entities.ServiceEntity;
import practicas.gestion_personal.domain.repositories.ServiceRepository;
import practicas.gestion_personal.infraestructure.abstract_services.ServiceService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private ServiceRepository serviceRepository;
    ModelMapper modelMapper;
    @Override
    @Transactional(readOnly = true)
    public ServiceResponse findByCode(String code) {
        ServiceEntity service = serviceRepository.findByCode(code).orElseThrow();

        return modelMapper.map(service, ServiceResponse.class);

    }

    @Override
    @Transactional(readOnly = true)
    public Set<ServiceResponse> findAll() {
        List<ServiceEntity> services=serviceRepository.findAll();
        Set<ServiceResponse> response =new HashSet<>();
        for (ServiceEntity ser:services){
            response.add(modelMapper.map(ser,ServiceResponse.class));
        }

        return response;
    }

    @Override
    @Transactional()
    public ServiceResponse create(ServiceRequest request) {
        ServiceEntity res=ServiceEntity.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        serviceRepository.save(res);
        return modelMapper.map(res,ServiceResponse.class);
    }

    @Override
    @Transactional()
    public void delete(String code) {
        ServiceEntity service =serviceRepository.findByCode(code).orElseThrow();
        serviceRepository.delete(service);

    }

    @Override
    @Transactional()
    public ServiceResponse update(String code, ServiceRequest request) {
        ServiceEntity serviceUpdate =serviceRepository.findByCode(code).orElseThrow();
        serviceUpdate.setCode(request.getCode());
        serviceUpdate.setName(request.getName());
        serviceUpdate.setDescription(request.getDescription());
        serviceRepository.save(serviceUpdate);
        return modelMapper.map(serviceUpdate,ServiceResponse.class);
    }
}
