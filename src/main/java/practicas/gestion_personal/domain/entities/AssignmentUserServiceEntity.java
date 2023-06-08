package practicas.gestion_personal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "usuarioServicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentUserServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAsignacion")
    private Long idAssignment;
    @Column(name = "inicioContrato")
    private Date startDate;
    @Column(name = "finContrato")
    private Date finishDate;
    /*relations with entity User*/
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "idUsuario")
    private UserEntity user;

    /*relations with entity Service*/
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "idServicio")
    private ServiceEntity service;

}
