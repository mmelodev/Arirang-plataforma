package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.service.TurmaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    private static final Logger logger = LoggerFactory.getLogger(TurmaController.class);

    @Autowired
    private TurmaService turmaService;

    // Helper method to convert Turma entity to TurmaDTO
    private TurmaDTO convertToDTO(Turma turma) {
        return new TurmaDTO(
                turma.getId(),
                turma.getNomeTurma(),
                turma.getProfessorResponsavel() != null ? turma.getProfessorResponsavel().getId() : null,
                turma.getNivelProficiencia(),
                turma.getDiaTurma(),
                turma.getTurno(),
                turma.getFormato(),
                turma.getModalidade(),
                turma.getRealizador(),
                turma.getHoraInicio(),
                turma.getHoraTermino(),
                turma.getAnoSemestre(),
                turma.getCargaHorariaTotal(),
                turma.getInicioTurma(),
                turma.getTerminoTurma(),
                turma.getSituacaoTurma(),
                turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : Collections.emptyList()
        );
    }
    
    // Helper method to convert Aluno entity to AlunoDTO
    private AlunoDTO convertAlunoToDTO(Aluno aluno) {
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
            aluno.getDataNascimento(),
            aluno.getNomeSocial(),
            aluno.getGenero(),
            aluno.getSituacao(),
            aluno.getUltimoNivel(),
            aluno.getEndereco(),
            aluno.getResponsavel() != null ? aluno.getResponsavel().getId() : null,
            aluno.getGrauParentesco(),
            aluno.isResponsavelFinanceiro(),
            Collections.emptyList() // Avoid infinite loop
        );
    }

    @GetMapping("/novo")
    public String novaTurmaForm(Model model) {
        model.addAttribute("turma", new TurmaDTO(
                null,                      // id
                "",                        // nomeTurma
                null,                      // professorResponsavelId
                "",                        // nivelProficiencia
                "",                        // diaTurma
                "",                        // turno
                "",                        // formato
                "",                        // modalidade
                "",                        // realizador
                "",                        // horaInicio
                "",                        // horaTermino
                "",                        // anoSemestre
                null,                      // cargaHorariaTotal (Integer)
                LocalDateTime.now(),       // inicioTurma
                LocalDateTime.now().plusHours(2), // terminoTurma
                "Prevista",                // situacaoTurma
                Collections.emptyList()    // alunoIds
        ));
        model.addAttribute("isNew", true);
        return "turma-form";
    }

    @PostMapping
    public String criarTurmaMVC(@Valid @ModelAttribute("turma") TurmaDTO novaTurma, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", true);
            return "turma-form";
        }
        try {
            turmaService.criarTurma(novaTurma);
            return "redirect:/turmas";
        } catch (Exception e) {
            logger.error("Erro ao criar turma: ", e);
            model.addAttribute("error", "Erro ao criar turma: " + e.getMessage());
            model.addAttribute("isNew", true);
            model.addAttribute("turma", novaTurma);
            return "turma-form";
        }
    }

    @GetMapping
    public String listarTodasTurmas(Model model) {
        try {
            List<TurmaDTO> turmas = turmaService.listarTodasTurmas().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            model.addAttribute("turmas", turmas);
            return "turmas";
        } catch (Exception e) {
            logger.error("Erro ao listar turmas: ", e);
            model.addAttribute("error", "Erro ao carregar a lista: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<AlunoDTO>> listarAlunosDaTurma(@PathVariable Long id) {
        return turmaService.buscarTurmaPorId(id)
                .map(turma -> ResponseEntity.ok(turma.getAlunos().stream()
                        .map(this::convertAlunoToDTO)
                        .collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/editar/{id}")
    public String editarTurmaForm(@PathVariable Long id, Model model) {
        try {
            TurmaDTO turma = turmaService.buscarTurmaPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + id));
            model.addAttribute("turma", turma);
            model.addAttribute("isNew", false);
            return "turma-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de edição para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarTurmaMVC(@PathVariable Long id, @Valid @ModelAttribute("turma") TurmaDTO turmaAtualizada, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isNew", false);
            return "turma-form";
        }
        try {
            turmaService.atualizarTurma(id, turmaAtualizada);
            return "redirect:/turmas";
        } catch (Exception e) {
            logger.error("Erro ao atualizar turma com ID {}: ", id, e);
            model.addAttribute("error", "Erro ao atualizar turma: " + e.getMessage());
            model.addAttribute("isNew", false);
            model.addAttribute("turma", turmaAtualizada);
            return "turma-form";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarTurmaConfirm(@PathVariable Long id, Model model) {
        try {
            TurmaDTO turma = turmaService.buscarTurmaPorId(id)
                    .map(this::convertToDTO)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + id));
            model.addAttribute("turma", turma);
            return "turma-delete";
        } catch (Exception e) {
            logger.error("Erro ao carregar confirmação de deleção para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a confirmação: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}")
    public String deletarTurmaMVC(@PathVariable Long id) {
        try {
            turmaService.deletarTurma(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar turma com ID {}: ", id, e);
        }
        return "redirect:/turmas";
    }
}
