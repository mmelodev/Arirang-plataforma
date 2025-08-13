package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.ProfessorDTO;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> criarProfessor(@Valid @RequestBody ProfessorDTO novoProfessor) {
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
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listarTodosProfessores() {
        return ResponseEntity.ok(professorService.listarTodosProfessores().stream().map(professor -> new ProfessorDTO(
                professor.getId(),
                professor.getNomeCompleto(),
                professor.getDataNascimento(),
                professor.getRg(),
                professor.getCpf(),
                professor.getTelefone(),
                professor.getCargo(),
                professor.getFormacao()
        )).collect(Collectors.toList()));
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

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizarProfessor(@PathVariable Long id, @Valid @RequestBody ProfessorDTO professorAtualizado) {
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
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }
}