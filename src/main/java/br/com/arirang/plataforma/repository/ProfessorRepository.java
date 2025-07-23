package br.com.arirang.plataforma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.arirang.plataforma.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}