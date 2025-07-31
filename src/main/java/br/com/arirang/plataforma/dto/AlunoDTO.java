package br.com.arirang.plataforma.dto;

import jakarta.validation.constraints.*;

public record AlunoDTO(
		@NotNull(message = "ID não pode ser nulo para atualizações")
		Long id,
		@NotBlank(message = "Nome completo é obrigatório")
		String nomeCompleto,
		@NotBlank(message = "Email é obrigatório")
		@Email(message = "Email deve ser válido")
		String email,
		@NotBlank(message = "CPF é obrigatório")
		@Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
		String cpf,
		@NotBlank(message = "RG/RNE é obrigatório")
		String rgRne,
		@NotBlank(message = "Nacionalidade é obrigatória")
		String nacionalidade,
		@NotBlank(message = "CEP é obrigatório")
		@Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
		String cep,
		@NotBlank(message = "Endereço é obrigatório")
		String endereco,
		@NotBlank(message = "Data de nascimento é obrigatória")
		@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Data de nascimento deve estar no formato AAAA-MM-DD")
		String dataNascimento,
		@NotNull(message = "Responsável financeiro é obrigatório")
		boolean responsavelFinanceiro) {}