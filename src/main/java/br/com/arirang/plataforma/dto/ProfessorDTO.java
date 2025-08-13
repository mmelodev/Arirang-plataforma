package br.com.arirang.plataforma.dto;

import java.time.LocalDateTime;

public record ProfessorDTO(
        Long id,
        String nomeCompleto,
        LocalDateTime dataNascimento,
        String rg,
        String cpf,
        String telefone,
        String cargo,
        String formacao
) {}