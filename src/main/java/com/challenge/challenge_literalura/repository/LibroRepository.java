package com.challenge.challenge_literalura.repository;

import com.challenge.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Libro findByTituloIgnoreCase(String nombre);
}
