package br.com.arirang.plataforma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.arirang.plataforma.Aluno;

import br.com.arirang.plataforma.repository.AlunoRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@PostMapping
	public Aluno criarAluno(@RequestBody Aluno novoAluno) {
		return alunoRepository.save(novoAluno);
	}
	

}
