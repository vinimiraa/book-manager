package com.rubix.vinimiraa.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.rubix.vinimiraa.model.Livro;

public class LivroDAO extends DAO
{
    public LivroDAO( )
    {
        super( );
        conectar( );
        criarTabela();
    }

    private void criarTabela( )
    {
        String query = """
                CREATE TABLE IF NOT EXISTS livro (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo VARCHAR(255) NOT NULL,
                    autor VARCHAR(255) NOT NULL,
                    preco REAL NOT NULL,
                    editora VARCHAR(255),
                    dataPublicacao DATE,
                    isbn INTEGER
                )
                """;
        try (PreparedStatement pst = conexao.prepareStatement(query)) {
            pst.executeUpdate( );
        } catch (Exception e) {
            System.out.println("Erro ao criar tabela 'livro': " + e.getMessage( ));
        }
    }

    public boolean inserir(Livro livro)
    {
        boolean inserido = false;

        String query = "INSERT INTO livro (titulo, autor, preco, editora, dataPublicacao, isbn) values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(query))
        {   
            pst.setString(1, livro.getTitulo( ));
            pst.setString(2, livro.getAutor( ));
            pst.setDouble(3, livro.getPreco( ));
            pst.setString(4, livro.getEditora( ));

            if(livro.getDataPublicacao() != null)
                pst.setDate(5, java.sql.Date.valueOf(livro.getDataPublicacao()));
            else
                pst.setNull(5, java.sql.Types.DATE);

            if(livro.getIsbn() != null)
                pst.setInt(6, livro.getIsbn());
            else
                pst.setNull(6, java.sql.Types.INTEGER);
                
            pst.executeUpdate( );
            inserido = true;
        } catch (SQLException sqle) {
            System.out.println("Erro ao inserir no banco de dados: " + sqle.getMessage( ));
        }

        return inserido;
    }
}
