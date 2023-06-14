package practicas.gestion_personal.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "regimenLaboral")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LaborRegimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLaborRegime;
    @Column(name = "codigo",length = 8,unique = true)
    private String code;
    @Column(name = "nombre",length = 20,nullable = false)
    private String name;
    @Column(name = "descripcion")
    private String description;
    /*relation with Contract*/
    @OneToMany(mappedBy = "laborRegime")
    @JsonIgnore
    private Set<ContractEntity> contracts=new HashSet<>();




}
