package practicas.gestion_personal.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignationRequest implements Serializable {
    private String codeService;
    private String dniUser;
    private Date startDate;
    private Date finishDate;

}
