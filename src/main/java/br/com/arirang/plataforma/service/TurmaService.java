package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.repository.AlunoRepository;
import br.com.arirang.plataforma.repository.ProfessorRepository;
import br.com.arirang.plataforma.repository.TurmaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    private static final Logger logger = LoggerFactory.getLogger(TurmaService.class);

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository; // Dependency needed for conversion

    @Autowired
    private AlunoRepository alunoRepository; // Dependency needed for conversion

    @Transactional
    public Turma criarTurma(TurmaDTO turmaDTO) {
        try {
            Turma turma = new Turma();
            turma.setNomeTurma(turmaDTO.nomeTurma());
            turma.setNivelProficiencia(turmaDTO.nivelProficiencia());
            turma.setDiaTurma(turmaDTO.diaTurma());
            turma.setTurno(turmaDTO.turno());
            turma.setFormato(turmaDTO.formato());
            turma.setModalidade(turmaDTO.modalidade());
            turma.setRealizador(turmaDTO.realizador());
            turma.setHoraInicio(turmaDTO.horaInicio());
            turma.setHoraTermino(turmaDTO.horaTermino());
            turma.setAnoSemestre(turmaDTO.anoSemestre());
            turma.setCargaHorariaTotal(turmaDTO.cargaHorariaTotal());
            turma.setInicioTurma(turmaDTO.inicioTurma());
            turma.setTerminoTurma(turmaDTO.terminoTurma());
            turma.setSituacaoTurma(turmaDTO.situacaoTurma());

            if (turmaDTO.professorResponsavelId() != null) {
                Professor professor = professorRepository.findById(turmaDTO.professorResponsavelId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + turmaDTO.professorResponsavelId()));
                turma.setProfessorResponsavel(professor);
            }

            if (turmaDTO.alunoIds() != null && !turmaDTO.alunoIds().isEmpty()) {
                List<Aluno> alunos = alunoRepository.findAllById(turmaDTO.alunoIds());
                turma.setAlunos(alunos);
            }

            Turma savedTurma = turmaRepository.save(turma);
            logger.info("Turma criada com ID: {}", savedTurma.getId());
            return savedTurma;
        } catch (Exception e) {
            logger.error("Erro ao criar turma: ", e);
            throw new RuntimeException("Erro ao criar turma: " + e.getMessage());
        }
    }

    public List<Turma> listarTodasTurmas() {
        try {
            return turmaRepository.findAll();
        } catch (Exception e) {
            logger.error("Erro ao listar todas as turmas: ", e);
            throw new RuntimeException("Erro ao listar turmas: " + e.getMessage());
        }
    }

    public Optional<Turma> buscarTurmaPorId(Long id) {
        try {
            return turmaRepository.findById(id);
        } catch (Exception e) {
            logger.error("Erro ao buscar turma por ID {}: ", id, e);
            throw new RuntimeException("Erro ao buscar turma: " + e.getMessage());
        }
    }

    @Transactional
    public Turma atualizarTurma(Long id, TurmaDTO turmaDTO) {
        try {
            Turma turmaExistente = turmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + id));

            turmaExistente.setNomeTurma(turmaDTO.nomeTurma());
            turmaExistente.setNivelProficiencia(turmaDTO.nivelProficiencia());
            turmaExistente.setDiaTurma(turmaDTO.diaTurma());
            turmaExistente.setTurno(turmaDTO.turno());
            turmaExistente.setFormato(turmaDTO.formato());
            turmaExistente.setModalidade(turmaDTO.modalidade());
            turmaExistente.setRealizador(turmaDTO.realizador());
            turmaExistente.setHoraInicio(turmaDTO.horaInicio());
            turmaExistente.setHoraTermino(turmaDTO.horaTermino());
            turmaExistente.setAnoSemestre(turmaDTO.anoSemestre());
            turmaExistente.setCargaHorariaTotal(turmaDTO.cargaHorariaTotal());
            turmaExistente.setInicioTurma(turmaDTO.inicioTurma());
            turmaExistente.setTerminoTurma(turmaDTO.terminoTurma());
            turmaExistente.setSituacaoTurma(turmaDTO.situacaoTurma());

            if (turmaDTO.professorResponsavelId() != null) {
                Professor professor = professorRepository.findById(turmaDTO.professorResponsavelId())
                        .orElseThrow(() -> new RuntimeException("Professor não encontrado com ID: " + turmaDTO.professorResponsavelId()));
                turmaExistente.setProfessorResponsavel(professor);
            } else {
                turmaExistente.setProfessorResponsavel(null);
            }

            if (turmaDTO.alunoIds() != null && !turmaDTO.alunoIds().isEmpty()) {
                List<Aluno> alunos = alunoRepository.findAllById(turmaDTO.alunoIds());
                turmaExistente.setAlunos(alunos);
            } else {
                turmaExistente.setAlunos(Collections.emptyList());
            }

            Turma updatedTurma = turmaRepository.save(turmaExistente);
            logger.info("Turma atualizada com ID: {}", updatedTurma.getId());
            return updatedTurma;
        } catch (Exception e) {
            logger.error("Erro ao atualizar turma com ID {}: ", id, e);
            throw new RuntimeException("Erro ao atualizar turma: " + e.getMessage());
        }
    }

    @Transactional
    public void deletarTurma(Long id) {
        try {
            if (turmaRepository.existsById(id)) {
                turmaRepository.deleteById(id);
                logger.info("Turma deletada com ID: {}", id);
            } else {
                throw new RuntimeException("Turma não encontrada com ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Erro ao deletar turma com ID {}: ", id, e);
            throw new RuntimeException("Erro ao deletar turma: " + e.getMessage());
        }
    }
}
