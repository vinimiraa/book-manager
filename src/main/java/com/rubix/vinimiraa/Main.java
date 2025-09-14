package com.rubix.vinimiraa;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.rubix.vinimiraa.dao.LivroDAO;
import com.rubix.vinimiraa.model.Livro;
import com.rubix.vinimiraa.util.IO;

/**
 * Classe principal do aplicativo Rubix - Gerenciador de Livros.
 * <p>
 * Esta classe fornece um menu interativo para o usuário realizar operações CRUD
 * (Create, Read, Update, Delete) em livros armazenados em um banco de dados SQLite.
 * O menu é apresentado no console, permitindo cadastrar, listar, buscar, atualizar e
 * excluir livros.
 * </p>
 *
 * <h2>Funcionalidades:</h2>
 * <ul>
 *   <li>Cadastrar novo livro com título, autor, preço, editora, data de publicação e ISBN.</li>
 *   <li>Listar todos os livros cadastrados, ordenados por título.</li>
 *   <li>Buscar livro pelo ID e exibir detalhes formatados.</li>
 *   <li>Atualizar campos específicos de um livro existente.</li>
 *   <li>Excluir livro pelo ID, após confirmação do usuário.</li>
 * </ul>
 *
 * <h2>Exemplo de uso:</h2>
 * <pre>{@code
 * public static void main(String[] args) {
 *     Main.main(args); // Inicia a aplicação e exibe o menu interativo
 * }
 * }</pre>
 *
 * <p>Os métodos utilizam a classe utilitária {@link IO} para entrada de dados e validação.</p>
 */
public class Main 
{
    /**
     * Scanner para leitura de entrada do console.
     */
    private static Scanner console = new Scanner(System.in);

    /**
     * Códigos ANSI para cor verde no console.
     */
    protected static final String GREEN = "\u001B[32m";
    /**
     * Códigos ANSI para cor vermelha no console.
     */
    protected static final String RED   = "\u001B[31m";
    
    /**
     * Código ANSI para resetar a cor no console.
     */
    protected static final String RESET = "\u001B[0m";

    /**
     * Instância do DAO para operações de banco de dados relacionadas a livros.
     */
    private static LivroDAO livroDAO = new LivroDAO( );

    /**
     * Método principal que inicia o aplicativo e controla o fluxo do menu.
     *
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) 
    {
        try
        {
            int opcao = 0;
            do
            {
                opcoesMenu( );
                opcao = lerOpcao( );
                executarOpcao(opcao);
            } while (opcao != 0);
        } catch (Exception e) {
            e.printStackTrace( );
        } finally {
            console.close();
        }
    }

    /**
     * Exibe o menu principal de opções no console.
     */
    private static void opcoesMenu( )
    {
        System.out.println("------------------------------");
        System.out.println("Rubix - Gerenciador de Livros" );
        System.out.println("Vinicius Miranda de Araujo"    );
        System.out.println("------------------------------");
        System.out.println("> Inicio"                      );
        System.out.println("1 - Cadastrar Livro"           );
        System.out.println("2 - Listar Livros"             );
        System.out.println("3 - Buscar Livro"              );
        System.out.println("4 - Atualizar Livro"           );
        System.out.println("5 - Excluir Livro"             );
        System.out.println("0 - Sair"                      );
        System.out.print  ("Digite o número da opção: "    );
    }

    /**
     * Lê a opção escolhida pelo usuário no menu.
     *
     * @return Um inteiro representando a opção escolhida.
     */
    private static int lerOpcao( ) 
    {
        int opcao = 0;
        try
        {
            opcao = Integer.valueOf(console.nextLine( ));
        } catch(NumberFormatException nfe) {
            opcao = -1;
        }
        return opcao;
    }

