package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Responsavel;
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

import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Responsavel;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional
    public Aluno criarAluno(String nomeCompleto, String email, String cpf, String rg, String orgaoExpeditorRg,
                            String nacionalidade, String uf, String telefone, String dataNascimentoStr,
                            String nomeSocial, String genero, String situacao, String ultimoNivel,
                            Endereco endereco, Responsavel responsavel, String grauParentesco,
                            boolean responsavelFinanceiro, List<Long> turmaIds) {
        Aluno aluno = new Aluno();
        aluno.setNomeCompleto(nomeCompleto);
        aluno.setEmail(email);
        aluno.setCpf(cpf);
        aluno.setRg(rg);
        aluno.setOrgaoExpeditorRg(orgaoExpeditorRg);
        aluno.setNacionalidade(nacionalidade);
        aluno.setUf(uf);
        aluno.setTelefone(telefone);
        aluno.setNomeSocial(nomeSocial);
        aluno.setGenero(genero);
        aluno.setSituacao(situacao);
        aluno.setUltimoNivel(ultimoNivel);
        aluno.setEndereco(endereco);
        aluno.setResponsavelFinanceiro(responsavelFinanceiro);

        // Parse da data de nascimento
        if (dataNascimentoStr != null && !dataNascimentoStr.isEmpty()) {
            try {
                LocalDateTime dataNascimento = LocalDateTime.parse(dataNascimentoStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                aluno.setDataNascimento(dataNascimento);

                // Lógica para menores de 18 anos
                int idade = LocalDateTime.now().getYear() - dataNascimento.getYear();
                if (idade < 18 && (responsavel == null || grauParentesco == null)) {
                    throw new IllegalArgumentException("Responsável e grau de parentesco são obrigatórios para menores de 18 anos.");
                }
                if (idade < 18) {
                    aluno.setResponsavel(responsavel);
                    aluno.setGrauParentesco(grauParentesco);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de data de nascimento inválido. Use ISO_LOCAL_DATE_TIME (ex.: 2025-08-13T11:13:00).");
            }
        }

        // Vinculação com turmas
        if (turmaIds != null && !turmaIds.isEmpty()) {
            List<Turma> turmas = turmaRepository.findAllById(turmaIds)
                    .stream()
                    .filter(t -> t != null)
                    .toList();
            if (turmas.size() != turmaIds.size()) {
                throw new RuntimeException("Uma ou mais turmas não foram encontradas.");
            }
            aluno.setTurmas(turmas);
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
    public Aluno atualizarAluno(Long id, String nomeCompleto, String email, String cpf, String rg, String orgaoExpeditorRg,
                                String nacionalidade, String uf, String telefone, String dataNascimentoStr,
                                String nomeSocial, String genero, String situacao, String ultimoNivel,
                                Endereco endereco, Responsavel responsavel, String grauParentesco,
                                boolean responsavelFinanceiro, List<Long> turmaIds) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        alunoExistente.setNomeCompleto(nomeCompleto);
        alunoExistente.setEmail(email);
        alunoExistente.setCpf(cpf);
        alunoExistente.setRg(rg);
        alunoExistente.setOrgaoExpeditorRg(orgaoExpeditorRg);
        alunoExistente.setNacionalidade(nacionalidade);
        alunoExistente.setUf(uf);
        alunoExistente.setTelefone(telefone);
        alunoExistente.setNomeSocial(nomeSocial);
        alunoExistente.setGenero(genero);
        alunoExistente.setSituacao(situacao);
        alunoExistente.setUltimoNivel(ultimoNivel);
        alunoExistente.setEndereco(endereco);
        alunoExistente.setResponsavelFinanceiro(responsavelFinanceiro);

        // Parse da data de nascimento e lógica de responsável
        if (dataNascimentoStr != null && !dataNascimentoStr.isEmpty()) {
            try {
                LocalDateTime dataNascimento = LocalDateTime.parse(dataNascimentoStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                alunoExistente.setDataNascimento(dataNascimento);

                int idade = LocalDateTime.now().getYear() - dataNascimento.getYear();
                if (idade < 18 && (responsavel == null || grauParentesco == null)) {
                    throw new IllegalArgumentException("Responsável e grau de parentesco são obrigatórios para menores de 18 anos.");
                }
                if (idade < 18) {
                    alunoExistente.setResponsavel(responsavel);
                    alunoExistente.setGrauParentesco(grauParentesco);
                } else {
                    alunoExistente.setResponsavel(null); // Limpar responsável se maior de 18
                    alunoExistente.setGrauParentesco(null);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Formato de data de nascimento inválido. Use ISO_LOCAL_DATE_TIME (ex.: 2025-08-13T11:13:00).");
            }
        }

        // Vinculação com turmas
        if (turmaIds != null && !turmaIds.isEmpty()) {
            List<Turma> turmas = turmaRepository.findAllById(turmaIds)
                    .stream()
                    .filter(t -> t != null)
                    .toList();
            if (turmas.size() != turmaIds.size()) {
                throw new RuntimeException("Uma ou mais turmas não foram encontradas.");
            }
            alunoExistente.setTurmas(turmas);
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
            aluno.setRg("1234567");
            aluno.setOrgaoExpeditorRg("SSP");
            aluno.setNacionalidade("Brasileiro");
            aluno.setUf("SP");
            aluno.setTelefone("11987654321");
            aluno.setNomeSocial(null);
            aluno.setGenero("Masculino");
            aluno.setSituacao("Ativo");
            aluno.setUltimoNivel("Básico 1");

            Endereco endereco = new Endereco();
            endereco.setRua("Rua Teste");
            endereco.setNumero("123");
            endereco.setBairro("Centro");
            endereco.setComplemento("Apto 10");
            endereco.setCep("12345678");
            endereco.setCidade("São Paulo");
            aluno.setEndereco(endereco);

            // Exemplo de responsável (assumindo menor de 18 anos)
            LocalDateTime dataNascimento = LocalDateTime.now().minusYears(16);
            aluno.setDataNascimento(dataNascimento);

            Responsavel responsavel = new Responsavel();
            responsavel.setNomeCompleto("Maria Silva");
            responsavel.setDataNascimento(LocalDateTime.now().minusYears(40));
            responsavel.setRg("7654321");
            responsavel.setCpf("98765432100");
            responsavel.setTelefone("11912345678");
            aluno.setResponsavel(responsavel);
            aluno.setGrauParentesco("Mãe");
            aluno.setResponsavelFinanceiro(true);

            // Vinculação com uma turma (se existir)
            Optional<Turma> turmaOpt = turmaRepository.findById(1L); // Exemplo com ID 1
            if (turmaOpt.isPresent()) {
                aluno.setTurmas(List.of(turmaOpt.get()));
            }

            alunoRepository.save(aluno);
        }
    }
}