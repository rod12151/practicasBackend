package practicas.gestion_personal.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignationRequest implements Serializable {
    private String dniBoss;
    private String codeService;
    private String dniUser;
    private LocalDate startDate;
    private LocalDate finishDate;

}
