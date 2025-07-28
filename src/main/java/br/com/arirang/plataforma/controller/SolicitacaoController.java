package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.arirang.plataforma.entity.SolicitacaoMudancaTurma;
import br.com.arirang.plataforma.repository.SolicitacaoMudancaTurmaRepository;
import br.com.arirang.plataforma.repository.AlunoRepository;
import br.com.arirang.plataforma.repository.TurmaRepository;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {
    @Autowired
    private SolicitacaoMudancaTurmaRepository solicitacaoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    @PostMapping("/mudanca-turma")
    @Transactional
    public ResponseEntity<String> solicitarMudancaTurma(@RequestBody SolicitacaoMudancaRequest request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        // Forçar inicialização da relação
        Turma turmaAtual = alunoRepository.getOne(request.alunoId()).getTurma();
        if (turmaAtual == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Aluno não está matriculado em uma turma.");
        }
        Long turmaAtualId = turmaAtual.getId();
        Turma turmaNova = turmaRepository.findById(request.turmaNovaId())
                .orElseThrow(() -> new RuntimeException("Turma de destino não encontrada"));

        // Depuração aprimorada
        System.out.println("turmaAtualId (tipo): " + turmaAtualId + " (class: " + turmaAtualId.getClass().getName() + ")");
        System.out.println("turmaNovaId (tipo): " + turmaNova.getId() + " (class: " + turmaNova.getId().getClass().getName() + ")");

        // Validação para mesma turma usando IDs
        if (turmaAtualId.equals(turmaNova.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A nova turma não pode ser a mesma que a atual.");
        }

        SolicitacaoMudancaTurma solicitacao = new SolicitacaoMudancaTurma();
        solicitacao.setAluno(aluno);
        solicitacao.setTurmaAtual(turmaAtual);
        solicitacao.setTurmaNova(turmaNova);
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        solicitacao.setStatus("PENDENTE");
        solicitacaoRepository.save(solicitacao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Solicitação de mudança de turma criada com sucesso. ID: " + solicitacao.getId());
    }

    @PutMapping("/{id}/aprovar")
    @Transactional
    public ResponseEntity<String> aprovarSolicitacao(@PathVariable Long id) {
        SolicitacaoMudancaTurma solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        if (!"PENDENTE".equals(solicitacao.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solicitação não está pendente.");
        }

        Aluno aluno = solicitacao.getAluno();
        aluno.setTurma(solicitacao.getTurmaNova());
        alunoRepository.save(aluno);
        solicitacao.setStatus("APROVADO");
        solicitacaoRepository.save(solicitacao);

        return ResponseEntity.ok("Solicitação aprovada. Aluno movido para a nova turma.");
    }

    @PutMapping("/{id}/rejeitar")
    @Transactional
    public ResponseEntity<String> rejeitarSolicitacao(@PathVariable Long id) {
        SolicitacaoMudancaTurma solicitacao = solicitacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        if (!"PENDENTE".equals(solicitacao.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Solicitação não está pendente.");
        }

        solicitacao.setStatus("REJEITADO");
        solicitacaoRepository.save(solicitacao);

        return ResponseEntity.ok("Solicitação rejeitada.");
    }
}

record SolicitacaoMudancaRequest(Long alunoId, Long turmaNovaId) {}