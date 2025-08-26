package br.com.arirang.plataforma.dto;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TurmaDTO(
        Long id,
        @NotBlank(message = "Nome da turma é obrigatório")
        @Size(max = 120)
        String nomeTurma,
        Long professorResponsavelId,
        @Size(max = 60)
        String nivelProficiencia,
        @Size(max = 20)
        String diaTurma,
        @Size(max = 20)
        String turno,
        @Size(max = 20)
        String formato,
        @Size(max = 20)
        String modalidade,
        @Size(max = 80)
        String realizador,
        @Size(max = 5)
        String horaInicio,
        @Size(max = 5)
        String horaTermino,
        @Size(max = 10)
        String anoSemestre,
        Integer cargaHorariaTotal,
        LocalDateTime inicioTurma,
        LocalDateTime terminoTurma,
        @Size(max = 30)
        String situacaoTurma,
        List<Long> alunoIds
) {}