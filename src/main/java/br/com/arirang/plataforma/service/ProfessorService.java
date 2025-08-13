package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional
    public Professor criarProfessor(String nomeCompleto, LocalDateTime dataNascimento, String rg, String cpf,
                                    String telefone, String cargo, String formacao) {
        Professor professor = new Professor();
        professor.setNomeCompleto(nomeCompleto);
        professor.setDataNascimento(dataNascimento);
        professor.setRg(rg);
        professor.setCpf(cpf);
        professor.setTelefone(telefone);
        professor.setCargo(cargo);
        professor.setFormacao(formacao);
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
    public Professor atualizarProfessor(Long id, String nomeCompleto, LocalDateTime dataNascimento, String rg,
                                        String cpf, String telefone, String cargo, String formacao) {
        Professor professorExistente = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
        professorExistente.setNomeCompleto(nomeCompleto);
        professorExistente.setDataNascimento(dataNascimento);
        professorExistente.setRg(rg);
        professorExistente.setCpf(cpf);
        professorExistente.setTelefone(telefone);
        professorExistente.setCargo(cargo);
        professorExistente.setFormacao(formacao);
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