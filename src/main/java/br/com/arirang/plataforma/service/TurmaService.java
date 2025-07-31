package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.repository.TurmaRepository;
import br.com.arirang.plataforma.repository.AlunoRepository;
import br.com.arirang.plataforma.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public Turma criarTurma(String nome, Long professorId) {
        Turma turma = new Turma();
        turma.setNome(nome);
        if (professorId != null) {
            Professor professor = professorRepository.findById(professorId)
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            turma.setProfessor(professor);
        }
        return turmaRepository.save(turma);
    }

    @Transactional(readOnly = true)
    public List<Turma> listarTodasTurmas() {
        return turmaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Turma> buscarTurmaPorId(Long id) {
        return turmaRepository.findById(id);
    }

    @Transactional
    public Turma atualizarTurma(Long id, String nome, Long professorId) {
        Turma turmaExistente = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        turmaExistente.setNome(nome);
        if (professorId != null) {
            Professor professor = professorRepository.findById(professorId)
                    .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
            turmaExistente.setProfessor(professor);
        }
        return turmaRepository.save(turmaExistente);
    }

    @Transactional
    public void deletarTurma(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada");
        }
        turmaRepository.deleteById(id);
    }

    @Transactional
    public void matricularAluno(Long turmaId, Long alunoId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        if (aluno.getTurma() != null) {
            throw new IllegalArgumentException("Aluno já está matriculado em outra turma.");
        }

        aluno.setTurma(turma);
        alunoRepository.save(aluno);
    }
}