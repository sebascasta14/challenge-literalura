package com.challenge.challenge_literalura.repository;

import com.challenge.challenge_literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombre);
    @Query( "SELECT a FROM Autor a WHERE :fecha >= a.fechaNacimiento AND :fecha <= a.fechaMuerte")
    List<Autor> autoresVivosPorFecha(int fecha);
}
