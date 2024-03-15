package practicas.gestion_personal.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractResponse implements Serializable {

    private Long idContract;
    private String position;
    private String startDate;
    private String finishDate;
    private boolean status;
    private Long salary;
    private UserResponse user;
    private WorkConditionResponse workCondition;
    private LaborRegimeResponse laborRegime;


}
