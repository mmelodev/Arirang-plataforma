package br.com.arirang.plataforma.dto;

import br.com.arirang.plataforma.entity.Endereco;
import br.com.arirang.plataforma.entity.Responsavel;

import java.util.List;

public record AlunoDTO(
		Long id,
		String nomeCompleto,
		String email,
		String cpf,
		String rg,
		String orgaoExpeditorRg,
		String nacionalidade,
		String uf,
		String telefone,
		String dataNascimento,
		String nomeSocial,
		String genero,
		String situacao,
		String ultimoNivel,
		Endereco endereco,
		Long responsavelId, // Usando Long em vez de Responsavel
		String grauParentesco,
		boolean responsavelFinanceiro,
		List<Long> turmaIds
) {}