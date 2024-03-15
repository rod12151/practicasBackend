package practicas.gestion_personal.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkConditionResponse implements Serializable {
    private Long id;
    private String name;
    private String code;
    private String description;
}
