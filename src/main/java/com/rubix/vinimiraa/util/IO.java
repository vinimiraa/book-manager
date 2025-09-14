package com.rubix.vinimiraa.util;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Classe utilitária para operações de entrada e saída (I/O) no console.
 * <p>
 * Esta classe fornece métodos estáticos para ler diferentes tipos de dados do console
 * com validação, como {@code String}, {@code Integer}, {@code Double} e {@link LocalDate}.
 * Também inclui métodos auxiliares para normalização de strings e confirmação de ações do usuário.
 * </p>
 *
 * <h2>Funcionalidades principais:</h2>
 * <ul>
 *   <li>Leitura de strings com validação de tamanho e caracteres.</li>
 *   <li>Leitura de datas no formato "dd/MM/yyyy".</li>
 *   <li>Leitura de números inteiros e decimais com validação.</li>
 *   <li>Confirmação de ações do usuário.</li>
 *   <li>Pausa da execução aguardando ENTER.</li>
 *   <li>Normalização de strings removendo acentos e convertendo para minúsculas.</li>
 * </ul>
 *
 * <h2>Exemplo de uso:</h2>
 * <pre>{@code
 * String nome = IO.lerString("Digite seu nome: ", 3, 50, false, true);
 * LocalDate data = IO.lerData("Digite a data de nascimento (dd/MM/yyyy): ", false);
 * Double preco = IO.lerDouble("Digite o preço: ", false);
 * boolean confirmado = IO.confirmarAcao("Deseja continuar? (S/N): ");
 * }</pre>
 */
public class IO 
{
    /**
     * Scanner para leitura de entrada do console.
     */
    private static Scanner console = new Scanner( System.in );

    /**
     * Códigos ANSI para cor vermelha no console.
     */
    private static final String RED   = "\u001B[31m";
    
    /**
     * Código ANSI para resetar a cor no console.
     */
    private static final String RESET = "\u001B[0m";

    /**
     * Lê uma string do console com validação de tamanho e caracteres.
     * 
     * @param mensagem   A mensagem a ser exibida ao usuário.
     * @param min        O tamanho mínimo da string.
     * @param max        O tamanho máximo da string.
     * @param opcional   Se true, permite entrada vazia.
     * @param apenasChar Se true, permite apenas letras e espaços.
     * @return A string lida do console.
     */
    public static String lerString(String mensagem, int min, int max, boolean opcional, boolean apenasChar)
    {
        String texto = "";
        boolean textoCorreto = false;

        do 
        {
            System.out.print(mensagem);
            texto = console.nextLine( );

            if(opcional && texto.isEmpty( ))
                textoCorreto = true;
            else if(texto.length( ) < min)
                System.out.println(RED + "Entrada inválida! Mínimo de " + min + " caracteres!" + RESET);
            else if(texto.length( ) > max)
                System.out.println(RED + "Entrada inválida! Máximo de " + max + " caracteres!" + RESET);
            else if(apenasChar && !texto.matches("[a-zA-ZÀ-ÿ\\s]+"))
                System.out.println(RED + "Entrada inválida! Digite apenas letras." + RESET);
            else
                textoCorreto = true;
        } while (textoCorreto == false);

        return texto;
    }

    /**
     * Lê uma data do console no formato "dd/MM/yyyy" com validação.
     * 
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @param opcional Se true, permite entrada vazia.
     * @return A data lida do console como LocalDate, ou null se vazia e opcional.
     */
    public static LocalDate lerData(String mensagem, boolean opcional) 
    {
        LocalDate data = null;
        boolean dataCorreta = false;

        do
        {
            System.out.print(mensagem);
            String entrada = console.nextLine( );
            
            if(opcional && entrada.isEmpty( )) return null;
            
            data = formatarData(entrada);
            if(data != null)
                dataCorreta = true;

        } while (dataCorreta == false);
        
        return data;
    } 

    /**
     * Formata uma string em {@link LocalDate} usando o padrão "dd/MM/yyyy".
     * 
     * @param dataEmString A string representando a data.
     * @return A data formatada como LocalDate, ou null se inválida.
     */
    private static LocalDate formatarData(String dataEmString) 
    {
        LocalDate data = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            data = LocalDate.parse(dataEmString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println(RED + "Formato de data inválido. Por favor, use o formato dd/MM/yyyy." + RESET);
        }

        return data;
    }

    /**
     * Lê um número decimal do console com validação.
     * 
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @param opcional Se true, permite entrada vazia.
     * @return O número decimal lido como Double, ou null se vazio e opcional.
     */
    public static Double lerDouble(String mensagem, boolean opcional)
    {
        Double valor = 0.0;
        boolean valorCorreto = false;

        do 
        {
            System.out.print(mensagem);
            String entrada = console.nextLine( );

            if(opcional && entrada.isEmpty( )) return null;

            try 
            {
                valor = Double.parseDouble(entrada);
                valorCorreto = true;
            } 
            catch (NumberFormatException nfe) 
            {
                System.out.println(RED + "Entrada inválida! Digite um número decimal." + RESET);
                valorCorreto = false;
            }
        } while (valorCorreto == false);

        return valor;
    }

    /**
     * Lê um número inteiro do console com validação.
     * 
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @param opcional Se true, permite entrada vazia.
     * @return O número inteiro lido como Integer, ou null se vazio e opcional.
     */
    public static Integer lerInteger(String mensagem, boolean opcional) 
    {
        Integer valor = 0;
        boolean valorCorreto = false;

        do 
        {
            System.out.print(mensagem);
            String entrada = console.nextLine( );

            if(opcional && entrada.isEmpty( )) return null;
            
            try 
            {
                valor = Integer.parseInt(entrada);
                valorCorreto = true;
            } 
            catch (NumberFormatException nfe) 
            {
                System.out.println(RED + "Entrada inválida! Digite um número inteiro." + RESET);
                valorCorreto = false;
            }
        } while (valorCorreto == false);

        return valor;
    }

    /**
     * Solicita confirmação do usuário para uma ação.
     * 
     * @param mensagem A mensagem a ser exibida ao usuário.
     * @return true se o usuário confirmar com S/s/Y/y, false caso contrário.
     */
    public static boolean confirmarAcao(String mensagem)
    {
        boolean confirmacao = false;

        System.out.println(mensagem);
        char resposta = console.nextLine( ).charAt(0);
        if(resposta == 'S' || resposta == 's' || resposta == 'Y' || resposta == 'y') confirmacao = true;

        return confirmacao;
    }

    /**
     * Pausa a execução até que o usuário pressione ENTER.
     */
    public static void continuar( ) 
    {
        System.out.print("\nPressione ENTER para continuar...");
        console.nextLine( );
    } // pressEnter ( )

    /**
     * Normaliza uma string removendo acentuação e convertendo para minúsculas.
     * 
     * @param str A string a ser normalizada.
     * @return A string normalizada.
     */
    public static String strNormalize(String str) 
    {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
    }
}
