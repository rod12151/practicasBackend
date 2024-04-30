package practicas.gestion_personal.api.models.response.dataResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataGenderGraphic implements Serializable {
    private List<String> genders;
    private List<Integer> dats;
    private String name;
}
