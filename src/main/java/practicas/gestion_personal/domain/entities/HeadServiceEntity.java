package practicas.gestion_personal.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "jefeServicio")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HeadServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idJefeServicio")
    private Long idHeadService;
    private Boolean status;
    @Column(name = "inicioPosicion")
    private LocalDate startDate;
    @Column(name = "finPosicion")
    private LocalDate finishDate;
    /*relations with entity Service*/
    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false)
    @JoinColumn(name = "idServicio")
    //@JsonIgnore
    private ServiceEntity service;
    /*relations with entity User*/
    @ManyToOne(
            fetch = FetchType.EAGER,
            optional = false
    )
    @JoinColumn(name = "idUsuario")
    private UserEntity user;
}
