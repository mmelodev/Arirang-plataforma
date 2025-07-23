# Documentação do Projeto AriranG

## Visão Geral
O projeto AriranG é um sistema de gestão escolar desenvolvido com Spring Boot, integrando uma API REST para gerenciamento de alunos. Iniciado em junho de 2025, o objetivo é criar uma solução escalável para administração escolar.

## Progresso

### Quinta-feira, Semana 3 (15 de julho de 2025)
- **Endpoints Implementados e Testados**:
  - **POST /alunos**: Cadastra um novo aluno.
    - Requisição: `{"nomeCompleto": "Ana Beatriz", "email": "ana.b@email.com", "cpf": "111.222.333-44", "dataNascimento": "2005-10-15"}`
    - Resposta: 201 Created com o aluno salvo.
  - **GET /alunos**: Lista todos os alunos.
    - Resposta: 200 OK com array de alunos.
  - **GET /alunos/{id}**: Busca um aluno por ID.
    - Resposta: 200 OK com dados do aluno ou 404 Not Found.
- **Resultados**:
  - Total de alunos cadastrados: 13.
  - IDs testados: [
    {
        "id": 1,
        "nomeCompleto": "Ana Beatriz Silva",
        "email": "ana.b@email.com",
        "cpf": "111.222.333-44",
        "dataNascimento": "2005-10-15"
    },
    {
        "id": 2,
        "nomeCompleto": "Bruno Costa",
        "email": "bruno.c@email.com",
        "cpf": "999.999.999-99",
        "dataNascimento": "2005-10-16"
    },
    {
        "id": 3,
        "nomeCompleto": "Murilo Melo",
        "email": "murilomelo@email.com",
        "cpf": "024.609.232-74",
        "dataNascimento": "2005-10-11"
    },
    {
        "id": 4,
        "nomeCompleto": "Juliete Pantoja",
        "email": "juliete.p@email.com",
        "cpf": "000.000.000-00",
        "dataNascimento": "2011-09-21"
    },
    {
        "id": 5,
        "nomeCompleto": "Pedro Sampaio",
        "email": "pedro.sa@email.com",
        "cpf": "111.111.111-11",
        "dataNascimento": "2009-11-10"
    },
    {
        "id": 6,
        "nomeCompleto": "Lucas Almeida",
        "email": "lucas.al@email.com",
        "cpf": "121.121.121.-12",
        "dataNascimento": "2004-01-29"
    },
    {
        "id": 7,
        "nomeCompleto": "Carolina Santos",
        "email": "carolina.s@email.com",
        "cpf": "232.232.232-23",
        "dataNascimento": "1999-05-12"
    },
    {
        "id": 8,
        "nomeCompleto": "Rafael Oliveira",
        "email": "rafael.o@email.com",
        "cpf": "343.343.343-34",
        "dataNascimento": "2002-11-08"
    },
    {
        "id": 9,
        "nomeCompleto": "Juliana Pereira",
        "email": "juliana.p@email.com",
        "cpf": "454.454.454-45",
        "dataNascimento": "2001-03-22"
    },
    {
        "id": 11,
        "nomeCompleto": "Fernanda Lima",
        "email": "fernanda.l@email.com",
        "cpf": "676.676.676-67",
        "dataNascimento": "2000-09-18"
    },
    {
        "id": 12,
        "nomeCompleto": "",
        "email": "fernanda.l@email.com",
        "cpf": "676.676.676-67",
        "dataNascimento": "2000-09-18"
    },
    {
        "id": 13,
        "nomeCompleto": "",
        "email": "fernanda.l@email.com",
        "cpf": "676.676.676-67",
        "dataNascimento": "2000-09-18"
    },
    {
        "id": 14,
        "nomeCompleto": "",
        "email": "fernanda.l@email.com",
        "cpf": "676.676.676-67",
        "dataNascimento": "2000-09-18"
    }
].
  - Observações: Todos os testes foram bem-sucedidos.

### Sexta-feira, Semana 3 (15 de julho de 2025)
- **Testes Exaustivos**:
  - Validação de fluxos e erros nos endpoints existentes (POST, GET, GET/{id}).
  - Testes incluíram cadastros múltiplos, buscas por IDs inexistentes (404), e validação no banco.
- **Novos Endpoints**:
  - **PUT /alunos/{id}**: Atualiza um aluno.
    - Requisição: `{"nomeCompleto": "Ana Beatriz Silva", "email": "ana.silva@email.com", ...}`
    - Resposta: 200 OK com dados atualizados ou 404 Not Found.
  - **DELETE /alunos/{id}**: Remove um aluno.
    - Requisição: Nenhum corpo, apenas o ID na URL.
    - Resposta: 204 No Content (sucesso) ou 404 Not Found.
- **Resultados**:
  - Total de atualizações testadas: [insira o número, ex.: 2].
  - Total de exclusões testadas: [insira o número, ex.: 1].
- **Tecnologias**:
  - Spring Boot 3.2.5
  - Hibernate 6.4.4.Final
  - MySQL
- **Próximos Passos**:
  - Semana 4: Expansão para gerenciamento de professores e turmas, testes de integração.
### Atualização de Ferramentas (23 de julho de 2025)
- Projeto migrado do Eclipse para IntelliJ Ultimate com sucesso.
- Configurações ajustadas: JDK, Maven, Spring Boot, e Database Tool integrados.
- Próximos Passos: Início da Semana 4 de Junho com entidades Professor e Turma.
### Semana 4, 23-29 de junho (23 de julho de 2025)
- **To Do**: Criar entidades Professor e Turma, implementar controllers.
- **In Progress**: Testar endpoints de professores e turmas, iniciar integração.
- **Done**: CRUD básico para professores e turmas concluído.
- **Entregável**: API expandida para professores e turmas.
- **Próximos Passos**: Iniciar relações Turma-Aluno e interface web.
- ### Semana 4, 23-29 de junho (25 de junho de 2025)
- **To Do**: Criar entidades Professor e Turma, implementar controllers.
- **In Progress**: Testar endpoints de professores e turmas, iniciar integração.
- **Done**: CRUD básico para professores e turmas concluído.
- **Entregável**: API expandida para professores e turmas.
- **Próximos Passos**: Iniciar relações Turma-Aluno e interface web.