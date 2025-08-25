package br.com.arirang.plataforma.dto;

import java.time.LocalDateTime;

public record ProfessorDTO(
        Long id,
        String nomeCompleto,
        LocalDateTime dataNascimento, // Campo adicional
        String rg,                   // Campo adicional
        String cpf,                  // Campo adicional
        String telefone,
        String cargo,
        String formacao
) {}