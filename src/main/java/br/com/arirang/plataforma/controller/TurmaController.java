package br.com.arirang.plataforma.controller;

import java.util.List;

import br.com.arirang.plataforma.entity.Aluno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.repository.TurmaRepository;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
    @Autowired
    private TurmaRepository turmaRepository;

    @PostMapping
    public ResponseEntity<Turma> criarTurma(@RequestBody Turma novaTurma) {
        Turma salvo = turmaRepository.save(novaTurma);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Turma>> listarTodasTurmas() {
        List<Turma> turmas = turmaRepository.findAll();
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}/alunos")
    public ResponseEntity<List<Aluno>> listarAlunosDaTurma(@PathVariable Long id) {
        return turmaRepository.findById(id)
                .map(turma -> ResponseEntity.ok(turma.getAlunos()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscarTurmaPorId(@PathVariable Long id) {
        return turmaRepository.findById(id)
                .map(turma -> ResponseEntity.ok(turma))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizarTurma(@PathVariable Long id, @RequestBody Turma turmaAtualizada) {
        return turmaRepository.findById(id)
                .map(turmaExistente -> {
                    turmaExistente.setNome(turmaAtualizada.getNome());
                    turmaExistente.setProfessor(turmaAtualizada.getProfessor());
                    Turma salvo = turmaRepository.save(turmaExistente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTurma(@PathVariable Long id) {
        if (turmaRepository.existsById(id)) {
            turmaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}