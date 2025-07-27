package br.com.arirang.plataforma.controller;

import java.util.List;

import br.com.arirang.plataforma.entity.Turma;
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
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.repository.AlunoRepository;
import br.com.arirang.plataforma.repository.TurmaRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
    
    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;
    
    @PostMapping
    public ResponseEntity<Aluno> criarAluno(@RequestBody Aluno novoAluno) {
        Aluno salvo = alunoRepository.save(novoAluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
    
    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodosAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        return alunoRepository.findById(id)
                .map(aluno -> ResponseEntity.ok(aluno))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable Long id, @RequestBody Aluno alunoAtualizado) {
        return alunoRepository.findById(id)
                .map(alunoExistente -> {
                    alunoExistente.setNomeCompleto(alunoAtualizado.getNomeCompleto());
                    alunoExistente.setEmail(alunoAtualizado.getEmail());
                    alunoExistente.setCpf(alunoAtualizado.getCpf());
                    alunoExistente.setRgRne(alunoAtualizado.getRgRne());
                    alunoExistente.setNacionalidade(alunoAtualizado.getNacionalidade());
                    alunoExistente.setCep(alunoAtualizado.getCep());
                    alunoExistente.setEndereco(alunoAtualizado.getEndereco());
                    alunoExistente.setDataNascimento(alunoAtualizado.getDataNascimento());
                    alunoExistente.setResponsavelFinanceiro(alunoAtualizado.isResponsavelFinanceiro());
                    if (alunoAtualizado.getTurma() != null && alunoAtualizado.getTurma().getId() != null) {
                        Turma turma = turmaRepository.findById(alunoAtualizado.getTurma().getId())
                                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
                        alunoExistente.setTurma(turma);
                    }
                    Aluno salvo = alunoRepository.save(alunoExistente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        if (alunoRepository.existsById(id)) {
            alunoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}