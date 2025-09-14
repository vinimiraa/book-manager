/**
 * Pacote utilitário que contém classes de suporte para operações gerais da aplicação.
 * <p>
 * Este pacote oferece funcionalidades auxiliares que não se enquadram diretamente na
 * camada de modelo ou persistência, como leitura e validação de dados do console,
 * normalização de strings e métodos utilitários para facilitar a interação com o usuário.
 * </p>
 *
 * <h2>Principais responsabilidades:</h2>
 * <ul>
 *   <li>Fornecer métodos para leitura de dados do console com validação de tipo e formato.</li>
 *   <li>Normalizar strings, removendo acentuação e padronizando letras minúsculas.</li>
 *   <li>Gerenciar interações com o usuário, como confirmação de ações e pausas na execução.</li>
 *   <li>Centralizar utilitários que podem ser usados por várias partes da aplicação.</li>
 * </ul>
 *
 * <h2>Classe principal:</h2>
 * <ul>
 *   <li>{@link com.rubix.vinimiraa.util.IO} – métodos estáticos para leitura, confirmação e formatação de dados do usuário.</li>
 * </ul>
 *
 * <p>
 * Exemplo de uso da classe {@link com.rubix.vinimiraa.util.IO}:
 * <pre>{@code
 * String nome = IO.lerString("Digite seu nome: ", 3, 50, false, true);
 * LocalDate dataNascimento = IO.lerData("Digite a data de nascimento (dd/MM/yyyy): ", false);
 * Double preco = IO.lerDouble("Digite o preço do livro: ", false);
 * boolean confirmar = IO.confirmarAcao("Deseja continuar? (S/N)");
 * }</pre>
 * </p>
 */
package com.rubix.vinimiraa.util;
