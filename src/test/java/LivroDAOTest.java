import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import com.rubix.vinimiraa.dao.LivroDAO;
import com.rubix.vinimiraa.model.Livro;

/**
 * Classe de teste para a classe LivroDAO.
 * Utiliza JUnit 5 para testar os métodos CRUD da classe LivroDAO.
 */
class LivroDAOTest {

    private static LivroDAO livroDAO;

    /** 
     * Inicializa o LivroDAO antes de todos os testes.
     */
    @BeforeAll
    static void setUp() {
        livroDAO = new LivroDAO();
    }

    /** 
     * Testa a inserção de um novo livro no banco de dados.
     */
    @Test
    void testInsert() {
        Livro livro = new Livro.Builder("O Alquimista", "Paulo Coelho", 39.90)
                .editora("Rocco")
                .dataPublicacao(LocalDate.of(1988, 4, 1))
                .isbn(123456)
                .build();

        boolean inserido = livroDAO.insert(livro);
        assertTrue(inserido, "O livro deveria ser inserido com sucesso");
    }

    /** 
     * Testa a recuperação de todos os livros do banco de dados.
     */
    @Test
    void testGetAll() {
        Livro livro = new Livro.Builder("1984", "George Orwell", 29.90).build();
        livroDAO.insert(livro);

        List<Livro> livros = livroDAO.getAll();
        assertNotNull(livros, "A lista de livros não deveria ser nula");
        assertFalse(livros.isEmpty(), "A lista de livros não deveria estar vazia");
    }

    /** 
     * Testa a recuperação de um livro pelo seu ID.
     */
    @Test
    void testGetById() {
        Livro livro = new Livro.Builder("Dom Casmurro", "Machado de Assis", 19.90).build();
        livroDAO.insert(livro);

        Livro encontrado = livroDAO.getAll().get(0);
        Livro buscado = livroDAO.getById(encontrado.getId());

        assertNotNull(buscado, "O livro deveria ser encontrado pelo ID");
        assertEquals("Dom Casmurro", buscado.getTitulo());
    }

    /** 
     * Testa a recuperação de um livro pelo seu ISBN.
     */
    @Test
    void testGetByIsbn() {
        Livro livro = new Livro.Builder("O Hobbit", "J.R.R. Tolkien", 49.90)
                .isbn(99999)
                .build();
        livroDAO.insert(livro);

        Livro buscado = livroDAO.getByIsbn(99999);
        assertNotNull(buscado, "O livro deveria ser encontrado pelo ISBN");
        assertEquals("O Hobbit", buscado.getTitulo());
    }

    /** 
     * Testa a atualização dos dados de um livro existente.
     */
    @Test
    void testUpdate() {
        Livro livro = new Livro.Builder("Livro Teste", "Autor Teste", 10.0).isbn(111).build();
        livroDAO.insert(livro);

        Livro existente = livroDAO.getAll().get(0);
        existente.setTitulo("Livro Atualizado");
        boolean atualizado = livroDAO.update(existente);

        assertTrue(atualizado, "O livro deveria ser atualizado com sucesso");
        Livro atualizadoLivro = livroDAO.getById(existente.getId());
        assertEquals("Livro Atualizado", atualizadoLivro.getTitulo());
    }

    /** 
     * Testa a exclusão de um livro pelo seu ID.
     */
    @Test
    void testDelete() {
        Livro livro = new Livro.Builder("Livro Temporário", "Autor X", 5.0).build();
        livroDAO.insert(livro);

        Livro existente = livroDAO.getAll().get(0);
        boolean deletado = livroDAO.delete(existente.getId());

        assertTrue(deletado, "O livro deveria ser deletado com sucesso");
        assertNull(livroDAO.getById(existente.getId()), "O livro não deveria mais existir");
    }
}
