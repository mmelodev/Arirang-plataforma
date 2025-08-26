package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.service.AlunoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.arirang.plataforma.service.TurmaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

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
            model.addAttribute("aluno", new AlunoDTO(
                    null, "", "", "", "", "", "", "", "", "", "", "", "", "",
                    new Endereco(),
                    null, "", false, null
            ));
            return "aluno-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de novo aluno: ", e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    // Processa o submit do formulário MVC (x-www-form-urlencoded)
    @PostMapping
    public String criarAlunoMVC(@Valid @ModelAttribute("aluno") AlunoDTO novoAluno, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "aluno-form";
            }
            alunoService.criarAluno(novoAluno);
            return "redirect:/alunos/lista";
        } catch (Exception e) {
            logger.error("Erro ao criar aluno: ", e);
            model.addAttribute("error", "Erro ao criar aluno: " + e.getMessage());
            return "error";
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

    // Atualização via formulário MVC
    @PostMapping("/atualizar/{id}")
    public String atualizarAlunoMVC(@PathVariable Long id, @Valid @ModelAttribute("aluno") AlunoDTO alunoAtualizado, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "aluno-form";
            }
            alunoService.atualizarAluno(id, alunoAtualizado);
            return "redirect:/alunos/lista";
        } catch (Exception e) {
            logger.error("Erro ao atualizar aluno com ID {}: ", id, e);
            model.addAttribute("error", "Erro ao atualizar aluno: " + e.getMessage());
            return "error";
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

    // Exclusão via formulário (method override no template)
    @PostMapping(value = "/{id}")
    public String deletarAlunoMVC(@PathVariable Long id, @RequestParam(value = "_method", required = false) String method, Model model) {
        if ("delete".equalsIgnoreCase(method)) {
            try {
                alunoService.deletarAluno(id);
                return "redirect:/alunos/lista";
            } catch (Exception e) {
                logger.error("Erro ao deletar aluno com ID {}: ", id, e);
                model.addAttribute("error", "Erro ao deletar aluno: " + e.getMessage());
                return "error";
            }
        }
        return "redirect:/alunos/lista";
    }

    @GetMapping("/turma/{id}")
    public String associarTurma(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
            model.addAttribute("aluno", aluno);
            model.addAttribute("turmas", turmaService.listarTodasTurmas());
            return "aluno-turma";
        } catch (Exception e) {
            logger.error("Erro ao carregar associação de turma para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a associação: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/turmas")
    public String salvarAssociacaoTurma(@PathVariable Long id, @RequestParam("turmaId") Long turmaId, Model model) {
        try {
            alunoService.associarTurma(id, turmaId);
            return "redirect:/alunos/lista";
        } catch (Exception e) {
            logger.error("Erro ao associar turma para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao associar turma: " + e.getMessage());
            return "error";
        }
    }
}