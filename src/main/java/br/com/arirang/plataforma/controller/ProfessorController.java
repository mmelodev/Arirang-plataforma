package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.ProfessorDTO;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.service.ProfessorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    private static final Logger logger = LoggerFactory.getLogger(ProfessorController.class);

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/novo")
    public String novoProfessorForm(Model model) {
        try {
            model.addAttribute("professor", new ProfessorDTO(null, "", null, "", "", "", "", ""));
            return "professor-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de novo professor: ", e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> criarProfessor(@Valid @RequestBody ProfessorDTO novoProfessor) {
        try {
            Professor professor = professorService.criarProfessor(
                    novoProfessor.nomeCompleto(),
                    novoProfessor.dataNascimento(),
                    novoProfessor.rg(),
                    novoProfessor.cpf(),
                    novoProfessor.telefone(),
                    novoProfessor.cargo(),
                    novoProfessor.formacao()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProfessorDTO(
                    professor.getId(),
                    professor.getNomeCompleto(),
                    professor.getDataNascimento(),
                    professor.getRg(),
                    professor.getCpf(),
                    professor.getTelefone(),
                    professor.getCargo(),
                    professor.getFormacao()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar professor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public String listarProfessores(Model model) {
        try {
            List<ProfessorDTO> professores = professorService.listarTodosProfessores().stream()
                    .map(professor -> new ProfessorDTO(
                            professor.getId(),
                            professor.getNomeCompleto(),
                            professor.getDataNascimento(),
                            professor.getRg(),
                            professor.getCpf(),
                            professor.getTelefone(),
                            professor.getCargo(),
                            professor.getFormacao()
                    )).collect(Collectors.toList());
            model.addAttribute("professores", professores);
            return "professores";
        } catch (Exception e) {
            logger.error("Erro ao listar professores: ", e);
            model.addAttribute("error", "Erro ao carregar a lista: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> buscarProfessorPorId(@PathVariable Long id) {
        return professorService.buscarProfessorPorId(id)
                .map(professor -> new ProfessorDTO(
                        professor.getId(),
                        professor.getNomeCompleto(),
                        professor.getDataNascimento(),
                        professor.getRg(),
                        professor.getCpf(),
                        professor.getTelefone(),
                        professor.getCargo(),
                        professor.getFormacao()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/editar/{id}")
    public String editarProfessorForm(@PathVariable Long id, Model model) {
        try {
            ProfessorDTO professor = professorService.buscarProfessorPorId(id)
                    .map(p -> new ProfessorDTO(
                            p.getId(),
                            p.getNomeCompleto(),
                            p.getDataNascimento(),
                            p.getRg(),
                            p.getCpf(),
                            p.getTelefone(),
                            p.getCargo(),
                            p.getFormacao()
                    ))
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            model.addAttribute("professor", professor);
            return "professor-form";
        } catch (Exception e) {
            logger.error("Erro ao carregar formulário de edição para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar o formulário: " + e.getMessage());
            return "error";
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizarProfessor(@PathVariable Long id, @Valid @RequestBody ProfessorDTO professorAtualizado) {
        try {
            Professor professor = professorService.atualizarProfessor(
                    id,
                    professorAtualizado.nomeCompleto(),
                    professorAtualizado.dataNascimento(),
                    professorAtualizado.rg(),
                    professorAtualizado.cpf(),
                    professorAtualizado.telefone(),
                    professorAtualizado.cargo(),
                    professorAtualizado.formacao()
            );
            return ResponseEntity.ok(new ProfessorDTO(
                    professor.getId(),
                    professor.getNomeCompleto(),
                    professor.getDataNascimento(),
                    professor.getRg(),
                    professor.getCpf(),
                    professor.getTelefone(),
                    professor.getCargo(),
                    professor.getFormacao()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar professor com ID {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/deletar/{id}")
    public String deletarProfessorConfirm(@PathVariable Long id, Model model) {
        try {
            ProfessorDTO professor = professorService.buscarProfessorPorId(id)
                    .map(p -> new ProfessorDTO(
                            p.getId(),
                            p.getNomeCompleto(),
                            p.getDataNascimento(),
                            p.getRg(),
                            p.getCpf(),
                            p.getTelefone(),
                            p.getCargo(),
                            p.getFormacao()
                    ))
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            model.addAttribute("professor", professor);
            return "professor-delete";
        } catch (Exception e) {
            logger.error("Erro ao carregar confirmação de deleção para ID {}: ", id, e);
            model.addAttribute("error", "Erro ao carregar a confirmação: " + e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        try {
            professorService.deletarProfessor(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar professor com ID {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}