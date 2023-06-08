package practicas.gestion_personal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "contrato")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContract;
    @Column(name = "puesto",length = 20)
    private String position;
    @Column(name = "inicioContrat")
    private Date startDate;
    @Column(name = "finContrat")
    private Date finishDate;
    /*relation with User*/
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "idUser")
    private UserEntity user;
    /*relation with LaborRegime*/
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "regimenLaboral")
    private LaborRegimeEntity laborRegime;
    /*relation with WorkCondition*/
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "condicionLaboral")
    private WorkConditionEntity workCondition;





}
