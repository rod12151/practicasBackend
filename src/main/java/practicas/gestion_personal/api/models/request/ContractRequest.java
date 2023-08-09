package practicas.gestion_personal.api.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractRequest implements Serializable {
    @Size(min = 8,max = 8,message = "El DNI del usuario tebe tener 8 numeros")
    @NotBlank(message = "El DNI es Obligatorio")
    private String dniUser;
    @Size(max = 8,message = "el numero de caracteres no puede ser mayor que 8")
    @NotBlank(message = "El CODIGO del regimen laboral es Obligatorio")
    private String codeLaborRegime;
    @Size(max = 8,message = "el numero de caracteres no puede ser mayor que 8")
    @NotBlank(message = "El CODIGO de la condición laboral es Obligatorio")
    private String codeWorkCondition;
    @NotBlank(message = "el puesto es obligatorio")
    private String position;
    @PositiveOrZero(message = "El salario no puede ser negativo")
    private Long salary;
    private LocalDate startDate;

    private LocalDate finishDate;



}
