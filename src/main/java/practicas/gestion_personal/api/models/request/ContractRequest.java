package practicas.gestion_personal.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractRequest implements Serializable {
    private String dniUser;
    private String codeLaborRegime;
    private String codeWorkCondition;
    private String position;
    private Date startDate;
    private Date finishDate;



}
