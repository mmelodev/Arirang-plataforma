package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public Professor criarProfessor(String nome, String email) {
        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setEmail(email);
        return professorRepository.save(professor);
    }

    @Transactional(readOnly = true)
    public List<Professor> listarTodosProfessores() {
        return professorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Professor> buscarProfessorPorId(Long id) {
        return professorRepository.findById(id);
    }

    @Transactional
    public Professor atualizarProfessor(Long id, String nome, String email) {
        Professor professorExistente = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        professorExistente.setNome(nome);
        professorExistente.setEmail(email);
        return professorRepository.save(professorExistente);
    }

    @Transactional
    public void deletarProfessor(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new RuntimeException("Professor não encontrado");
        }
        professorRepository.deleteById(id);
    }
}