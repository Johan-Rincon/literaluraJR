package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    private Long libroId;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Idioma idioma;
    private Double numDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.libroId = datosLibro.libroId();
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().isEmpty() ?
                new DatosAutor("null", 0, 0) :
                datosLibro.autor().get(0));
        this.idioma = new Idioma(new DatosIdioma(datosLibro.idioma()));
        this.numDescargas = datosLibro.numDescargas();
    }

    @Override
    public String toString() {
        return "----------- LIBRO -----------\nTítulo: " + titulo + "\nAutor: " +
               autor.getNombre() + "\nIdioma: " + idioma.getAbvIdioma() + "\nNúmero de descargas: " + numDescargas +
               "\n-----------------------------";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getNumDescargas() {
        return numDescargas;
    }

    public void setNumDescargas(Double numDescargas) {
        this.numDescargas = numDescargas;
    }
}
