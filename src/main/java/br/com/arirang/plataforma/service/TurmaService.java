package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.repository.TurmaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    private static final Logger logger = LoggerFactory.getLogger(TurmaService.class);

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional
    public Turma criarTurma(
            String nomeTurma,
            Professor professorResponsavel,
            String nivelProficiencia,
            String diaTurma,
            String turno,
            String formato,
            String modalidade,
            String realizador,
            String horaInicio,
            String horaTermino,
            String anoSemestre,
            Integer cargaHorariaTotal,
            LocalDateTime inicioTurma,
            LocalDateTime terminoTurma,
            String situacaoTurma,
            List<Long> alunoIds
    ) {
        try {
            Turma turma = new Turma();
            turma.setNomeTurma(nomeTurma);
            turma.setProfessorResponsavel(professorResponsavel);
            turma.setNivelProficiencia(nivelProficiencia);
            turma.setDiaTurma(diaTurma);
            turma.setTurno(turno);
            turma.setFormato(formato);
            turma.setModalidade(modalidade);
            turma.setRealizador(realizador);
            turma.setHoraInicio(horaInicio);
            turma.setHoraTermino(horaTermino);
            turma.setAnoSemestre(anoSemestre);
            turma.setCargaHorariaTotal(cargaHorariaTotal);
            turma.setInicioTurma(inicioTurma);
            turma.setTerminoTurma(terminoTurma);
            turma.setSituacaoTurma(situacaoTurma);
            // Associação de alunos pode ser feita separadamente (via TurmaRepository ou outro serviço)

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
    public Turma atualizarTurma(
            Long id,
            String nomeTurma,
            Professor professorResponsavel,
            String nivelProficiencia,
            String diaTurma,
            String turno,
            String formato,
            String modalidade,
            String realizador,
            String horaInicio,
            String horaTermino,
            String anoSemestre,
            Integer cargaHorariaTotal,
            LocalDateTime inicioTurma,
            LocalDateTime terminoTurma,
            String situacaoTurma,
            List<Long> alunoIds
    ) {
        try {
            Turma turmaExistente = turmaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Turma não encontrada com ID: " + id));

            turmaExistente.setNomeTurma(nomeTurma);
            turmaExistente.setProfessorResponsavel(professorResponsavel);
            turmaExistente.setNivelProficiencia(nivelProficiencia);
            turmaExistente.setDiaTurma(diaTurma);
            turmaExistente.setTurno(turno);
            turmaExistente.setFormato(formato);
            turmaExistente.setModalidade(modalidade);
            turmaExistente.setRealizador(realizador);
            turmaExistente.setHoraInicio(horaInicio);
            turmaExistente.setHoraTermino(horaTermino);
            turmaExistente.setAnoSemestre(anoSemestre);
            turmaExistente.setCargaHorariaTotal(cargaHorariaTotal);
            turmaExistente.setInicioTurma(inicioTurma);
            turmaExistente.setTerminoTurma(terminoTurma);
            turmaExistente.setSituacaoTurma(situacaoTurma);
            // Associação de alunos pode ser feita separadamente

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