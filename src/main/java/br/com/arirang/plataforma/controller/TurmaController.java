package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Professor;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @PostMapping
    public ResponseEntity<TurmaDTO> criarTurma(@Valid @RequestBody TurmaDTO novaTurma) {
        Professor professorResponsavel = novaTurma.professorResponsavelId() != null ?
                new Professor() {{ setId(novaTurma.professorResponsavelId()); }} : null;
        Turma turma = turmaService.criarTurma(
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
                turma.getUltimaModificacao(),
                turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
        ));
    }

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listarTodasTurmas() {
        return ResponseEntity.ok(turmaService.listarTodasTurmas().stream().map(turma -> new TurmaDTO(
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
                turma.getUltimaModificacao(),
                turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
        )).collect(Collectors.toList()));
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
                        aluno.getResponsavel(),
                        aluno.getGrauParentesco(),
                        aluno.isResponsavelFinanceiro(),
                        null // turmaIds não é necessário aqui
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
                        turma.getUltimaModificacao(),
                        turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long id, @Valid @RequestBody TurmaDTO turmaAtualizada) {
        Professor professorResponsavel = turmaAtualizada.professorResponsavelId() != null ?
                new Professor() {{ setId(turmaAtualizada.professorResponsavelId()); }} : null;
        Turma turma = turmaService.atualizarTurma(
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
                turma.getUltimaModificacao(),
                turma.getAlunos() != null ? turma.getAlunos().stream().map(Aluno::getId).collect(Collectors.toList()) : null
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }
}