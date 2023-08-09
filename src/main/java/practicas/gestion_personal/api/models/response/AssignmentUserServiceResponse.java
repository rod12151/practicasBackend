package practicas.gestion_personal.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentUserServiceResponse implements Serializable {
    private Long idAssignment;
    private LocalDate startDate;
    private LocalDate finishDate;
    private boolean status;
    private UserResponse user;
    private ServiceResponse service;


}
