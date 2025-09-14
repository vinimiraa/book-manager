package com.rubix.vinimiraa.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.rubix.vinimiraa.model.Livro;

/**
 * DAO específico para a entidade {@link Livro}.
 * <p>
 * Esta classe fornece métodos para operações CRUD
 * (Create, Read, Update, Delete) no banco de dados SQLite.
 * </p>
 * 
 * <p>
 * Depende da classe {@link DAO} para a conexão e utiliza
 * a entidade {@link Livro} como modelo de dados.
 * </p>
 */
public class LivroDAO extends DAO
{
    /**
     * Construtor que inicializa a conexão e cria a tabela 'livro' se não existir.
     */
    public LivroDAO( )
    {
        super( );
        connect( );
        createTable( );
    }

    /**
     * Cria a tabela 'livro' no banco de dados se ela não existir.
     * <p>
     * A tabela contém os seguintes campos:
     * <ul>
     *   <li><b>id</b> (chave primária, autoincremento)</li>
     *   <li><b>titulo</b> (texto, obrigatório)</li>
     *   <li><b>autor</b> (texto, obrigatório)</li>
     *   <li><b>preco</b> (real, obrigatório)</li>
     *   <li><b>editora</b> (texto, opcional)</li>
     *   <li><b>dataPublicacao</b> (data, opcional)</li>
     *   <li><b>isbn</b> (inteiro, opcional)</li>
     * </ul>
     * </p>
     */
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
        } catch (SQLException sqle) {
            System.err.println("Erro ao criar tabela 'livro': " + sqle.getMessage( ));
        }
    }

    /**
     * Insere um novo livro no banco de dados.
     * <p>
     * Após a inserção, o ID gerado automaticamente será atribuído
     * ao objeto {@link Livro} fornecido.
     * </p>
     *
     * @param livro O objeto {@link Livro} a ser inserido.
     * @return {@code true} se a inserção foi bem-sucedida,
     *         {@code false} caso contrário.
     */
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

            if(livro.getDataPublicacao( ) != null)
                pst.setDate(5, java.sql.Date.valueOf(livro.getDataPublicacao( )));
            else
                pst.setNull(5, java.sql.Types.DATE);

            if(livro.getIsbn( ) != null)
                pst.setInt(6, livro.getIsbn( ));
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

    /**
     * Recupera todos os livros do banco de dados.
     *
     * @return Uma lista de objetos {@link Livro}.
     *         Nunca {@code null}, mas pode estar vazia se não houver registros.
     */
    public List<Livro> getAll( )
    {
        List<Livro> livros = new LinkedList<>( );

        String query = "SELECT * FROM livro";
        try (
            PreparedStatement pst = conexao.prepareStatement(query);
            ResultSet rs = pst.executeQuery( )
        ) 
        {
            while (rs.next( ))
            {
                Livro livro = new Livro( );
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setPreco(rs.getDouble("preco"));
                livro.setEditora(rs.getString("editora"));

                java.sql.Date data = rs.getDate("dataPublicacao");
                if(data != null)
                    livro.setDataPublicacao(data.toLocalDate( ));
            
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

    /**
     * Recupera um livro do banco de dados pelo seu ID.
     *
     * @param id O ID do livro a ser recuperado.
     * @return O objeto {@link Livro} correspondente ao ID, 
     *         ou {@code null} se não encontrado.
     */
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

    /**
     * Recupera um livro do banco de dados pelo seu ISBN.
     *
     * @param isbn O ISBN do livro a ser recuperado.
     * @return O objeto {@link Livro} correspondente ao ISBN,
     *         ou {@code null} se não encontrado.
     */
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

    /**
     * Atualiza os dados de um livro no banco de dados.
     *
     * @param livro O objeto {@link Livro} com os dados atualizados.
     * @return {@code true} se a atualização foi bem-sucedida,
     *         {@code false} se não houve alterações (ex: ID inexistente).
     */
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

    /**
     * Deleta um livro do banco de dados pelo seu ID.
     *
     * @param id O ID do livro a ser deletado.
     * @return {@code true} se a deleção foi bem-sucedida,
     *         {@code false} caso contrário.
     */
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
