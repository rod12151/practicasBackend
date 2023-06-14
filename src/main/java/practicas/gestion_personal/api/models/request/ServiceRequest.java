package practicas.gestion_personal.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceRequest implements Serializable {
    @NotBlank(message = "Se debe ingresar un codigo")
    private String code;
    @NotBlank(message = "Se debe ingresar un nombre")
    private String name;
    private String description;
}
