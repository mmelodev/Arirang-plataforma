package br.com.arirang.plataforma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.arirang.plataforma.entity.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}