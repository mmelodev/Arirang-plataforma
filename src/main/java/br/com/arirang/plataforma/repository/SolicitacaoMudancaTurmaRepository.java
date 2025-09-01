package br.com.arirang.plataforma.repository;

import br.com.arirang.plataforma.entity.SolicitacaoMudancaTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoMudancaTurmaRepository extends JpaRepository<SolicitacaoMudancaTurma, Long> {
    void deleteByAlunoId(Long alunoId);
}