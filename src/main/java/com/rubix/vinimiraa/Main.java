package com.rubix.vinimiraa;

import java.time.LocalDate;
import java.util.Scanner;
import com.rubix.vinimiraa.model.Livro;
import com.rubix.vinimiraa.util.IO;

public class Main 
{
    private static Scanner console = new Scanner(System.in);
    protected static final String GREEN = "\u001B[32m";
    protected static final String RED   = "\u001B[31m";
    protected static final String RESET = "\u001B[0m";

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
            } while(opcao != 0);
        } catch(Exception e) {
            e.printStackTrace( );
        } finally {
            console.close();
        }
    }

    private static void opcoesMenu( )
    {
        System.out.println("Rubix"                         );
        System.out.println("Gerenciador de Livros"         );
        System.out.println("Vinicius Miranda de Araujo"    );
        System.out.println("------------------------------");
        System.out.println("> Inicio"                      );
        System.out.println("1 - Cadastrar Livro"           );
        System.out.println("2 - Listar Livros"             );
        System.out.println("3 - Buscar Livro"              );
        System.out.println("4 - Atualizar Livro"           );
        System.out.println("4 - Excluir Livro"             );
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
                System.out.println( RED + "Opção inválida!" + RESET);
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
                        .build();
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
                    System.out.println("Inserindo no BD");
                    System.out.println(livro);
                } else {
                    System.out.println(RED + "Operação cancelada!" + RESET);
                }
            }
        } catch(Exception e) {
            System.out.println(RED + "Erro ao cadastrar um livro: " + e.getMessage() + RESET);
        }
    }

    public static void listarLivros( )
    {
        System.out.println("\n> Listar os Livros");
        
    }
    
    public static void buscarLivro( )
    {
        System.out.println("\n> Buscar um Livro");
        
    }
    
    public static void atualizarLivro( )
    {
        System.out.println("\n> Atualizar um Livro");

    }

    public static void excluirLivro( )
    {
        System.out.println("\n> Excluir um Livro");

    }
}