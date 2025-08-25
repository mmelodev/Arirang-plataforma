# Arirang-Plataforma

## Descrição
Arirang-Plataforma é uma aplicação web desenvolvida com Spring Boot para gerenciar uma escola de idiomas (Arirang) localizada em Manaus. O sistema oferece funcionalidades de gestão escolar, incluindo CRUD para alunos, professores, turmas e funcionários, com suporte a matrículas e solicitações de mudança de turma. A interface frontend utiliza Thymeleaf, e o backend integra um banco de dados MySQL via Hibernate.

## Funcionalidades
- **CRUD para Alunos, Professores e Turmas**: Criação, leitura, atualização e exclusão.
- **Relações Entidades**: Turma-Professor e Turma-Aluno, com endpoints para matrícula.
- **Formulários Frontend**: Templates Thymeleaf para listagem e cadastro (ex.: `turmas.html`, `turmas-form.html`).
- **Herança**: Classe `Funcionario` como superclasse abstrata, com `Professor` como subclasse.
- **Validações e DTOs**: Uso de DTOs para desacoplar entidades e validações básicas.

## Estrutura do Projeto
- `src/main/java/br.com.arirang.plataforma`:
    - `controller`: Contém `AlunoController`, `ProfessorController`, `TurmaController`, `CadastroController`.
    - `service`: Contém serviços como `AlunoService`, `ProfessorService`, `TurmaService`.
    - `entity`: Contém entidades como `Funcionario`, `Professor`, `Aluno`, `Turma`.
    - `dto`: Contém DTOs como `AlunoDTO`, `ProfessorDTO`, `TurmaDTO`.
    - `repository`: Contém repositórios JPA.
- `src/main/resources`:
    - `templates`: Contém `cadastro.html`, `turmas.html`, `turmas-form.html`, `professores.html`.
    - `static/css`: Contém estilos como `cadastro.css`, `turmas.css`, `turmas-form.css`, `professores.css`.
    - `application.properties`: Configurações de banco e Hibernate.

## Configuração e Instalação
1. **Pré-requisitos**:
    - Java 17 ou superior.
    - Maven 3.9+.
    - MySQL Server.
    - IntelliJ IDEA Ultimate (recomendado).

2. **Clone o Repositório**:
   ```bash
   git clone https://github.com/mmelodev/Arirang-plataforma.git
   cd Arirang-plataforma