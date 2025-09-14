/**
 * Pacote que contém as classes responsáveis pelo acesso a dados 
 * (DAO - Data Access Object).
 * <p>
 * Este pacote centraliza a comunicação com o banco de dados 
 * <b>SQLite</b>, oferecendo uma camada de abstração para operações 
 * de persistência de entidades da aplicação.
 * </p>
 *
 * <h2>Principais responsabilidades:</h2>
 * <ul>
 *   <li>Gerenciar a conexão com o banco de dados ({@link com.rubix.vinimiraa.dao.DAO}).</li>
 *   <li>Executar operações de criação, leitura, atualização e exclusão (CRUD).</li>
 *   <li>Mapear registros do banco para objetos Java (e vice-versa).</li>
 *   <li>Fornecer DAOs específicos para cada entidade, como {@link com.rubix.vinimiraa.dao.LivroDAO}.</li>
 * </ul>
 *
 * <h2>Entidades suportadas:</h2>
 * <ul>
 *   <li>{@code Livro} – inclui título, autor, preço, editora, data de publicação e ISBN.</li>
 * </ul>
 *
 * <p>
 * Esse design segue o padrão DAO para isolar a lógica de persistência, 
 * garantindo melhor organização, reutilização de código e facilidade 
 * de manutenção.
 * </p>
 */
package com.rubix.vinimiraa.dao;
