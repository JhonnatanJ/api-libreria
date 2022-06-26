package com.lib.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "detalle")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Detalle {

    @Id
    @Column(name = "id_detalle", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdDetalle;

    @Column(name = "cantidad", nullable = false)
    private int Cantidad;

    @Column(name = "subtotal", nullable = false, scale = 2)
    private Double Subtotal;

    //-------------------------------------------------------------------------------------------------------------------
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "isbn")
    private Libro libro;
}
