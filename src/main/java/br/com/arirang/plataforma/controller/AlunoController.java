package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
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

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private AlunoDTO convertToDTO(Aluno aluno) {
        return new AlunoDTO(
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
        );
    }

    @GetMapping("/lista")
    public String listarAlunos(Model model) {
        try {
            List<AlunoDTO> alunos = alunoService.listarTodosAlunos().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            model.addAttribute("alunos", alunos);
            return "alunos"; // Retorna o template alunos.html
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar os alunos: " + e.getMessage());
            return "error"; // Retorna uma página de erro personalizada (a criar, se necessário)
        }
    }

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarTodosAlunos() {
        List<AlunoDTO> alunos = alunoService.listarTodosAlunos().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoDTO> buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunoPorId(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/novo")
    public String novoAlunoForm(Model model) {
        model.addAttribute("aluno", new AlunoDTO(null, "", "", "", "", "", "", "", "", "", "", "", "", null, null, null, "", false, null));
        return "aluno-form"; // Template para formulário de cadastro (a criar)
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@Valid @RequestBody AlunoDTO novoAluno) {
        Aluno aluno = alunoService.criarAluno(novoAluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(aluno));
    }

    @GetMapping("/editar/{id}")
    public String editarAlunoForm(@PathVariable Long id, Model model) {
        AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        model.addAttribute("aluno", aluno);
        return "aluno-form"; // Template para formulário de edição
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable Long id, @Valid @RequestBody AlunoDTO alunoAtualizado) {
        Aluno aluno = alunoService.atualizarAluno(id, alunoAtualizado);
        return ResponseEntity.ok(convertToDTO(aluno));
    }

    @GetMapping("/deletar/{id}")
    public String deletarAlunoConfirm(@PathVariable Long id, Model model) {
        AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        model.addAttribute("aluno", aluno);
        return "aluno-delete"; // Template para confirmação de exclusão
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/turma/{id}")
    public String associarTurma(@PathVariable Long id, Model model) {
        AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        model.addAttribute("aluno", aluno);
        return "aluno-turma"; // Template para associação de turma
    }
}