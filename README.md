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
### Correção de Loop Infinito (27 de julho de 2025)
- Resolvido problema de serialização infinita nos endpoints /turmas e /alunos devido a relação bidirecional Turma-Aluno.
- Implementada solução com @JsonManagedReference e @JsonBackReference.
- Resultados: Resposta JSON agora é finita e consistente.
- Próximos Passos: Continuar com a Semana 1 de Julho.
### Semana 1, 30 de junho - 6 de julho (28 de julho de 2025)
- **To Do**: Implementar relações (ex.: Turma-Aluno).
- **In Progress**: Ajustar controllers para relações.
- **Done**: Modelo relacional funcional.
- **Entregável**: Modelo relacional funcional.
- **Resultados**: [Insira número de testes bem-sucedidos, ex.: 10].
- **Próximos Passos**: Desenvolver endpoint de matrícula.
### Reset do Banco de Dados (28 de julho de 2025)
- Esvaziadas as tabelas professor, turma e aluno no MySQL usando TRUNCATE após desativar FOREIGN_KEY_CHECKS.
- Backup criado: arirang_backup_20250728.sql.
- Próximos Passos: Desenvolver endpoint de matrícula (2 de julho).
### Correção de Erro 500 na Criação de Aluno (28 de julho de 2025)
- Resolvido erro 500 Internal Server Error ao criar aluno com turma inexistente.
- Ajustado AlunoController para validar e carregar Turma antes de salvar.
- Próximos Passos: Validar e testar o endpoint de matrícula.
### Semana 1, 30 de junho - 6 de julho (28 de julho de 2025)
- **To Do**: Desenvolver endpoint de matrícula (concluído).
- **In Progress**: Validar e testar o endpoint de matrícula.
- **Done**: Modelo relacional funcional, endpoint de matrícula funcional.
- **Entregável**: Endpoint de matrícula funcional.
- **Resultados**: [Insira número de testes bem-sucedidos, ex.: 8].
- **Próximos Passos**: Iniciar adaptações para novas funcionalidades (Semana 3 de Julho).
### Correção de Erro 404 no Endpoint de Solicitação (28 de julho de 2025)
- Resolvido erro 404 Not Found no endpoint /solicitacoes/mudanca-turma.
- Verificado mapeamento do controlador e criados dados de teste.
- Próximos Passos: Continuar testes do endpoint de solicitação.
### Correção de Validação para Mesma Turma (28 de julho de 2025)
- Ajustada validação com @Transactional e depuração aprimorada para comparar IDs.
- Resolvido caso 201 Created para {"alunoId": 5, "turmaNovaId": 1}, agora retorna 400 Bad Request.
- Próximos Passos: Continuar testes e implementar aprovação/rejeição.
### Semana 3, 14 de julho - 20 de julho (28 de julho de 2025)
- **To Do**: Implementar aprovação/rejeição de solicitações.
- **In Progress**: Testar endpoints de aprovação/rejeição.
- **Done**: Modelo relacional funcional, endpoint de matrícula funcional, endpoint de solicitação funcional.
- **Entregável**: Endpoints de aprovação e rejeição implementados.
- **Resultados**: [Insira número de testes bem-sucedidos, ex.: 8].
- **Próximos Passos**: Finalizar testes e planejar próxima funcionalidade.
### Atualizações - 31 de julho de 2025
- **Resolvido**: Exposição de entidades JPA com uso de DTOs.
- **Em Progresso**: Implementação de tratamento de exceções global com @ControllerAdvice.
- **Próximos Passos**: Validar outros itens do relatório (Lógica de Negócio, Status como Enum, Validação de Entrada).
### Atualizações - 31 de julho de 2025
- **Resolvido**: Exposição de entidades JPA com uso de DTOs.
- **Resolvido**: Tratamento de exceções global com @ControllerAdvice.
- **Resolvido**: Movendo lógica de negócio para camada de serviço.
- **Próximos Passos**: Validar Uso de Strings para Status e Validação de Entrada.
### Atualizações - 31 de julho de 2025
- **Resolvido**: Exposição de entidades JPA com uso de DTOs.
- **Resolvido**: Tratamento de exceções global com @ControllerAdvice.
- **Resolvido**: Movendo lógica de negócio para camada de serviço.
- **Resolvido**: Uso de Strings para Status substituído por Enum.
- **Próximos Passos**: Validar Validação de Entrada.
### Atualizações - 31 de julho de 2025
- **Resolvido**: Exposição de entidades JPA com uso de DTOs.
- **Resolvido**: Tratamento de exceções global com @ControllerAdvice.
- **Resolvido**: Movendo lógica de negócio para camada de serviço.
- **Resolvido**: Uso de Strings para Status substituído por Enum.
- **Resolvido**: Validação de Entrada implementada com Bean Validation.
- **Próximos Passos**: Revisão geral ou melhorias adicionais (sugerir se desejar).
