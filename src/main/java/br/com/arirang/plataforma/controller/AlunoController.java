package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.service.AlunoService;
import br.com.arirang.plataforma.service.TurmaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private TurmaService turmaService;

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
                aluno.getDataNascimento(), // Direct LocalDate
                aluno.getNomeSocial(),
                aluno.getGenero(),
                aluno.getSituacao(),
                aluno.getUltimoNivel(),
                aluno.getEndereco(),
                aluno.getResponsavel() != null ? aluno.getResponsavel().getId() : null,
                aluno.getGrauParentesco(),
                aluno.isResponsavelFinanceiro(),
                aluno.getTurmas() != null ? aluno.getTurmas().stream().map(Turma::getId).collect(Collectors.toList()) : Collections.emptyList()
        );
    }

    @GetMapping("/lista")
    public String listarAlunos(Model model) {
        try {
            List<AlunoDTO> alunos = alunoService.listarTodosAlunos().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            model.addAttribute("alunos", alunos);
        } catch (Exception e) {
            logger.error("Erro ao carregar os alunos: ", e);
            model.addAttribute("error", "Erro ao carregar os alunos: " + e.getMessage());
        }
        return "alunos";
    }

    @GetMapping("/novo")
    public String novoAlunoForm(Model model) {
        model.addAttribute("aluno", new AlunoDTO(
                null, "", "", "", "", "", "", "", "",
                LocalDate.now(),
                "", "", "", "",
                new Endereco(),
                null, "", false, Collections.emptyList()
        ));
        model.addAttribute("isNew", true);
        model.addAttribute("turmas", turmaService.listarTodasTurmas()); // Adicionado
        return "aluno-form";
    }

    @PostMapping
    public String criarAlunoMVC(@Valid @ModelAttribute("aluno") AlunoDTO novoAluno, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "aluno-form";
        }
        try {
            alunoService.criarAluno(novoAluno);
            return "redirect:/alunos/lista";
        } catch (Exception e) {
            logger.error("Erro ao criar aluno: ", e);
            model.addAttribute("error", "Erro ao criar aluno: " + e.getMessage());
            model.addAttribute("isNew", true);
            return "aluno-form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarAlunoForm(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));
            model.addAttribute("aluno", aluno);
            model.addAttribute("isNew", false);
            model.addAttribute("turmas", turmaService.listarTodasTurmas()); // Adicionado
            return "aluno-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de edição para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarAlunoMVC(@PathVariable Long id, @Valid @ModelAttribute("aluno") AlunoDTO alunoAtualizado, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", false);
            return "aluno-form";
        }
        try {
            alunoService.atualizarAluno(id, alunoAtualizado);
            return "redirect:/alunos/lista";
        } catch (Exception e) {
            logger.error("Erro ao atualizar aluno com ID {}: ", id, e);
            model.addAttribute("error", "Erro ao atualizar aluno: " + e.getMessage());
            model.addAttribute("isNew", false);
            return "aluno-form";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarAlunoConfirm(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));
            model.addAttribute("aluno", aluno);
            return "aluno-delete";
        } catch (Exception e) {
            logger.error("Erro ao carregar confirmação de deleção para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a confirmação: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}")
    public String deletarAlunoMVC(@PathVariable Long id) {
        try {
            alunoService.deletarAluno(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar aluno com ID {}: ", id, e);
            // Optionally add error to flash attributes for redirect
        }
        return "redirect:/alunos/lista";
    }

    @GetMapping("/turma/{id}")
    public String associarTurmaForm(@PathVariable Long id, Model model) {
        try {
            AlunoDTO aluno = alunoService.buscarAlunoPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));
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
    public String salvarAssociacaoTurma(@PathVariable Long id, @RequestParam("turmaId") Long turmaId) {
        try {
            alunoService.associarTurma(id, turmaId);
        } catch (Exception e) {
            logger.error("Erro ao associar turma para ID {}: ", id, e);
            // Optionally add error to flash attributes for redirect
        }
        return "redirect:/alunos/lista";
    }
}
