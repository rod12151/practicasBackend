package practicas.gestion_personal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "condicionLaboral")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWorkCondition;
    @Column(name = "codigo",length = 8,unique = true)
    private String code;
    @Column(name = "nombre",length = 20,nullable = false)
    private String name;
    @Column(name = "descripcion")
    private String description;
    /*relation with workCondition*/
    @OneToMany(mappedBy = "workCondition")
    private Set<ContractEntity> contract=new HashSet<>();


}
