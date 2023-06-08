package practicas.gestion_personal.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractResponse implements Serializable {
    private Long idContract;
    private String dni;
    private String fullName;
    private String nameLaborRegime;
    private String nameWorkCondition;
    private String position;
    private String startDate;
    private String finishDate;

}
