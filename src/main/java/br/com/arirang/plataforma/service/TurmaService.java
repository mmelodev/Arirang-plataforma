package br.com.arirang.plataforma.service;

import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.repository.TurmaRepository;
import br.com.arirang.plataforma.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional
    public Turma criarTurma(String nomeTurma, Professor professorResponsavel, String nivelProficiencia,
                            String diaTurma, String turno, String formato, String modalidade, String realizador,
                            LocalDateTime horaInicio, LocalDateTime horaTermino, String anoSemestre,
                            Integer cargaHorariaTotal, LocalDateTime inicioTurma, LocalDateTime terminoTurma,
                            String situacaoTurma, List<Long> alunoIds) {
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

        // Validação para realizador Particular
        if ("Particular".equals(realizador) && (
                horaInicio == null || horaTermino == null ||
                        cargaHorariaTotal == null || inicioTurma == null || terminoTurma == null)) {
            throw new IllegalArgumentException("Hora, carga horária e início/termino são obrigatórios para turmas particulares.");
        }

        // Vinculação com alunos
        if (alunoIds != null && !alunoIds.isEmpty()) {
            List<Aluno> alunos = alunoRepository.findAllById(alunoIds)
                    .stream()
                    .filter(a -> a != null)
                    .collect(Collectors.toList());
            if (alunos.size() != alunoIds.size()) {
                throw new RuntimeException("Um ou mais alunos não foram encontrados.");
            }
            turma.setAlunos(alunos);
        }

        turma.setUltimaModificacao(LocalDateTime.now());
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
    public Turma atualizarTurma(Long id, String nomeTurma, Professor professorResponsavel, String nivelProficiencia,
                                String diaTurma, String turno, String formato, String modalidade, String realizador,
                                LocalDateTime horaInicio, LocalDateTime horaTermino, String anoSemestre,
                                Integer cargaHorariaTotal, LocalDateTime inicioTurma, LocalDateTime terminoTurma,
                                String situacaoTurma, List<Long> alunoIds) {
        Turma turmaExistente = turmaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
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

        // Validação para realizador Particular
        if ("Particular".equals(realizador) && (
                horaInicio == null || horaTermino == null ||
                        cargaHorariaTotal == null || inicioTurma == null || terminoTurma == null)) {
            throw new IllegalArgumentException("Hora, carga horária e início/termino são obrigatórios para turmas particulares.");
        }

        // Vinculação com alunos
        if (alunoIds != null && !alunoIds.isEmpty()) {
            List<Aluno> alunos = alunoRepository.findAllById(alunoIds)
                    .stream()
                    .filter(a -> a != null)
                    .collect(Collectors.toList());
            if (alunos.size() != alunoIds.size()) {
                throw new RuntimeException("Um ou mais alunos não foram encontrados.");
            }
            turmaExistente.setAlunos(alunos);
        }

        turmaExistente.setUltimaModificacao(LocalDateTime.now());
        return turmaRepository.save(turmaExistente);
    }

    @Transactional
    public void deletarTurma(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada");
        }
        turmaRepository.deleteById(id);
    }
}