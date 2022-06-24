package com.lib.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "libro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro implements Serializable {
    @Id
    @Column(name = "ISBN", nullable = false, length = 10)
    private String ISBN;

    @Column(name = "titulo", nullable = false, length = 600)
    private String Titulo;

    @Column(name = "descripcion",length = 5000)
    private String Descripcion;

    @Column(name = "stock", nullable = false)
    private String Stock;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal PrecioUnitario;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private List<Autor> autores;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libros_generos",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "id_genero")
    )
    private List<Genero> generos;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "libros_editoriales",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "id_editorial")
    )
    private List<Editorial> editoriales;


//-------------------------------------- MÉTODOS ---------------------
    //MÉTODO AGREGAR AUTORES A LA ENTIDAD
    public void AddAutores(List<Autor> newAutores){
        autores.clear();
        for (Autor autor: newAutores) {
            autores.add(autor);
        }
    }

    //MÉTODO AGREGAR GENEROS A LA ENTIDAD
    public void AddGeneros(List<Genero> newGeneros){
        generos.clear();
        for (Genero genero: newGeneros) {
            generos.add(genero);
        }
    }
    //MÉTODO AGREGAR EDITORIALES A LA ENTIDAD
    public void AddEditoriales(List<Editorial> newEditoriales){
        editoriales.clear();
        for (Editorial editorial: newEditoriales) {
            editoriales.add(editorial);
        }
    }
}
