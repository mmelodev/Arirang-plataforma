package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.repository.AlunoRepository;
import br.com.arirang.plataforma.repository.TurmaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional
    public Aluno criarAluno(String nomeCompleto, String email, String cpf, String rgRne, String nacionalidade,
                            String cep, String endereco, String dataNascimento, boolean responsavelFinanceiro, Long turmaId) {
        Aluno aluno = new Aluno();
        aluno.setNomeCompleto(nomeCompleto);
        aluno.setEmail(email);
        aluno.setCpf(cpf);
        aluno.setRgRne(rgRne);
        aluno.setNacionalidade(nacionalidade);
        aluno.setCep(cep);
        aluno.setEndereco(endereco);
        if (dataNascimento != null && !dataNascimento.isEmpty()) {
            aluno.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        aluno.setResponsavelFinanceiro(responsavelFinanceiro);
        if (turmaId != null) {
            Turma turma = turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            aluno.setTurma(turma);
        }
        return alunoRepository.save(aluno);
    }

    @Transactional(readOnly = true)
    public List<Aluno> listarTodosAlunos() {
        return alunoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Aluno> buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id);
    }

    @Transactional
    public Aluno atualizarAluno(Long id, String nomeCompleto, String email, String cpf, String rgRne,
                                String nacionalidade, String cep, String endereco, String dataNascimento,
                                boolean responsavelFinanceiro, Long turmaId) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        alunoExistente.setNomeCompleto(nomeCompleto);
        alunoExistente.setEmail(email);
        alunoExistente.setCpf(cpf);
        alunoExistente.setRgRne(rgRne);
        alunoExistente.setNacionalidade(nacionalidade);
        alunoExistente.setCep(cep);
        alunoExistente.setEndereco(endereco);
        if (dataNascimento != null && !dataNascimento.isEmpty()) {
            alunoExistente.setDataNascimento(LocalDateTime.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        alunoExistente.setResponsavelFinanceiro(responsavelFinanceiro);
        if (turmaId != null) {
            Turma turma = turmaRepository.findById(turmaId)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
            alunoExistente.setTurma(turma);
        }
        return alunoRepository.save(alunoExistente);
    }

    @Transactional
    public void deletarAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

    @PostConstruct
    public void init() {
        if (alunoRepository.count() == 0) {
            Aluno aluno = new Aluno();
            aluno.setNomeCompleto("João Silva");
            aluno.setEmail("joao@example.com");
            aluno.setCpf("12345678901");
            aluno.setRgRne("1234567");
            aluno.setNacionalidade("Brasileiro");
            aluno.setCep("12345678");
            aluno.setEndereco("Rua Teste, 123");
            aluno.setDataNascimento(LocalDateTime.now().minusYears(20));
            aluno.setResponsavelFinanceiro(true);
            alunoRepository.save(aluno);
        }
    }
}