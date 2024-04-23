package practicas.gestion_personal.api.models.response.dataResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataGenericGrafic {
    private List<String> names;
    private List<String> codes;
    private List<Integer> dats;
}
