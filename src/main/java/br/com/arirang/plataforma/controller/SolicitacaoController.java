package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.model.StatusSolicitacao;
import br.com.arirang.plataforma.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @PostMapping
    public ResponseEntity<Void> criarSolicitacao(@RequestParam Long alunoId, @RequestParam Long turmaNovaId) {
        solicitacaoService.criarSolicitacao(alunoId, turmaNovaId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}/aprovar")
    public ResponseEntity<Void> aprovarSolicitacao(@PathVariable Long id) {
        solicitacaoService.aprovarSolicitacao(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rejeitar")
    public ResponseEntity<Void> rejeitarSolicitacao(@PathVariable Long id) {
        solicitacaoService.rejeitarSolicitacao(id);
        return ResponseEntity.ok().build();
    }
}