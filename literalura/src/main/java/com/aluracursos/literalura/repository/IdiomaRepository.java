package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Idioma;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    List<Idioma> findByAbvIdioma (String abvIdioma);
}
