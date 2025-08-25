package br.com.arirang.plataforma.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;
    private String email;
    private String cpf;
    private String rg;
    private String orgaoExpeditorRg;
    private String nacionalidade;
    private String uf;
    private String telefone;
    private LocalDateTime dataNascimento;
    private String nomeSocial;
    private String genero;
    private String situacao;
    private String ultimoNivel;
    @Embedded
    private Endereco endereco;
    private String grauParentesco;
    private boolean responsavelFinanceiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Responsavel responsavel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "aluno_turma",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "turma_id")
    )
    private List<Turma> turmas;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getOrgaoExpeditorRg() { return orgaoExpeditorRg; }
    public void setOrgaoExpeditorRg(String orgaoExpeditorRg) { this.orgaoExpeditorRg = orgaoExpeditorRg; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDateTime getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDateTime dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getNomeSocial() { return nomeSocial; }
    public void setNomeSocial(String nomeSocial) { this.nomeSocial = nomeSocial; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
    public String getUltimoNivel() { return ultimoNivel; }
    public void setUltimoNivel(String ultimoNivel) { this.ultimoNivel = ultimoNivel; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public String getGrauParentesco() { return grauParentesco; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }
    public boolean isResponsavelFinanceiro() { return responsavelFinanceiro; }
    public void setResponsavelFinanceiro(boolean responsavelFinanceiro) { this.responsavelFinanceiro = responsavelFinanceiro; }
    public Responsavel getResponsavel() { return responsavel; }
    public void setResponsavel(Responsavel responsavel) { this.responsavel = responsavel; }
    public List<Turma> getTurmas() { return turmas; }
    public void setTurmas(List<Turma> turmas) { this.turmas = turmas; }
}