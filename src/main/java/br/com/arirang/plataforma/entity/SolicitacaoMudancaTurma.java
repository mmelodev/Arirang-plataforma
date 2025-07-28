package br.com.arirang.plataforma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
public class SolicitacaoMudancaTurma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "turma_atual_id")
    private Turma turmaAtual;
    @ManyToOne
    @JoinColumn(name = "turma_nova_id")
    private Turma turmaNova;
    private LocalDateTime dataSolicitacao;
    private String status; // Ex.: "PENDENTE", "APROVADO", "REJEITADO"

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public Turma getTurmaAtual() { return turmaAtual; }
    public void setTurmaAtual(Turma turmaAtual) { this.turmaAtual = turmaAtual; }
    public Turma getTurmaNova() { return turmaNova; }
    public void setTurmaNova(Turma turmaNova) { this.turmaNova = turmaNova; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}