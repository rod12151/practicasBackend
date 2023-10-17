package practicas.gestion_personal.api.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SimpleRequest implements Serializable {
    @NotBlank(message = "Se debe ingresar un nombre")
    @Size(max = 20,message = "el tamaño del nombre no puede ser mayor a 20 digitos")
    private String name;
    @NotBlank(message = "Se debe ingresar el codigo")
    @Size(min = 8,max = 8,message = "el Codigo debe tener 8 digitos")
    private String code;
    private String description;
}
