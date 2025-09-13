package com.rubix.vinimiraa.util;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class IO 
{
    private static Scanner console = new Scanner( System.in );
    private static final String RED   = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

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

    public static boolean confirmarAcao(String mensagem)
    {
        boolean confirmacao = false;

        System.out.println(mensagem);
        char resposta = console.nextLine( ).charAt(0);
        if(resposta == 'S' || resposta == 's' || resposta == 'Y' || resposta == 'y') confirmacao = true;

        return confirmacao;
    }

    public static void continuar( ) 
    {
        System.out.print("\nPressione ENTER para continuar...");
        console.nextLine( );
    } // pressEnter ( )

    public static String strNormalize(String str) 
    {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
    }
}
