package practicas.gestion_personal.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LaborRegimeRequest implements Serializable {
    private String name;
    private String code;
    private String description;
}
