package br.com.arirang.plataforma.entity;

import br.com.arirang.plataforma.model.StatusSolicitacao;
import jakarta.persistence.*;

@Entity
public class SolicitacaoMudancaTurma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "turma_nova_id")
    private Turma turmaNova;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusSolicitacao status; // Alterado de String para StatusSolicitacao

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public Turma getTurmaNova() { return turmaNova; }
    public void setTurmaNova(Turma turmaNova) { this.turmaNova = turmaNova; }
    public StatusSolicitacao getStatus() { return status; }
    public void setStatus(StatusSolicitacao status) { this.status = status; }
}