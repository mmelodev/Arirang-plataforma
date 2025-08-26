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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/professores")
public class ProfessorRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProfessorRestController.class);

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listar() {
        List<ProfessorDTO> professores = professorService.listarTodosProfessores().stream()
                .map(p -> new ProfessorDTO(
                        p.getId(), p.getNomeCompleto(), p.getDataNascimento(), p.getRg(), p.getCpf(),
                        p.getTelefone(), p.getCargo(), p.getFormacao()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDTO> buscar(@PathVariable Long id) {
        return professorService.buscarProfessorPorId(id)
                .map(p -> new ProfessorDTO(
                        p.getId(), p.getNomeCompleto(), p.getDataNascimento(), p.getRg(), p.getCpf(),
                        p.getTelefone(), p.getCargo(), p.getFormacao()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> criar(@Valid @RequestBody ProfessorDTO novo) {
        try {
            Professor professor = professorService.criarProfessor(
                    novo.nomeCompleto(), novo.dataNascimento(), novo.rg(), novo.cpf(),
                    novo.telefone(), novo.cargo(), novo.formacao()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProfessorDTO(
                    professor.getId(), professor.getNomeCompleto(), professor.getDataNascimento(), professor.getRg(),
                    professor.getCpf(), professor.getTelefone(), professor.getCargo(), professor.getFormacao()
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar professor", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProfessorDTO dto) {
        try {
            Professor p = professorService.atualizarProfessor(
                    id, dto.nomeCompleto(), dto.dataNascimento(), dto.rg(), dto.cpf(),
                    dto.telefone(), dto.cargo(), dto.formacao()
            );
            return ResponseEntity.ok(new ProfessorDTO(
                    p.getId(), p.getNomeCompleto(), p.getDataNascimento(), p.getRg(), p.getCpf(),
                    p.getTelefone(), p.getCargo(), p.getFormacao()
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar professor {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            professorService.deletarProfessor(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar professor {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


