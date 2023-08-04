package practicas.gestion_personal.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "servicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idService;
    @Column(name = "codigo",nullable = false,length = 8,unique = true)
    private String code;
    @Column(name = "nombre",nullable = false,length = 50)
    private String name;
    @Column(name = "descripcion")
    private String description;

    /*relations with entity AssignmentUserService*/
    @OneToMany(mappedBy = "service"    )
    @JsonIgnore
    private Set<AssignmentUserServiceEntity> assignment=new HashSet<>();
    /*relations with entity HeadService*/
    @OneToMany(mappedBy = "service"    )
    @JsonIgnore
    private Set<HeadServiceEntity> boss =new HashSet<>();
}
