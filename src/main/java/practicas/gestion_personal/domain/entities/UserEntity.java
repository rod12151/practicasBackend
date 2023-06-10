package practicas.gestion_personal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Column(nullable = false,unique = true,length =8 )
    private String dni;
    @Column(name = "nombre",nullable = false,length = 20)
    private String name;
    @Column(name = "apellidos",nullable = false,length = 40)
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "profesion")
    private String profession;
    @Column(name = "fechaNacimiento")
    private LocalDate birthDate;

    /*relation with AssignmentUserService*/
    @OneToMany(mappedBy = "user"
    )
    private Set<AssignmentUserServiceEntity> assignment=new HashSet<>();

    /*relation with HeaderService*/
    @OneToMany(mappedBy = "user"
    )
    private Set<HeadServiceEntity> boss=new HashSet<>();

    /*relation with Contract*/
    @OneToMany(mappedBy = "user")
    private Set<ContractEntity> contracts;
}
