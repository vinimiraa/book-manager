# Rubix Book Manager

Aplicativo de gerenciamento de livros desenvolvido em Java.  
Permite criar, listar, buscar, atualizar e excluir livros utilizando um banco de dados SQLite.  

## Documentação da Aplicação

A documentação completa do código fonte está disponível na pasta [`docs/`](docs/).  
Para abrir a documentação no navegador, clique no arquivo `index.html` ou use o link abaixo:

[Abrir Documentação HTML](docs/index.html)

Ela inclui informações detalhadas sobre cada classe, método, atributos e exemplos de uso.

### Recurso principal

**Livro**: Representa os livros gerenciados pela aplicação.  

| Propriedade       | Tipo       | Obrigatória | Descrição                                                  |
|-------------------|------------|-------------|------------------------------------------------------------|
| `titulo`          | String     | Sim         | Nome do livro (mínimo 2, máximo 255 caracteres)            |
| `autor`           | String     | Sim         | Autor do livro (mínimo 2, máximo 255 caracteres)           |
| `preco`           | Double     | Sim         | Preço do livro                                             |
| `editora`         | String     | Não         | Nome da editora (opcional, 2-255 caracteres)               |
| `dataPublicacao`  | LocalDate  | Não         | Data de publicação do livro (opcional, formato dd/MM/yyyy) |
| `isbn`            | Integer    | Não         | Código ISBN do livro (opcional)                            |


## Tecnologias e Linguagem

- Linguagem: **Java 24**
- Gerenciamento de dependências: **Maven**
- Banco de dados: **SQLite**
- Bibliotecas externas:
  - **sqlite-jdbc 3.50.3.0**: conexão com SQLite
  - **JUnit 5.10.0**: testes unitários

## Configuração do Ambiente

### Pré-requisitos

- Java 24 JDK instalado
- Maven instalado
- Editor de código (IDE) de sua preferência

### Passos para instalar dependências

Dentro do diretório do projeto, execute:

```bash
mvn clean install
```

Isso irá baixar todas as dependências e compilar o projeto.

## Iniciando a Aplicação

Para compilar:

```bash
mvn compile
```

Para executar diretamente pelo Maven:

```bash
mvn exec:java -Dexec.mainClass="com.rubix.vinimiraa.Main"
```

> **Nota:** Não é recomendado executar diretamente com `java Main.java`, pois o Maven gerencia o classpath e dependências.

## Funcionalidades

### Menu principal

Ao iniciar, o usuário verá o seguinte menu:

```
------------------------------
Rubix - Gerenciador de Livros
Vinicius Miranda de Araujo
------------------------------
> Inicio
1 - Cadastrar Livro
2 - Listar Livros
3 - Buscar Livro
4 - Atualizar Livro
5 - Excluir Livro
0 - Sair
Digite o número da opção:
```

### 1. Cadastrar Livro

- Solicita título, autor, preço (obrigatórios) e editora, data de publicação e ISBN (opcionais).
- Confirmação antes de salvar.

### 2️. Listar Livros

- Lista todos os livros cadastrados, ordenados pelo título.

### 3️. Buscar Livro

- Busca pelo **ID do livro** e exibe todos os detalhes.

### 4️. Atualizar Livro

- Busca pelo ID.
- Permite atualizar qualquer campo individualmente.
- Confirmação antes de salvar.

### 5️. Excluir Livro

- Busca pelo ID.
- Exibe detalhes do livro.
- Confirmação antes de deletar.

## Diferenciais

- Validação completa de entradas do usuário.
- Suporte a campos opcionais.
- Persistência via SQLite.
- Normalização de strings para busca consistente.
- Interface de console intuitiva.

## Testes Unitários

- Localizados em `src/test/java/com/rubix/vinimiraa/`.
- Bibliotecas usadas: **JUnit 5**
- Exemplos de testes:
  - `LivroTest.java`: valida criação e atributos do livro.
  - `LivroDAOTest.java`: valida operações CRUD no banco de dados.
- Executar testes:

```bash
mvn test
```

## Estrutura do Projeto

```
book-manager/
├─ docs/                   # Documentação gerada pelo Javadoc
├─ src/
│  ├─ main/java/
│  │  └─ com/rubix/vinimiraa/
│  │       ├─ Main.java
│  │       ├─ dao/
│  │       ├─ model/
│  │       └─ util/
│  └─ test/java/
│       └─ com/rubix/vinimiraa/
├─ target/                 # Build output (pode ser limpo com mvn clean)
├─ livros.db               # Banco SQLite
├─ pom.xml
└─ README.md
```

## Observações Finais

- O arquivo de banco `livros.db` é criado automaticamente ao rodar o aplicativo pela primeira vez.
- Para atualizar a estrutura do banco, utilize o script SQL em `src/main/resources/db/createTable.sql`.
- Recomenda-se usar Maven para compilar e executar para garantir o correto carregamento de dependências.
- A documentação completa pode ser aberta no navegador diretamente em [`docs/index.html`](docs/index.html).

