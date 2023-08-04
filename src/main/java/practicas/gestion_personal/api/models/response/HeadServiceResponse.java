package practicas.gestion_personal.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HeadServiceResponse {
    private Long idHeadService;
    private LocalDate startDate;
    private LocalDate finishDate;
    private ServiceResponse service;
    private UserResponse user;

}
