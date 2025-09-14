package com.rubix.vinimiraa.model;

import java.time.LocalDate;

import com.rubix.vinimiraa.util.IO;


/**
 * Representa um livro dentro da aplicação.
 * <p>
 * Um livro possui os atributos obrigatórios {@code titulo}, {@code autor} e {@code preco}, 
 * e atributos opcionais como {@code editora}, {@code dataPublicacao} e {@code isbn}.
 * </p>
 *
 * <p>Exemplo de criação usando o construtor:</p>
 * <pre>{@code
 * Livro l = new Livro(1, "Dom Casmurro", "Machado de Assis", 29.90,
 *                     "Editora Garnier", LocalDate.of(1899, 1, 1), 123456);
 * }</pre>
 *
 * <p>Exemplo de criação usando o {@link Livro.Builder Builder}:</p>
 * <pre>{@code
 * Livro l = new Livro.Builder("Dom Casmurro", "Machado de Assis", 29.90)
 *                  .editora("Editora Garnier")
 *                  .dataPublicacao(LocalDate.of(1899, 1, 1))
 *                  .isbn(123456)
 *                  .build();
 * }</pre>
 *
 * @see com.rubix.vinimiraa.util.IO#strNormalize(String)
 * @since 1.0
 */
public class Livro implements Comparable<Livro>
{
    /** Identificador único do livro no banco de dados. */
    private int id;

    /** Título do livro (obrigatório). */
    private String titulo;

    /** Autor do livro (obrigatório). */
    private String autor;

    /** Preço do livro (obrigatório). */
    private Double preco;

    /** Editora do livro (opcional). */
    private String editora;

    /** Data de publicação do livro (opcional). */
    private LocalDate dataPublicacao;

    /** ISBN do livro (opcional). */
    private Integer isbn;

    /**
     * Construtor padrão. Cria um livro vazio com valores default.
     */
    public Livro( ) {
        this(-1, "", "", 0.0, null, null, null);
    }

    /**
     * Construtor completo da classe.
     *
     * @param id              identificador único
     * @param titulo          título do livro (não pode ser nulo ou vazio)
     * @param autor           autor do livro (não pode ser nulo ou vazio)
     * @param preco           preço do livro (não pode ser nulo)
     * @param editora         editora (pode ser nulo)
     * @param dataPublicacao  data de publicação (pode ser nulo)
     * @param isbn            código ISBN (pode ser nulo)
     */
    public Livro(int id, String titulo, String autor, Double preco, String editora, LocalDate dataPublicacao, Integer isbn)
    {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
        this.editora = editora;
        this.dataPublicacao = dataPublicacao;
        this.isbn = isbn;
    }

    /**
     * Construtor privado usado apenas pelo {@link Builder}.
     *
     * @param builder instância do builder
     */
    private Livro(Builder builder) 
    {
        this.id = builder.id;
        this.titulo = builder.titulo;
        this.autor = builder.autor;
        this.preco = builder.preco;
        this.editora = builder.editora;
        this.dataPublicacao = builder.dataPublicacao;
        this.isbn = builder.isbn;
    }

    /**
     * Retorna o ID do livro.
     * @return O ID do livro.
     */
    public int getId( ) { return this.id; }

    /**
     * Retorna o título do livro.
     * @return O título do livro.
     */
    public String getTitulo( ) { return this.titulo; }

    /**
     * Retorna o autor do livro.
     * @return O autor do livro.
     */
    public String getAutor( ) { return this.autor; }

    /**
     * Retorna o preço do livro.
     * @return O preço do livro.
     */
    public Double getPreco( ) { return this.preco; }

    /**
     * Retorna a editora do livro.
     * @return A editora do livro.
     */
    public String getEditora( ) { return this.editora; }

    /**
     * Retorna a data de publicação do livro.
     * @return A data de publicação do livro.
     */
    public LocalDate getDataPublicacao( ) { return this.dataPublicacao; }
    
    /**
     * Retorna o ISBN do livro.
     * @return O ISBN do livro.
     */
    public Integer getIsbn( ) { return this.isbn; }