    /**
     * Executa a ação correspondente à opção escolhida pelo usuário.
     *
     * @param opcao A opção escolhida no menu.
     */
    private static void executarOpcao(int opcao)
    {
        switch(opcao) 
        {
            case 0:
                System.out.println("\nSaindo...");
                break;
            case 1:
                cadastrarLivro( );
                break;
            case 2:
                listarLivros( );
                break;
            case 3:
                buscarLivro( );
                break;
            case 4:
                atualizarLivro ();
                break;
            case 5:
                excluirLivro ();
                break;
            default:
                System.err.println(RED + "Opção inválida!\n" + RESET);
                break;
        }
    }

    /**
     * Lê os dados de um livro a partir do console.
     *
     * @return Um objeto {@link Livro} com os dados fornecidos pelo usuário.
     */
    private static Livro lerLivro( )
    {
        String titulo = IO.lerString("Digite o título (obrigatório): ", 2, 255, false, false);
        String autor = IO.lerString("Digite o autor (obrigatório): ", 2, 255, false, false);
        double preco = IO.lerDouble("Digite o preço (obrigatório): ", false);
        String editora = IO.lerString("Digite a editora (opcional): ", 2, 255, true, false);
        LocalDate dataPublicacao = IO.lerData("Digite a data de publicação (opcional): ", true);
        Integer isbn = IO.lerInteger("Digite o ISBN (opcional):", true);

        return new Livro.Builder(titulo, autor, preco)
                        .editora(editora)
                        .dataPublicacao(dataPublicacao)
                        .isbn(isbn)
                        .build( );
    }

    /**
     * Cadastra um novo livro no banco de dados.
     * Solicita confirmação do usuário antes de inserir.
     */
    public static void cadastrarLivro( )
    {
        System.out.println("\n> Cadastrar um Livro");
        try
        {
            Livro livro = lerLivro( );
            if(livro != null)
            {
                if(IO.confirmarAcao("\nConfirma cadastro do livro? (S/N)"))
                {
                    if(livroDAO.insert(livro))
                        System.out.println(GREEN + "Livro cadastrado com sucesso!\n" + RESET);
                } else {
                    System.out.println(RED + "Operação cancelada!\n" + RESET);
                }
            }
        } catch(Exception e) {
            System.err.println(RED + "Erro ao cadastrar um livro: " + e.getMessage() + RESET);
        }
    }
    
    /**
     * Lista todos os livros cadastrados no banco de dados,
     * exibindo suas informações de forma formatada e ordenada.
     */
    public static void listarLivros( )
    {
        System.out.println("\n> Listar os Livros");
        try 
        {
            List<Livro> livros = livroDAO.getAll( );

            if(livros != null)
            {
                Collections.sort(livros);
    
                for (Livro livro : livros) {
                    System.out.println(livro.formatado( ));
                }
                System.out.println( );
            } else {
                System.err.println(RED + "Náo há livros cadastrados na base de dados!\n" + RESET);
            }
            
        } catch (Exception e) {
            System.err.println(RED + "Erro ao listar os livros: " + e.getMessage() + RESET);
        }
    }
    
    /**
     * Busca um livro pelo ID fornecido pelo usuário e exibe seus detalhes.
     */
    public static void buscarLivro( )
    {
        System.out.println("\n> Buscar um Livro");
        try
        {
            int id = IO.lerInteger("Digite o ID do livro: ", false);
            Livro livro = livroDAO.getById(id);

            if(livro != null) 
            {
                System.out.println(livro.formatado( ));
                System.out.println( );
            }
            else {
                System.out.println("Livro não encontrado!\n");  
            }
        } catch (Exception e) {
            System.err.println(RED + "Erro ao buscar um livro: " + e.getMessage() + RESET);
        }
    }
    
