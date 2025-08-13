package br.com.arirang.plataforma.dto;

import br.com.arirang.plataforma.entity.Aluno;

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
        LocalDateTime horaInicio,
        LocalDateTime horaTermino,
        String anoSemestre,
        Integer cargaHorariaTotal,
        LocalDateTime inicioTurma,
        LocalDateTime terminoTurma,
        String situacaoTurma,
        LocalDateTime ultimaModificacao,
        List<Long> alunoIds
) {}