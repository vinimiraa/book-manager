// import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import com.rubix.vinimiraa.model.Livro;

/**
 * Classe de teste para a classe Livro.
 * Utiliza JUnit 5 para testar os diferentes construtores e métodos da classe Livro.
 */
class LivroTest 
{
    /** 
     * Testa a criação de um livro usando o padrão Builder com todos os atributos.
     */
    @Disabled
    void criarLivroBuilder( ) 
    {
        Livro livro = new Livro.Builder("Java Básico", "Autor X", 59.90)
                .editora("TechBooks")
                .dataPublicacao(LocalDate.of(2024, 1, 10))
                .isbn(12345)
                .build();

        assertEquals("Java Básico", livro.getTitulo());
        assertEquals("Autor X", livro.getAutor());
        assertEquals(59.90f, livro.getPreco(), 0.001);
        assertEquals("TechBooks", livro.getEditora());
        assertEquals(LocalDate.of(2024, 1, 10), livro.getDataPublicacao());
        assertEquals(12345, livro.getIsbn());
    }

    /** 
     * Testa a criação de um livro usando o padrão Builder com apenas os atributos obrigatórios.
     */
    @Disabled
    void criarLivroBuilderObrigatorios( ) 
    {
        Livro livro = new Livro.Builder("Spring Boot", "Autor Y", 79.90)
                .build();

        assertEquals("Spring Boot", livro.getTitulo());
        assertEquals("Autor Y", livro.getAutor());
        assertEquals(79.90f, livro.getPreco(), 0.001);
        assertNull(livro.getEditora());
        assertNull(livro.getDataPublicacao());
        assertNull(livro.getIsbn());
    }

    /** 
     * Testa a criação de um livro usando o construtor completo.
     */
    @Disabled
    void criarLivroConstrutorCompleto( ) 
    {
        Livro livro = new Livro(1, "Spring Boot", "Autor Y", 79.90, "Editora Z",
                                LocalDate.of(2024, 5, 10), 987654);

        assertEquals(1, livro.getId());
        assertEquals("Spring Boot", livro.getTitulo());
        assertEquals("Autor Y", livro.getAutor());
        assertEquals(79.90, livro.getPreco(), 0.001);
        assertEquals("Editora Z", livro.getEditora());
        assertEquals(LocalDate.of(2024, 5, 10), livro.getDataPublicacao());
        assertEquals(987654, livro.getIsbn());
    }

    /** 
     * Testa a criação de um livro usando o construtor vazio e setters.
     */
    @Disabled
    void criarLivroConstrutorVazio( )
    {
        Livro livro = new Livro();

        assertEquals(-1, livro.getId());
        assertEquals("", livro.getTitulo());
        assertEquals("", livro.getAutor());
        assertEquals(0.0, livro.getPreco(), 0.001);
        assertNull(livro.getEditora());
        assertNull(livro.getDataPublicacao());
        assertNull(livro.getIsbn());
    }

    /** 
     * Testa o método toString do livro.
     */
    @Disabled
    void testarToString( ) 
    {
        Livro livro = new Livro.Builder("Java Básico", "Autor X", 49.90).build();
        String str = livro.toString();

        assertNotNull(str);
        assertTrue(str.contains("Java Básico"));
        assertTrue(str.contains("Autor X"));
    }
}
