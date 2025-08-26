package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.service.TurmaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/turmas")
public class TurmaController {

    private static final Logger logger = LoggerFactory.getLogger(TurmaController.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Autowired
    private TurmaService turmaService;

    @GetMapping("/novo")
    public String novaTurmaForm(Model model) {
        try {
            // Ajustado para 17 argumentos, removendo o último null
            model.addAttribute("turma", new TurmaDTO(null, "", null, "", "", "", "", "", "", "", "", "", 0, null, null, "", null));
            return "turma-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de nova turma: ", e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping
    public String criarTurmaMVC(@Valid @ModelAttribute("turma") TurmaDTO novaTurma, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "turma-form";
            }
            Professor professorResponsavel = novaTurma.professorResponsavelId() != null ?
                    new Professor() {{ setId(novaTurma.professorResponsavelId()); }} : null;
            turmaService.criarTurma(
                    novaTurma.nomeTurma(),
                    professorResponsavel,
                    novaTurma.nivelProficiencia(),
                    novaTurma.diaTurma(),
                    novaTurma.turno(),
                    novaTurma.formato(),
                    novaTurma.modalidade(),
                    novaTurma.realizador(),
                    novaTurma.horaInicio(),
                    novaTurma.horaTermino(),
                    novaTurma.anoSemestre(),
                    novaTurma.cargaHorariaTotal(),
                    novaTurma.inicioTurma(),
                    novaTurma.terminoTurma(),
                    novaTurma.situacaoTurma(),
                    novaTurma.alunoIds()
            );
            return "redirect:/turmas";
        } catch (Exception e) {
            logger.error("Erro ao criar turma: ", e);
            model.addAttribute("error", "Erro ao criar turma: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping
    public String listarTodasTurmas(Model model) {
        try {
            List<TurmaDTO> turmas = turmaService.listarTodasTurmas().stream().map(turma -> new TurmaDTO(
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
                    turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
            )).collect(Collectors.toList());
            model.addAttribute("turmas", turmas);
            return "turmas"; // Espera turmas.html
        } catch (Exception e) {
            logger.error("Erro ao listar turmas: ", e);
            model.addAttribute("error", "Erro ao carregar a lista: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<AlunoDTO>> listarAlunosDaTurma(@PathVariable Long id) {
        return turmaService.buscarTurmaPorId(id)
                .map(turma -> ResponseEntity.ok(turma.getAlunos().stream().map(aluno -> new AlunoDTO(
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
                        aluno.getResponsavel().getId(),
                        aluno.getGrauParentesco(),
                        aluno.isResponsavelFinanceiro(),
                        null
                )).collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> buscarTurmaPorId(@PathVariable Long id) {
        return turmaService.buscarTurmaPorId(id)
                .map(turma -> new TurmaDTO(
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
                        turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/editar/{id}")
    public String editarTurmaForm(@PathVariable Long id, Model model) {
        try {
            TurmaDTO turma = turmaService.buscarTurmaPorId(id)
                    .map(t -> new TurmaDTO(
                            t.getId(),
                            t.getNomeTurma(),
                            t.getProfessorResponsavel() != null ? t.getProfessorResponsavel().getId() : null,
                            t.getNivelProficiencia(),
                            t.getDiaTurma(),
                            t.getTurno(),
                            t.getFormato(),
                            t.getModalidade(),
                            t.getRealizador(),
                            t.getHoraInicio(),
                            t.getHoraTermino(),
                            t.getAnoSemestre(),
                            t.getCargaHorariaTotal(),
                            t.getInicioTurma(),
                            t.getTerminoTurma(),
                            t.getSituacaoTurma(),
                            t.getAlunos() != null ? t.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
                    ))
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            model.addAttribute("turma", turma);
            return "turma-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de edição para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarTurmaMVC(@PathVariable Long id, @Valid @ModelAttribute("turma") TurmaDTO turmaAtualizada, BindingResult bindingResult, Model model) {
        try {
            if (bindingResult.hasErrors()) {
                return "turma-form";
            }
            Professor professorResponsavel = turmaAtualizada.professorResponsavelId() != null ?
                    new Professor() {{ setId(turmaAtualizada.professorResponsavelId()); }} : null;
            turmaService.atualizarTurma(
                    id,
                    turmaAtualizada.nomeTurma(),
                    professorResponsavel,
                    turmaAtualizada.nivelProficiencia(),
                    turmaAtualizada.diaTurma(),
                    turmaAtualizada.turno(),
                    turmaAtualizada.formato(),
                    turmaAtualizada.modalidade(),
                    turmaAtualizada.realizador(),
                    turmaAtualizada.horaInicio(),
                    turmaAtualizada.horaTermino(),
                    turmaAtualizada.anoSemestre(),
                    turmaAtualizada.cargaHorariaTotal(),
                    turmaAtualizada.inicioTurma(),
                    turmaAtualizada.terminoTurma(),
                    turmaAtualizada.situacaoTurma(),
                    turmaAtualizada.alunoIds()
            );
            return "redirect:/turmas";
        } catch (Exception e) {
            logger.error("Erro ao atualizar turma com ID {}: ", id, e);
            model.addAttribute("error", "Erro ao atualizar turma: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarTurmaConfirm(@PathVariable Long id, Model model) {
        try {
            TurmaDTO turma = turmaService.buscarTurmaPorId(id)
                    .map(t -> new TurmaDTO(
                            t.getId(),
                            t.getNomeTurma(),
                            t.getProfessorResponsavel() != null ? t.getProfessorResponsavel().getId() : null,
                            t.getNivelProficiencia(),
                            t.getDiaTurma(),
                            t.getTurno(),
                            t.getFormato(),
                            t.getModalidade(),
                            t.getRealizador(),
                            t.getHoraInicio(),
                            t.getHoraTermino(),
                            t.getAnoSemestre(),
                            t.getCargaHorariaTotal(),
                            t.getInicioTurma(),
                            t.getTerminoTurma(),
                            t.getSituacaoTurma(),
                            t.getAlunos() != null ? t.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
                    ))
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            model.addAttribute("turma", turma);
            return "turma-delete";
        } catch (Exception e) {
            logger.error("Erro ao carregar confirmação de deleção para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a confirmação: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping(value = "/{id}")
    public String deletarTurmaMVC(@PathVariable Long id, @RequestParam(value = "_method", required = false) String method, Model model) {
        if ("delete".equalsIgnoreCase(method)) {
            try {
                turmaService.deletarTurma(id);
                return "redirect:/turmas";
            } catch (Exception e) {
                logger.error("Erro ao deletar turma com ID {}: ", id, e);
                model.addAttribute("error", "Erro ao deletar turma: " + e.getMessage());
                return "error";
            }
        }
        return "redirect:/turmas";
    }
}