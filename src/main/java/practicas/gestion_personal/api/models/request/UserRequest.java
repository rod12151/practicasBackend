package practicas.gestion_personal.api.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import practicas.gestion_personal.enums.Genero;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    @Size(min = 8,max = 8,message = "El DNI del usuario tebe tener 8 numeros")
    @NotBlank(message = "El DNI es Obligatorio")
    private String dni;
    @Size(max = 20)
    @NotBlank(message = "el Nombre es obligatorio")
    private String name;
    @Size(max=40)
    @NotBlank(message = "el Apellido es obligatorio")
    private String lastName;
    /*@Size(min = 6,message = "La contraseña debe tener mas de 6 caracteres")
    private String password;*/
    @NotBlank(message = "se debe ingresar una profesion")
    private String profession;
    @Past()
    private LocalDate birthDate;
    private Genero genero;

}