    /**
     * Define o ID do livro.
     * @param id O ID do livro.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Define o título do livro.
     * @param titulo O título do livro.
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Define o autor do livro.
     * @param autor O autor do livro.
     */
    public void setAutor(String autor) { this.autor = autor; }

    /**
     * Define o preço do livro.
     * @param preco O preço do livro.
     */
    public void setPreco(Double preco) { this.preco = preco; }

    /**
     * Define a editora do livro.
     * @param editora A editora do livro.
     */
    public void setEditora(String editora) { this.editora = editora; }

    /**
     * Define a data de publicação do livro.
     * @param dataPublicacao A data de publicação do livro.
     */
    public void setDataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; }

    /**
     * Define o ISBN do livro.
     * @param isbn O ISBN do livro.
     */
    public void setIsbn(Integer isbn) { this.isbn = isbn; }

    /**
     * Retorna uma representação simples do livro.
     *
     * @return string contendo todos os atributos do livro
     */
    @Override
    public String toString( )
    {
        return (
            "Livro{" +
                "id="               + id             +
                ", titulo='"        + titulo         + '\'' +
                ", autor='"         + autor          + '\'' +
                ", preco="          + preco          +
                ", editora='"       + editora        + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", isbn="           + isbn           +
            '}'
        );
    }

    /**
     * Retorna uma representação formatada do livro,
     * adequada para exibição em listagens.
     *
     * @return string formatada contendo os principais dados do livro
     */
    public String formatado( ) 
    {
    return String.format(
        "ID: %d, Título: %s, Autor: %s, Preço: R$ %.2f, Editora: %s, Data de Publicação: %s, ISBN: %s",
        id,
        titulo,
        autor,
        preco != null ? preco : 0.0,
        editora != null ? editora : "N/A",
        dataPublicacao != null ? dataPublicacao.toString() : "N/A",
        isbn != null ? isbn.toString() : "N/A"
    );
}

    /**
     * Compara livros pelo título, ignorando acentuação e diferenças de maiúsculas/minúsculas.
     *
     * @param outro outro livro para comparação
     * @return valor negativo se este livro for "menor", zero se for igual,
     *         ou positivo se for "maior"
     */
    @Override
    public int compareTo(Livro outro) {
        return IO.strNormalize(this.getTitulo()).compareTo(IO.strNormalize(outro.getTitulo()));
    }

    /**
     * Builder para a classe {@link Livro}.
     * Permite construir instâncias de forma mais legível,
     * definindo primeiro os campos obrigatórios e depois os opcionais.
     *
     * @see Livro
     */
    public static class Builder 
    {
        /** Atributos do Builder */
        private int id;
        private String titulo;
        private String autor;
        private Double preco;
        private String editora;
        private LocalDate dataPublicacao;
        private Integer isbn;

        /**
         * Constrói um Builder com os campos obrigatórios.
         *
         * @param titulo título do livro
         * @param autor  autor do livro
         * @param preco  preço do livro
         */
        public Builder(String titulo, String autor, Double preco) 
        {
            this.titulo = titulo;
            this.autor = autor;
            this.preco = preco;
        }

        /**
         * Define o ID do livro.
         * @param id O ID do livro.
         * @return O Builder atual.
         */
        public Builder id(int id) { this.id = id; return this; }

        /**
         * Define a editora do livro.
         * @param editora A editora do livro.
         * @return O Builder atual.
         */
        public Builder editora(String editora) { this.editora = editora; return this; }

        /**
         * Define a data de publicação do livro.
         * @param dataPublicacao A data de publicação do livro.
         * @return O Builder atual.
         */
        public Builder dataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; return this; }

        /**
         * Define o ISBN do livro.
         * @param isbn O ISBN do livro.
         * @return O Builder atual.
         */
        public Builder isbn(Integer isbn) { this.isbn = isbn; return this; }

        /**
         * Constrói o objeto Livro com os dados fornecidos.
         * @return O objeto Livro construído.
         */
        public Livro build( ) {
            return new Livro(this);
        }
    }
}
