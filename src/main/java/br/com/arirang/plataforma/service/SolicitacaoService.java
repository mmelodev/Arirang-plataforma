package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.SolicitacaoMudancaTurma;
import br.com.arirang.plataforma.model.StatusSolicitacao;
import br.com.arirang.plataforma.repository.SolicitacaoMudancaTurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoMudancaTurmaRepository solicitacaoRepository;

    @Transactional
    public SolicitacaoMudancaTurma criarSolicitacao(Long alunoId, Long turmaNovaId) {
        SolicitacaoMudancaTurma solicitacao = new SolicitacaoMudancaTurma();
        // Lógica para associar aluno e turma (a ser implementada)
        solicitacao.setStatus(StatusSolicitacao.PENDENTE); // Status inicial
        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public void aprovarSolicitacao(Long id) {
        SolicitacaoMudancaTurma solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        solicitacao.setStatus(StatusSolicitacao.APROVADO);
        solicitacaoRepository.save(solicitacao);
        // Lógica adicional (ex.: atualizar aluno para nova turma)
    }

    @Transactional
    public void rejeitarSolicitacao(Long id) {
        SolicitacaoMudancaTurma solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        solicitacao.setStatus(StatusSolicitacao.REJEITADO);
        solicitacaoRepository.save(solicitacao);
    }
}