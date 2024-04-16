package practicas.gestion_personal.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Long idUser;
    private String dni;
    private String username;
    private String name;
    private String lastName;
    private String profession;
    private boolean status;

}
