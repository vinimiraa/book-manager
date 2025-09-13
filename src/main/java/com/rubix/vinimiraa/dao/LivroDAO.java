package com.rubix.vinimiraa.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.rubix.vinimiraa.model.Livro;

public class LivroDAO extends DAO
{
    public LivroDAO( )
    {
        super( );
        connect( );
        createTable();
    }

    private void createTable( )
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
            System.err.println("Erro ao criar tabela 'livro': " + e.getMessage( ));
        }
    }

    public boolean insert(Livro livro)
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

            // pegar o id gerado e setar
            try (ResultSet rs = pst.getGeneratedKeys( )) 
            {
                if(rs.next( )) {
                    livro.setId(rs.getInt(1)); 
                }
            }

            pst.executeUpdate( );
            inserido = true;
        } catch (SQLException sqle) {
            System.err.println("Erro ao inserir no banco de dados: " + sqle.getMessage( ));
        }

        return inserido;
    }

    public List<Livro> getAll( )
    {
        List<Livro> livros = new LinkedList<>( );

        String query = "SELECT * FROM livro";
        try (
            PreparedStatement pst = conexao.prepareStatement(query);
            ResultSet rs = pst.executeQuery( )
        ) 
        {
            while (rs.next())
            {
                Livro livro = new Livro( );
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setPreco(rs.getDouble("preco"));
                livro.setEditora(rs.getString("editora"));

                java.sql.Date data = rs.getDate("dataPublicacao");
                if(data != null)
                    livro.setDataPublicacao(data.toLocalDate());
            
                int isbn = rs.getInt("isbn");
                if(rs.wasNull( ) == false)
                    livro.setIsbn(isbn);

                livros.add(livro);
            }
        } catch (SQLException sqle) {
            System.err.println("Erro ao listar livros: " + sqle.getMessage( ));
        }

        return livros;
    }

    public Livro getById(int id)
    {
        Livro livro = null;

        String query = "SELECT * FROM livro WHERE id = ?";
        try (PreparedStatement pst = conexao.prepareStatement(query)) 
        {
            pst.setInt(1, id);
            try(ResultSet rs = pst.executeQuery( ))
            {
                if(rs.next( ))
                {
                    livro = new Livro( );
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setPreco(rs.getDouble("preco"));
                    livro.setEditora(rs.getString("editora"));

                    java.sql.Date data = rs.getDate("dataPublicacao");
                    if(data != null)
                        livro.setDataPublicacao(data.toLocalDate());

                    int isbn = rs.getInt("isbn");
                    if(rs.wasNull( ) == false)
                        livro.setIsbn(isbn);
                }
            }
        } catch (SQLException sqle) {
            System.err.println("Erro ao buscar livro por ID: " + sqle.getMessage( ));
        }

        return livro;
    }

    public Livro getByIsbn(int isbn)
    {
        Livro livro = null;

        String query = "SELECT * FROM livro WHERE isbn = ?";
        try (PreparedStatement pst = conexao.prepareStatement(query)) 
        {
            pst.setInt(1, isbn);
            try(ResultSet rs = pst.executeQuery( ))
            {
                if(rs.next( ))
                {
                    livro = new Livro( );
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutor(rs.getString("autor"));
                    livro.setPreco(rs.getDouble("preco"));
                    livro.setEditora(rs.getString("editora"));
    
                    java.sql.Date data = rs.getDate("dataPublicacao");
                    if(data != null)
                        livro.setDataPublicacao(data.toLocalDate());
    
                    livro.setIsbn(rs.getInt("isbn"));
                }
            }
        } catch (SQLException sqle) {
            System.err.println("Erro ao buscar livro por ID: " + sqle.getMessage( ));
        }

        return livro;
    }

    public boolean update(Livro livro)
    {
        boolean atualizado = false;

        String query = "UPDATE livro SET titulo = ?, autor = ?, preco = ?, editora = ?, dataPublicacao = ?, isbn = ? WHERE id = ?";
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

            pst.setInt(7, livro.getId( ));
            pst.executeUpdate( );
            atualizado = true;
        } catch (SQLException sqle) {
            System.err.println("Erro ao atualizar livro: " + sqle.getMessage( ));
        }

        return atualizado;
    }

    public boolean delete(int id)
    {
        boolean deletado = false;

        String query = "DELETE FROM livro WHERE id = ?";
        try (PreparedStatement pst = conexao.prepareStatement(query))
        {
            pst.setInt(1, id);
            pst.executeUpdate( );
            deletado = true;
        } catch (SQLException sqle) {
            System.err.println("Erro ao deletar livro: " + sqle.getMessage( ));
        }

        return deletado;
    }
}
