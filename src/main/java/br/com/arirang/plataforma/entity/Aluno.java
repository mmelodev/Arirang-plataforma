package br.com.arirang.plataforma.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // "registro" para crachás

    private String situacao; // Inativo/Ativo
    private String ultimoNivel; // Último nível (ex.: Básico 2)
    private String nomeCompleto;
    private LocalDateTime dataNascimento;
    private String nomeSocial;
    private String genero;
    private String rg; // Opcional
    private String cpf;
    private String orgaoExpeditorRg;
    private String nacionalidade;
    private String uf;
    private String telefone;
    private String email;

    @Embedded
    private Endereco endereco;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nomeCompleto", column = @Column(name = "responsavel_nome")),
            @AttributeOverride(name = "dataNascimento", column = @Column(name = "responsavel_data_nascimento")),
            @AttributeOverride(name = "rg", column = @Column(name = "responsavel_rg")),
            @AttributeOverride(name = "cpf", column = @Column(name = "responsavel_cpf")),
            @AttributeOverride(name = "telefone", column = @Column(name = "responsavel_telefone"))
    })
    private Responsavel responsavel;
    private String grauParentesco; // Grau de parentesco do responsável

    @ManyToMany(mappedBy = "alunos")
    @JsonBackReference
    private List<Turma> turmas;

    private boolean responsavelFinanceiro;
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }
    public String getUltimoNivel() { return ultimoNivel; }
    public void setUltimoNivel(String ultimoNivel) { this.ultimoNivel = ultimoNivel; }
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public LocalDateTime getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDateTime dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getNomeSocial() { return nomeSocial; }
    public void setNomeSocial(String nomeSocial) { this.nomeSocial = nomeSocial; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getOrgaoExpeditorRg() { return orgaoExpeditorRg; }
    public void setOrgaoExpeditorRg(String orgaoExpeditorRg) { this.orgaoExpeditorRg = orgaoExpeditorRg; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }
    public Responsavel getResponsavel() { return responsavel; }
    public void setResponsavel(Responsavel responsavel) { this.responsavel = responsavel; }
    public String getGrauParentesco() { return grauParentesco; }
    public void setGrauParentesco(String grauParentesco) { this.grauParentesco = grauParentesco; }
    public List<Turma> getTurmas() { return turmas; }
    public void setTurmas(List<Turma> turmas) { this.turmas = turmas; }
    public boolean isResponsavelFinanceiro() { return responsavelFinanceiro; }
    public void setResponsavelFinanceiro(boolean responsavelFinanceiro) { this.responsavelFinanceiro = responsavelFinanceiro; }
}