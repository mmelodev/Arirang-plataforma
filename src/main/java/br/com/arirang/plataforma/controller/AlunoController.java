package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.service.AlunoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);

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
                aluno.getResponsavel() != null ? aluno.getResponsavel().getId() : null, // Ajuste para Long
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
            if (alunos.isEmpty()) {
                logger.warn("Nenhum aluno encontrado no banco de dados.");
                model.addAttribute("error", "Nenhum aluno cadastrado.");
            } else {
                model.addAttribute("alunos", alunos);
            }
            return "alunos";
        } catch (Exception e) {
            logger.error("Erro ao carregar os alunos: ", e);
            model.addAttribute("error", "Erro ao carregar os alunos: " + e.getMessage());
            return "error";
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
        try {
            model.addAttribute("aluno", new AlunoDTO(null, "", "", "", "", "", "", "", "", "", "", "", "", null, null, null, "", false, null));
            return "aluno-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de novo aluno: ", e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criarAluno(@Valid @RequestBody AlunoDTO novoAluno) {
        try {
            Aluno aluno = alunoService.criarAluno(novoAluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(aluno));
        } catch (Exception e) {
            logger.error("Erro ao criar aluno: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/editar/{id}")
    public String editarAlunoForm(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            model.addAttribute("aluno", aluno);
            return "aluno-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de edição para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable Long id, @Valid @RequestBody AlunoDTO alunoAtualizado) {
        try {
            Aluno aluno = alunoService.atualizarAluno(id, alunoAtualizado);
            return ResponseEntity.ok(convertToDTO(aluno));
        } catch (Exception e) {
            logger.error("Erro ao atualizar aluno com ID {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarAlunoConfirm(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            model.addAttribute("aluno", aluno);
            return "aluno-delete";
        } catch (Exception e) {
            logger.error("Erro ao carregar confirmação de deleção para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a confirmação: " + e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        try {
            alunoService.deletarAluno(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar aluno com ID {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/turma/{id}")
    public String associarTurma(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            model.addAttribute("aluno", aluno);
            return "aluno-turma";
        } catch (Exception e) {
            logger.error("Erro ao carregar associação de turma para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a associação: " + e.getMessage());
            return "error";
        }
    }
}