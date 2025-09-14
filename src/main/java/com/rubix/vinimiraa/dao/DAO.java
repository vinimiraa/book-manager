package com.rubix.vinimiraa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe base para DAOs (Data Access Objects).
 * <p>
 * Responsável por gerenciar a conexão com o banco de dados SQLite.
 * Outras classes DAO (como {@link LivroDAO}) devem estender esta classe
 * para reutilizar os métodos de conexão.
 * </p>
 */
public class DAO 
{
	/**
     * Objeto de conexão JDBC com o banco de dados.
     * Mantém a sessão ativa até que {@link #disconnect()} seja chamado.
     */
	protected Connection conexao;

	/**
	 * Construtor padrão que inicializa a conexão como nula.
	 */
	public DAO( ) {
		conexao = null;
	}

	/**
     * Estabelece uma conexão com o banco de dados SQLite.
     * <p>
     * Por padrão, o banco utilizado é <b>livros.db</b> (criado no diretório raiz da aplicação
     * caso ainda não exista).
     * </p>
     *
     * @return {@code true} se a conexão foi estabelecida com sucesso,
     *         {@code false} caso contrário.
     */
	public boolean connect( )
	{
		boolean conectado = false;
		
		String driver   = "sqlite"; 
		String database = "livros.db";
		String url      = "jdbc:" + driver + ":" + database;
		
		try
		{
			this.conexao = DriverManager.getConnection(url);
			conectado = (this.conexao != null);
			// System.out.println("Conexão efetuado com SQLite!");
		} catch (SQLException sqle) {
			System.out.println("Erro ao conectar com o banco de dados: " + sqle.getMessage( ));
		}
		
		return conectado;
	}
	
	/**
     * Encerra a conexão com o banco de dados SQLite.
     * <p>
     * Após o encerramento, o atributo {@link #conexao} será fechado
     * e não deve mais ser utilizado sem uma nova chamada a {@link #connect()}.
     * </p>
     *
     * @return {@code true} se a conexão foi encerrada com sucesso,
     *         {@code false} caso contrário.
     */
	public boolean disconnect( )
	{
		boolean desconectado = false;
		
		try
		{
			this.conexao.close( );
			desconectado = true;
		} catch (SQLException sqle) {
			System.out.println("Erro ao desconectar do banco de dados: " + sqle.getMessage( ));
		}

		return desconectado;
	}
}