    /**
     * Atualiza os dados de um livro existente.
     * Permite alterar campos específicos antes de confirmar a atualização.
     */
    public static void atualizarLivro( )
    {
        System.out.println("\n> Atualizar um Livro");
        try
        {
            int id = IO.lerInteger("Digite o ID do livro: ", false);
            Livro livro = livroDAO.getById(id);

            if(livro != null) 
            {
                System.out.println(livro.formatado( ));
                System.out.println( );

                int opcao = 0;
                do
                {
                    menuCamposAtualizacao( );
                    opcao = lerOpcao( );
                    atualizarCampo(livro, opcao);
                } while (opcao!= 0);
                
                if(IO.confirmarAcao("\nConfirma atualização do livro? (S/N)")) 
                {
                    if(livroDAO.update(livro))
                        System.out.println(GREEN + "Livro atualizado com sucesso!\n" + RESET);
                } else {
                    System.out.println(RED + "Operação cancelada!\n" + RESET);
                }
            }
            else {
                System.out.println("Livro não encontrado!\n");    
            }
        } catch (Exception e) {
            System.err.println(RED + "Erro ao atualizar um livro: " + e.getMessage() + RESET);
        }
    }

    /**
     * Exibe o menu de campos que podem ser atualizados em um livro.
     */
    private static void menuCamposAtualizacao( )
    {
        System.out.println("Qual campo deseja atualizar?");
        System.out.println("1 - Título"                  );
        System.out.println("2 - Autor"                   );
        System.out.println("3 - Preço"                   );
        System.out.println("4 - Editora"                 );
        System.out.println("5 - Data de Publicação"      );
        System.out.println("6 - ISBN"                    );
        System.out.println("0 - Finalizar"               );
        System.out.print  ("Digite o número da opção: "  );
    }

    /**
     * Atualiza um campo específico de um livro de acordo com a opção escolhida.
     *
     * @param livro O objeto {@link Livro} a ser atualizado.
     * @param opcao Número que indica qual campo atualizar.
     */
    private static void atualizarCampo(Livro livro, int opcao)
    {
        switch (opcao) 
        {
            case 0:
                System.out.println("\nLivro atualizado:");
                System.out.println(livro.formatado( ));
                System.out.println( );
                break;
            case 1:
                String novoTitulo = IO.lerString("\nDigite o novo título: ", 2, 255, false, false);
                livro.setTitulo(novoTitulo);
                break;
            case 2:
                String novoAutor = IO.lerString("\nDigite o novo autor: ", 2, 255, false, false);
                livro.setAutor(novoAutor);
                break;
            case 3:
                double novoPreco = IO.lerDouble("\nDigite o novo preço: ", false);
                livro.setPreco(novoPreco);
                break;
            case 4:
                String novaEditora = IO.lerString("\nDigite a nova editora (opcional): ", 2, 255, true, false);
                livro.setEditora(novaEditora);
                break;
            case 5:
                LocalDate novaDataPublicacao = IO.lerData("\nDigite a nova data de publicação (opcional): ", true);
                livro.setDataPublicacao(novaDataPublicacao);
                break;
            case 6:
                Integer novoIsbn = IO.lerInteger("\nDigite o novo ISBN (opcional):", true);
                livro.setIsbn(novoIsbn);
                break;
            default:
                System.err.println(RED + "Opção inválida!\n" + RESET);
                break;
        }
    }

    /**
     * Exclui um livro do banco de dados com base no ID fornecido pelo usuário.
     * Solicita confirmação antes de remover.
     */
    public static void excluirLivro( )
    {
        System.out.println("\n> Excluir um Livro");
        try
        {
            int id = IO.lerInteger("Digite o ID do livro: ", false);
            Livro livro = livroDAO.getById(id);

            if(livro != null)
            {
                System.out.println(livro.formatado( ));
                
                if(IO.confirmarAcao("\nConfirma exclusão do livro? (S/N)")) {
                    if(livroDAO.delete(id))
                        System.out.println(GREEN + "Livro excluido com sucesso!\n" + RESET);
                } else {
                    System.out.println(RED + "Operação cancelada!\n" + RESET);
                }
            }
            else {
                System.out.println("Livro não encontrado!\n");  
            }
        } catch (Exception e) {
            System.err.println(RED + "Erro ao excluir um livro: " + e.getMessage() + RESET);
        }
    }
}