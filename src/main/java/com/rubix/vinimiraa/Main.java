package com.rubix.vinimiraa;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.rubix.vinimiraa.dao.LivroDAO;
import com.rubix.vinimiraa.model.Livro;
import com.rubix.vinimiraa.util.IO;

public class Main 
{
    private static Scanner console = new Scanner(System.in);
    protected static final String GREEN = "\u001B[32m";
    protected static final String RED   = "\u001B[31m";
    protected static final String RESET = "\u001B[0m";
    private static LivroDAO livroDAO = new LivroDAO( );

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

    private static Livro lerLivro( )
    {
        String titulo = IO.lerString("Digite o título: ", 2, 255, false, false);
        String autor = IO.lerString("Digite o autor: ", 2, 255, false, false);
        double preco = IO.lerDouble("Digite o preço: ", false);
        String editora = IO.lerString("Digite a editora (opcional): ", 2, 255, true, false);
        LocalDate dataPublicacao = IO.lerData("Digite a data de publicação (opcional): ", true);
        Integer isbn = IO.lerInteger("Digite o ISBN (opcional):", true);

        return new Livro.Builder(titulo, autor, preco)
                        .editora(editora)
                        .dataPublicacao(dataPublicacao)
                        .isbn(isbn)
                        .build( );
    }

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
                System.out.println();

                int opcao = 0;
                do
                {
                    menuCamposAtualizacao( );
                    opcao = lerOpcao( );
                    atualizarCampo(livro, opcao);
                } while (opcao!= 0);
                
                if(IO.confirmarAcao("\nConfirma atualização do livro? (S/N)")) {
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

    private static void atualizarCampo(Livro livro, int opcao)
    {
        switch (opcao) 
        {
            case 0:
                System.out.println("Livro atualizado:");
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