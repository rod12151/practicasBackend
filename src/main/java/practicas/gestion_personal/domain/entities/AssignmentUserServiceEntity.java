package practicas.gestion_personal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "usuarioServicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AssignmentUserServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAsignacion")
    private Long idAssignment;
    @Column(name = "inicioAsignacion")
    private LocalDate startDate;
    @Column(name = "finAsignacion")
    private LocalDate finishDate;
    private boolean status;
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
