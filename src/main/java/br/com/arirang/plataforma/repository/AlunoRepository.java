package br.com.arirang.plataforma.repository;

import br.com.arirang.plataforma.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Métodos adicionais podem ser adicionados aqui se necessário
}