package practicas.gestion_personal.api.models.response.dataResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenderValue implements Serializable {
    private String gender;
    private int count;
}
