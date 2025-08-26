package br.com.arirang.plataforma.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessorDTO(
        Long id,
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150)
        String nomeCompleto,
        LocalDateTime dataNascimento,
        @Size(max = 20)
        String rg,
        @Size(max = 14)
        String cpf,
        @Size(max = 20)
        String telefone,
        @Size(max = 80)
        String cargo,
        @Size(max = 120)
        String formacao
) {}