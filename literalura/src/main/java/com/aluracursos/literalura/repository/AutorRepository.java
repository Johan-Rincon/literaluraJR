package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    List<Autor> findIdByNombreContainsIgnoreCase(String nombre);
    List<Autor> findByAnoMuerteGreaterThan(int anoBusqueda);

}
