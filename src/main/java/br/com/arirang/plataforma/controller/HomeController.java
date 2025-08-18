package br.com.arirang.plataforma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home"; // Retorna o template home.html
    }

    @GetMapping("/funcionarios")
    public String funcionarios(Model model) {
        // Placeholder para mensagens (substituir por lógica real se houver controllers específicos)
        model.addAttribute("recepcaoMsg", "Funcionalidade em desenvolvimento");
        model.addAttribute("limpezaMsg", "Funcionalidade em desenvolvimento");
        return "funcionarios"; // Retorna o template funcionarios.html
    }
}