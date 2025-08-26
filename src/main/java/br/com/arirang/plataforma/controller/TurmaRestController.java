package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.service.TurmaService;
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
@RequestMapping("/api/turmas")
public class TurmaRestController {

    private static final Logger logger = LoggerFactory.getLogger(TurmaRestController.class);

    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listar() {
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
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> buscar(@PathVariable Long id) {
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

    @PostMapping
    public ResponseEntity<TurmaDTO> criar(@Valid @RequestBody TurmaDTO dto) {
        try {
            Professor professorResponsavel = dto.professorResponsavelId() != null ? new Professor() {{ setId(dto.professorResponsavelId()); }} : null;
            Turma turma = turmaService.criarTurma(
                    dto.nomeTurma(),
                    professorResponsavel,
                    dto.nivelProficiencia(),
                    dto.diaTurma(),
                    dto.turno(),
                    dto.formato(),
                    dto.modalidade(),
                    dto.realizador(),
                    dto.horaInicio(),
                    dto.horaTermino(),
                    dto.anoSemestre(),
                    dto.cargaHorariaTotal(),
                    dto.inicioTurma(),
                    dto.terminoTurma(),
                    dto.situacaoTurma(),
                    dto.alunoIds()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(new TurmaDTO(
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
            ));
        } catch (Exception e) {
            logger.error("Erro ao criar turma", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TurmaDTO dto) {
        try {
            Professor professorResponsavel = dto.professorResponsavelId() != null ? new Professor() {{ setId(dto.professorResponsavelId()); }} : null;
            Turma turma = turmaService.atualizarTurma(
                    id,
                    dto.nomeTurma(),
                    professorResponsavel,
                    dto.nivelProficiencia(),
                    dto.diaTurma(),
                    dto.turno(),
                    dto.formato(),
                    dto.modalidade(),
                    dto.realizador(),
                    dto.horaInicio(),
                    dto.horaTermino(),
                    dto.anoSemestre(),
                    dto.cargaHorariaTotal(),
                    dto.inicioTurma(),
                    dto.terminoTurma(),
                    dto.situacaoTurma(),
                    dto.alunoIds()
            );
            return ResponseEntity.ok(new TurmaDTO(
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
            ));
        } catch (Exception e) {
            logger.error("Erro ao atualizar turma {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            turmaService.deletarTurma(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao deletar turma {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


