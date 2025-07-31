package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @PostMapping
    public ResponseEntity<TurmaDTO> criarTurma(@Valid @RequestBody TurmaDTO novaTurma) {
        Turma turma = turmaService.criarTurma(novaTurma.nome(), novaTurma.professorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TurmaDTO(
                turma.getId(),
                turma.getNome(),
                turma.getProfessor() != null ? turma.getProfessor().getId() : null,
                null
        ));
    }

    @GetMapping
    public ResponseEntity<List<TurmaDTO>> listarTodasTurmas() {
        return ResponseEntity.ok(turmaService.listarTodasTurmas().stream().map(turma -> new TurmaDTO(
                turma.getId(),
                turma.getNome(),
                turma.getProfessor() != null ? turma.getProfessor().getId() : null,
                turma.getAlunos().stream().map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNomeCompleto(),
                        aluno.getEmail(),
                        aluno.getCpf(),
                        aluno.getRgRne(),
                        aluno.getNacionalidade(),
                        aluno.getCep(),
                        aluno.getEndereco(),
                        aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                        aluno.isResponsavelFinanceiro()
                )).collect(Collectors.toList())
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
                        aluno.getRgRne(),
                        aluno.getNacionalidade(),
                        aluno.getCep(),
                        aluno.getEndereco(),
                        aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                        aluno.isResponsavelFinanceiro()
                )).collect(Collectors.toList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> buscarTurmaPorId(@PathVariable Long id) {
        return turmaService.buscarTurmaPorId(id)
                .map(turma -> new TurmaDTO(
                        turma.getId(),
                        turma.getNome(),
                        turma.getProfessor() != null ? turma.getProfessor().getId() : null,
                        turma.getAlunos().stream().map(aluno -> new AlunoDTO(
                                aluno.getId(),
                                aluno.getNomeCompleto(),
                                aluno.getEmail(),
                                aluno.getCpf(),
                                aluno.getRgRne(),
                                aluno.getNacionalidade(),
                                aluno.getCep(),
                                aluno.getEndereco(),
                                aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                                aluno.isResponsavelFinanceiro()
                        )).collect(Collectors.toList())
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> atualizarTurma(@PathVariable Long id, @Valid @RequestBody TurmaDTO turmaAtualizada) {
        Turma turma = turmaService.atualizarTurma(id, turmaAtualizada.nome(), turmaAtualizada.professorId());
        return ResponseEntity.ok(new TurmaDTO(
                turma.getId(),
                turma.getNome(),
                turma.getProfessor() != null ? turma.getProfessor().getId() : null,
                turma.getAlunos().stream().map(aluno -> new AlunoDTO(
                        aluno.getId(),
                        aluno.getNomeCompleto(),
                        aluno.getEmail(),
                        aluno.getCpf(),
                        aluno.getRgRne(),
                        aluno.getNacionalidade(),
                        aluno.getCep(),
                        aluno.getEndereco(),
                        aluno.getDataNascimento() != null ? aluno.getDataNascimento().format(DATE_TIME_FORMATTER) : null,
                        aluno.isResponsavelFinanceiro()
                )).collect(Collectors.toList())
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        turmaService.deletarTurma(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{turmaId}/matricular")
    public ResponseEntity<String> matricularAluno(@PathVariable Long turmaId, @RequestBody MatriculaRequest matriculaRequest) {
        turmaService.matricularAluno(turmaId, matriculaRequest.alunoId());
        return ResponseEntity.status(HttpStatus.OK)
                .body("Aluno matriculado com sucesso na turma " + turmaId);
    }
}