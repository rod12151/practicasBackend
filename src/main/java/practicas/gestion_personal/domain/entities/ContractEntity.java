package practicas.gestion_personal.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity(name = "contrato")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContract;
    @Column(name = "puesto",length = 20)
    private String position;
    @Column(name = "inicioContrat")
    private LocalDate startDate;
    @Column(name = "finContrat")
    private LocalDate finishDate;
    /*relation with User*/
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private UserEntity user;
    /*relation with LaborRegime*/
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "regimenLaboral")
    @JsonIgnore
    private LaborRegimeEntity laborRegime;
    /*relation with WorkCondition*/
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "condicionLaboral")
    @JsonIgnore
    private WorkConditionEntity workCondition;





}
