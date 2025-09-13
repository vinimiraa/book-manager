package com.rubix.vinimiraa.model;

import java.time.LocalDate;

public class Livro 
{
    private int id;                    // banco de dados
    private String titulo;             // obrigatorio
    private String autor;              // obrigatorio
    private Double preco;              // obrigatorio
    private String editora;            // opcional
    private LocalDate dataPublicacao;  // opcional
    private Integer isbn;              // opcional

    public Livro( ) {
        this(-1, "", "", 0.0, null, null, null);
    }

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

    // Getters
    public int getId( ) { return this.id; }
    public String getTitulo( ) { return this.titulo; }
    public String getAutor( ) { return this.autor; }
    public Double getPreco( ) { return this.preco; }
    public String getEditora( ) { return this.editora; }
    public LocalDate getDataPublicacao( ) { return this.dataPublicacao; }
    public Integer getIsbn( ) { return this.isbn; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setPreco(Double preco) { this.preco = preco; }
    public void setEditora(String editora) { this.editora = editora; }
    public void setDataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; }
    public void setIsbn(Integer isbn) { this.isbn = isbn; }

    @Override
    public String toString( )
    {
        return (
            "\nID................:" + this.id             +
            "\nTitulo............:" + this.titulo         +
            "\nAutor.............:" + this.autor          +
            "\nPreco.............:" + this.preco          +
            "\nEditora...........:" + this.editora        +
            "\nData da Publicacao:" + this.dataPublicacao +
            "\nISBN..............:" + this.isbn           + "\n"
        );
    }

    // Builder
    public static class Builder 
    {
        private int id;
        private String titulo;
        private String autor;
        private Double preco;
        private String editora;
        private LocalDate dataPublicacao;
        private Integer isbn;

        public Builder(String titulo, String autor, Double preco) 
        {
            this.titulo = titulo;
            this.autor = autor;
            this.preco = preco;
        }

        public Builder id(int id) { this.id = id; return this; }
        public Builder editora(String editora) { this.editora = editora; return this; }
        public Builder dataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; return this; }
        public Builder isbn(Integer isbn) { this.isbn = isbn; return this; }

        public Livro build( ) {
            return new Livro(this);
        }
    }
}
