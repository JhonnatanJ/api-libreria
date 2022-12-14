package com.lib.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @Column(name = "id_reserva", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_abono", nullable = false)
    private LocalDate fechaAbono;

    @Column(name = "valor_total", nullable = false, scale = 2)
    private Double valorTotal;

    @Column(name = "abono", nullable = false, scale = 2)
    private Double abono;

    @Column(name = "saldo", nullable = false, scale = 2)
    private Double saldo;

    //------------------------------------------------------------------------------------------------------------------------------

    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    @OneToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "ci")
    private Usuario usuario;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "reserva_detalle",
            joinColumns =@JoinColumn(name = "id_reserva"),
            inverseJoinColumns = @JoinColumn(name = "id_detalle_reserva")
    )
    private List<DetalleReserva> detalleReservas;
}
