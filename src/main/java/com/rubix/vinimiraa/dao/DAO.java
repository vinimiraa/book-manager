package com.rubix.vinimiraa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
	protected Connection conexao;

	public DAO( ) {
		conexao = null;
	}

	public boolean conectar( )
	{
		boolean conectado = false;
		
		String driver   = "sqlite"; 
		String database = "livros.db";
		String url      = "jdbc:" + driver + ":" + database;
		
		try
		{
			this.conexao = DriverManager.getConnection(url);
			conectado = (this.conexao == null);
			System.out.println("Conexão efetuado com SQLite!");
		} catch (SQLException sqle) {
			System.out.println("Erro ao conectar com o banco de dados: " + sqle.getMessage( ));
		}
		
		return conectado;
	}
	
	public boolean desconectar( )
	{
		boolean desconectado = false;
		
		try
		{
			this.conexao.close();
			desconectado = true;
		} catch (SQLException sqle) {
			System.out.println("Erro ao desconectar do banco de dados: " + sqle.getMessage( ));
		}

		return desconectado;
	}
}