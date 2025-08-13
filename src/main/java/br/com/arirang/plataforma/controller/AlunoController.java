package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Responsavel;
import br.com.arirang.plataforma.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.arirang.plataforma.entity.Turma;

@Controller // Mantido como @Controller para suportar Thymeleaf
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarTodosAlunos() {
        List<AlunoDTO> alunos = alunoService.listarTodosAlunos().stream()
                .map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNomeCompleto(),
                        aluno.getEmail(),
                        aluno.getCpf(),
                        aluno.getRg(),
                        aluno.getOrgaoExpeditorRg(),
                        aluno.getNacionalidade(),
                        aluno.getUf(),
                        aluno.getTelefone(),
                        aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                        aluno.getNomeSocial(),
                        aluno.getGenero(),
                        aluno.getSituacao(),
                        aluno.getUltimoNivel(),
                        aluno.getEndereco(),
                        aluno.getResponsavel(),
                        aluno.getGrauParentesco(),
                        aluno.isResponsavelFinanceiro(),
                        aluno.getTurmas() != null ? aluno.getTurmas().stream().map(Turma::getId).collect(Collectors.toList()) : null
                )).collect(Collectors.toList());
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/lista")
    public String listarAlunos(Model model) {
        model.addAttribute("alunos", alunoService.listarTodosAlunos().stream()
                .map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNomeCompleto(),
                        aluno.getEmail(),
                        aluno.getCpf(),
                        aluno.getRg(),
                        aluno.getOrgaoExpeditorRg(),
                        aluno.getNacionalidade(),
                        aluno.getUf(),
                        aluno.getTelefone(),
                        aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                        aluno.getNomeSocial(),
                        aluno.getGenero(),
                        aluno.getSituacao(),
                        aluno.getUltimoNivel(),
                        aluno.getEndereco(),
                        aluno.getResponsavel(),
                        aluno.getGrauParentesco(),
                        aluno.isResponsavelFinanceiro(),
                        aluno.getTurmas() != null ? aluno.getTurmas().stream().map(Turma::getId).collect(Collectors.toList()) : null
                )).collect(Collectors.toList()));
        return "alunos"; // Template Thymeleaf
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunoPorId(id)
                .map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNomeCompleto(),
                        aluno.getEmail(),
                        aluno.getCpf(),
                        aluno.getRg(),
                        aluno.getOrgaoExpeditorRg(),
                        aluno.getNacionalidade(),
                        aluno.getUf(),
                        aluno.getTelefone(),
                        aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                        aluno.getNomeSocial(),
                        aluno.getGenero(),
                        aluno.getSituacao(),
                        aluno.getUltimoNivel(),
                        aluno.getEndereco(),
                        aluno.getResponsavel(),
                        aluno.getGrauParentesco(),
                        aluno.isResponsavelFinanceiro(),
                        aluno.getTurmas() != null ? aluno.getTurmas().stream().map(Turma::getId).collect(Collectors.toList()) : null
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@Valid @RequestBody AlunoDTO novoAluno) {
        Aluno aluno = alunoService.criarAluno(
                novoAluno.nomeCompleto(),
                novoAluno.email(),
                novoAluno.cpf(),
                novoAluno.rg(),
                novoAluno.orgaoExpeditorRg(),
                novoAluno.nacionalidade(),
                novoAluno.uf(),
                novoAluno.telefone(),
                novoAluno.dataNascimento(),
                novoAluno.nomeSocial(),
                novoAluno.genero(),
                novoAluno.situacao(),
                novoAluno.ultimoNivel(),
                novoAluno.endereco(),
                novoAluno.responsavel(),
                novoAluno.grauParentesco(),
                novoAluno.responsavelFinanceiro(),
                novoAluno.turmaIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(new AlunoDTO(
                aluno.getId(),
                aluno.getNomeCompleto(),
                aluno.getEmail(),
                aluno.getCpf(),
                aluno.getRg(),
                aluno.getOrgaoExpeditorRg(),
                aluno.getNacionalidade(),
                aluno.getUf(),
                aluno.getTelefone(),
                aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                aluno.getNomeSocial(),
                aluno.getGenero(),
                aluno.getSituacao(),
                aluno.getUltimoNivel(),
                aluno.getEndereco(),
                aluno.getResponsavel(),
                aluno.getGrauParentesco(),
                aluno.isResponsavelFinanceiro(),
                aluno.getTurmas() != null ? aluno.getTurmas().stream().map(Turma::getId).collect(Collectors.toList()) : null
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable Long id, @Valid @RequestBody AlunoDTO alunoAtualizado) {
        Aluno aluno = alunoService.atualizarAluno(
                id,
                alunoAtualizado.nomeCompleto(),
                alunoAtualizado.email(),
                alunoAtualizado.cpf(),
                alunoAtualizado.rg(),
                alunoAtualizado.orgaoExpeditorRg(),
                alunoAtualizado.nacionalidade(),
                alunoAtualizado.uf(),
                alunoAtualizado.telefone(),
                alunoAtualizado.dataNascimento(),
                alunoAtualizado.nomeSocial(),
                alunoAtualizado.genero(),
                alunoAtualizado.situacao(),
                alunoAtualizado.ultimoNivel(),
                alunoAtualizado.endereco(),
                alunoAtualizado.responsavel(),
                alunoAtualizado.grauParentesco(),
                alunoAtualizado.responsavelFinanceiro(),
                alunoAtualizado.turmaIds()
        );
        return ResponseEntity.ok(new AlunoDTO(
                aluno.getId(),
                aluno.getNomeCompleto(),
                aluno.getEmail(),
                aluno.getCpf(),
                aluno.getRg(),
                aluno.getOrgaoExpeditorRg(),
                aluno.getNacionalidade(),
                aluno.getUf(),
                aluno.getTelefone(),
                aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                aluno.getNomeSocial(),
                aluno.getGenero(),
                aluno.getSituacao(),
                aluno.getUltimoNivel(),
                aluno.getEndereco(),
                aluno.getResponsavel(),
                aluno.getGrauParentesco(),
                aluno.isResponsavelFinanceiro(),
                aluno.getTurmas() != null ? aluno.getTurmas().stream().map(Turma::getId).collect(Collectors.toList()) : null
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }
}