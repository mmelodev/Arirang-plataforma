package br.com.arirang.plataforma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TurmaDTO(
        @NotNull(message = "ID não pode ser nulo para atualizações")
        Long id,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        Long professorId, // Opcional, então sem @NotNull
        List<AlunoDTO> alunos) {}