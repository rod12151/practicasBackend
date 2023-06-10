package practicas.gestion_personal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "jefeServicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeadServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idJefeServicio")
    private Long idHeadService;
    private Boolean status;
    @Column(name = "inicioPosicion")
    private Date startDate;
    @Column(name = "finPosicion")
    private Date finishDate;
    /*relations with entity Service*/
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false)
    @JoinColumn(name = "idServicio")
    private ServiceEntity service;
    /*relations with entity User*/
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "idUsuario")
    private UserEntity user;
}
