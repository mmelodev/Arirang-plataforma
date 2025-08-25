package br.com.arirang.plataforma.controller;

import br.com.arirang.plataforma.dto.AlunoDTO;
import br.com.arirang.plataforma.dto.ProfessorDTO;
import br.com.arirang.plataforma.dto.TurmaDTO;
import br.com.arirang.plataforma.entity.Aluno;
import br.com.arirang.plataforma.entity.Professor;
import br.com.arirang.plataforma.entity.Turma;
import br.com.arirang.plataforma.service.AlunoService;
import br.com.arirang.plataforma.service.ProfessorService;
import br.com.arirang.plataforma.service.TurmaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    private static final Logger logger = LoggerFactory.getLogger(CadastroController.class);

    @Autowired
    private AlunoService alunoService;
    @Autowired
    private ProfessorService professorService;
    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public String cadastro(Model model) {
        List<CadastroItem> cadastros = new ArrayList<>();

        try {
            // Adicionar Alunos
            cadastros.addAll(alunoService.listarTodosAlunos().stream()
                    .map(aluno -> new CadastroItem("Aluno", aluno.getId(), aluno.getNomeCompleto(), aluno.getEmail(), aluno.getTelefone()))
                    .collect(Collectors.toList()));

            // Adicionar Professores
            cadastros.addAll(professorService.listarTodosProfessores().stream()
                    .map(professor -> new CadastroItem("Professor", professor.getId(), professor.getNomeCompleto(), null, professor.getTelefone()))
                    .collect(Collectors.toList()));

            // Adicionar Turmas (simplificado, sem e-mail)
            cadastros.addAll(turmaService.listarTodasTurmas().stream()
                    .map(turma -> new CadastroItem("Turma", turma.getId(), turma.getNomeTurma(), null, null))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            logger.error("Erro ao carregar cadastros: ", e);
            model.addAttribute("error", "Erro ao carregar a lista de cadastros: " + e.getMessage());
            return "error"; // Retorna um template de erro genérico
        }

        model.addAttribute("cadastros", cadastros);
        return "cadastro";
    }

    // Classe auxiliar para unificar os cadastros
    public static class CadastroItem {
        private String tipo;
        private Long id;
        private String nome;
        private String email;
        private String telefone;

        public CadastroItem(String tipo, Long id, String nome, String email, String telefone) {
            this.tipo = tipo;
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
        }

        public String getTipo() { return tipo; }
        public Long getId() { return id; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }
        public String getTelefone() { return telefone; }
    }
}