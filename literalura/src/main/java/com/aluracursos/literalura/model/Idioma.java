package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "idioma")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  int id;
    private String abvIdioma;
    @OneToMany(mappedBy = "idioma", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Libro> libro;

    public Idioma() {
    }

    public Idioma(DatosIdioma idioma) {
        this.abvIdioma = idioma.idiomas().get(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbvIdioma() {
        return abvIdioma;
    }

    public void setAbvIdioma(String abvIdioma) {
        this.abvIdioma = abvIdioma;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        libro.forEach(l -> l.setIdioma(this));
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "" + libro;
    }
}
