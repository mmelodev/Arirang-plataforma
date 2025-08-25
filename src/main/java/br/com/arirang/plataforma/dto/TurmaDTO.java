package br.com.arirang.plataforma.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TurmaDTO(
        Long id,
        String nomeTurma,
        Long professorResponsavelId,
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
) {}