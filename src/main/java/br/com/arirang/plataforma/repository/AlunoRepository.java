package br.com.arirang.plataforma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.arirang.plataforma.entity.Aluno;
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
	
	
	
}
