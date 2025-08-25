package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Responsavel;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.repository.AlunoRepository;
import br.com.arirang.plataforma.repository.ResponsavelRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private static final Logger logger = LoggerFactory.getLogger(AlunoService.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Aluno> listarTodosAlunos() {
        try {
            List<Aluno> alunos = entityManager.createQuery(
                            "SELECT a FROM Aluno a JOIN FETCH a.responsavel JOIN FETCH a.turmas", Aluno.class)
                    .getResultList();
            if (alunos.isEmpty()) {
                logger.warn("Nenhum aluno encontrado no banco de dados.");
            }
            return alunos;
        } catch (Exception e) {
            logger.error("Erro ao listar todos os alunos: ", e);
            throw new RuntimeException("Erro ao carregar os alunos: " + e.getMessage());
        }
    }

    public Optional<Aluno> buscarAlunoPorId(Long id) {
        try {
            return alunoRepository.findById(id);
        } catch (Exception e) {
            logger.error("Erro ao buscar aluno por ID {}: ", id, e);
            throw new RuntimeException("Erro ao buscar aluno: " + e.getMessage());
        }
    }

    @Transactional
    public Aluno criarAluno(AlunoDTO alunoDTO) {
        try {
            Aluno aluno = new Aluno();
            aluno.setId(alunoDTO.id());
            aluno.setNomeCompleto(alunoDTO.nomeCompleto());
            aluno.setEmail(alunoDTO.email());
            aluno.setCpf(alunoDTO.cpf());
            aluno.setRg(alunoDTO.rg());
            aluno.setOrgaoExpeditorRg(alunoDTO.orgaoExpeditorRg());
            aluno.setNacionalidade(alunoDTO.nacionalidade());
            aluno.setUf(alunoDTO.uf());
            aluno.setTelefone(alunoDTO.telefone());
            aluno.setDataNascimento(alunoDTO.dataNascimento() != null ? LocalDateTime.parse(alunoDTO.dataNascimento(), DATE_TIME_FORMATTER) : null);
            aluno.setNomeSocial(alunoDTO.nomeSocial());
            aluno.setGenero(alunoDTO.genero());
            aluno.setSituacao(alunoDTO.situacao());
            aluno.setUltimoNivel(alunoDTO.ultimoNivel());
            aluno.setEndereco(alunoDTO.endereco());
            aluno.setGrauParentesco(alunoDTO.grauParentesco());
            aluno.setResponsavelFinanceiro(alunoDTO.responsavelFinanceiro());

            // Corrigir a linha 71: Buscar o Responsavel pelo ID
            if (alunoDTO.responsavelId() != null) {
                Responsavel responsavel = responsavelRepository.findById(alunoDTO.responsavelId())
                        .orElseThrow(() -> new RuntimeException("Responsável não encontrado com ID: " + alunoDTO.responsavelId()));
                aluno.setResponsavel(responsavel);
            }

            // Associar turmas (implementar conforme necessário)
            if (alunoDTO.turmaIds() != null) {
                // Aqui seria necessário um TurmaRepository para buscar as turmas por ID
                // List<Turma> turmas = turmaRepository.findAllById(alunoDTO.turmaIds());
                // aluno.setTurmas(turmas);
            }

            Aluno savedAluno = alunoRepository.save(aluno);
            logger.info("Aluno criado com ID: {}", savedAluno.getId());
            return savedAluno;
        } catch (Exception e) {
            logger.error("Erro ao criar aluno: ", e);
            throw new RuntimeException("Erro ao criar aluno: " + e.getMessage());
        }
    }

    @Transactional
    public Aluno atualizarAluno(Long id, AlunoDTO alunoDTO) {
        try {
            Aluno alunoExistente = alunoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

            alunoExistente.setNomeCompleto(alunoDTO.nomeCompleto());
            alunoExistente.setEmail(alunoDTO.email());
            alunoExistente.setCpf(alunoDTO.cpf());
            alunoExistente.setRg(alunoDTO.rg());
            alunoExistente.setOrgaoExpeditorRg(alunoDTO.orgaoExpeditorRg());
            alunoExistente.setNacionalidade(alunoDTO.nacionalidade());
            alunoExistente.setUf(alunoDTO.uf());
            alunoExistente.setTelefone(alunoDTO.telefone());
            alunoExistente.setDataNascimento(alunoDTO.dataNascimento() != null ? LocalDateTime.parse(alunoDTO.dataNascimento(), DATE_TIME_FORMATTER) : null);
            alunoExistente.setNomeSocial(alunoDTO.nomeSocial());
            alunoExistente.setGenero(alunoDTO.genero());
            alunoExistente.setSituacao(alunoDTO.situacao());
            alunoExistente.setUltimoNivel(alunoDTO.ultimoNivel());
            alunoExistente.setEndereco(alunoDTO.endereco());
            alunoExistente.setGrauParentesco(alunoDTO.grauParentesco());
            alunoExistente.setResponsavelFinanceiro(alunoDTO.responsavelFinanceiro());

            // Corrigir a linha 83: Buscar o Responsavel pelo ID
            if (alunoDTO.responsavelId() != null) {
                Responsavel responsavel = responsavelRepository.findById(alunoDTO.responsavelId())
                        .orElseThrow(() -> new RuntimeException("Responsável não encontrado com ID: " + alunoDTO.responsavelId()));
                alunoExistente.setResponsavel(responsavel);
            }

            // Atualizar turmas (implementar conforme necessário)
            // if (alunoDTO.turmaIds() != null) {
            //     List<Turma> turmas = turmaRepository.findAllById(alunoDTO.turmaIds());
            //     alunoExistente.setTurmas(turmas);
            // }

            Aluno updatedAluno = alunoRepository.save(alunoExistente);
            logger.info("Aluno atualizado com ID: {}", updatedAluno.getId());
            return updatedAluno;
        } catch (Exception e) {
            logger.error("Erro ao atualizar aluno com ID {}: ", id, e);
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage());
        }
    }

    @Transactional
    public void deletarAluno(Long id) {
        try {
            if (alunoRepository.existsById(id)) {
                alunoRepository.deleteById(id);
                logger.info("Aluno deletado com ID: {}", id);
            } else {
                throw new RuntimeException("Aluno não encontrado com ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Erro ao deletar aluno com ID {}: ", id, e);
            throw new RuntimeException("Erro ao deletar aluno: " + e.getMessage());
        }
    }
}