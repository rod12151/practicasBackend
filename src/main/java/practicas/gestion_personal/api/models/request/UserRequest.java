package practicas.gestion_personal.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    private String dni;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String profession;
    private String birthDate;

}
