package br.com.arirang.plataforma.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeTurma;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_responsavel_id")
    private Professor professorResponsavel;
    private String nivelProficiencia;
    private String diaTurma;
    private String turno;
    private String formato;
    private String modalidade;
    private String realizador;
    private String horaInicio;
    private String horaTermino;
    private String anoSemestre;
    private Integer cargaHorariaTotal;
    private LocalDateTime inicioTurma;
    private LocalDateTime terminoTurma;
    private String situacaoTurma;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "turma_aluno",
            joinColumns = @JoinColumn(name = "turma_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private List<Aluno> alunos;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeTurma() { return nomeTurma; }
    public void setNomeTurma(String nomeTurma) { this.nomeTurma = nomeTurma; }
    public Professor getProfessorResponsavel() { return professorResponsavel; }
    public void setProfessorResponsavel(Professor professorResponsavel) { this.professorResponsavel = professorResponsavel; }
    public String getNivelProficiencia() { return nivelProficiencia; }
    public void setNivelProficiencia(String nivelProficiencia) { this.nivelProficiencia = nivelProficiencia; }
    public String getDiaTurma() { return diaTurma; }
    public void setDiaTurma(String diaTurma) { this.diaTurma = diaTurma; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }
    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }
    public String getRealizador() { return realizador; }
    public void setRealizador(String realizador) { this.realizador = realizador; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraTermino() { return horaTermino; }
    public void setHoraTermino(String horaTermino) { this.horaTermino = horaTermino; }
    public String getAnoSemestre() { return anoSemestre; }
    public void setAnoSemestre(String anoSemestre) { this.anoSemestre = anoSemestre; }
    public Integer getCargaHorariaTotal() { return cargaHorariaTotal; }
    public void setCargaHorariaTotal(Integer cargaHorariaTotal) { this.cargaHorariaTotal = cargaHorariaTotal; }
    public LocalDateTime getInicioTurma() { return inicioTurma; }
    public void setInicioTurma(LocalDateTime inicioTurma) { this.inicioTurma = inicioTurma; }
    public LocalDateTime getTerminoTurma() { return terminoTurma; }
    public void setTerminoTurma(LocalDateTime terminoTurma) { this.terminoTurma = terminoTurma; }
    public String getSituacaoTurma() { return situacaoTurma; }
    public void setSituacaoTurma(String situacaoTurma) { this.situacaoTurma = situacaoTurma; }
    public List<Aluno> getAlunos() { return alunos; }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }
}