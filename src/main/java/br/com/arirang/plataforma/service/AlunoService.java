package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.dto.AlunoDTO;
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
import java.util.stream.Collectors;

import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Responsavel;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional
    public Aluno criarAluno(AlunoDTO alunoDTO) {
        Aluno aluno = new Aluno();
        mapDtoToEntity(alunoDTO, aluno);
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
    public Aluno atualizarAluno(Long id, AlunoDTO alunoDTO) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
        mapDtoToEntity(alunoDTO, alunoExistente);
        return alunoRepository.save(alunoExistente);
    }

    private void mapDtoToEntity(AlunoDTO dto, Aluno aluno) {
        aluno.setNomeCompleto(dto.nomeCompleto());
        aluno.setEmail(dto.email());
        aluno.setCpf(dto.cpf());
        aluno.setRg(dto.rg());
        aluno.setOrgaoExpeditorRg(dto.orgaoExpeditorRg());
        aluno.setNacionalidade(dto.nacionalidade());
        aluno.setUf(dto.uf());
        aluno.setTelefone(dto.telefone());
        if (dto.dataNascimento() != null) {
            aluno.setDataNascimento(LocalDateTime.parse(dto.dataNascimento(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        aluno.setNomeSocial(dto.nomeSocial());
        aluno.setGenero(dto.genero());
        aluno.setSituacao(dto.situacao());
        aluno.setUltimoNivel(dto.ultimoNivel());
        aluno.setEndereco(dto.endereco());
        aluno.setResponsavel(dto.responsavel());
        aluno.setGrauParentesco(dto.grauParentesco());
        aluno.setResponsavelFinanceiro(dto.responsavelFinanceiro());

        if (dto.turmaIds() != null && !dto.turmaIds().isEmpty()) {
            List<Turma> turmas = turmaRepository.findAllById(dto.turmaIds())
                    .stream()
                    .filter(t -> t != null)
                    .collect(Collectors.toList());
            if (turmas.size() != dto.turmaIds().size()) {
                throw new RuntimeException("Uma ou mais turmas não foram encontradas.");
            }
            aluno.setTurmas(turmas);
        }
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