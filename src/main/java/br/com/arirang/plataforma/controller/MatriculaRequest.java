package br.com.arirang.plataforma.controller;

import jakarta.validation.constraints.NotNull;

public record MatriculaRequest(@NotNull(message = "O ID do aluno é obrigatório") Long alunoId) {}