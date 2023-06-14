package practicas.gestion_personal.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleRequest implements Serializable {
    @NotBlank(message = "Se debe ingresar un nombre")
    private String name;
    @NotBlank(message = "Se debe ingresar el codigo")
    private String code;
    private String description;
}
