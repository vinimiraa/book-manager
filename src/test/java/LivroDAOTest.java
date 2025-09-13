import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.rubix.vinimiraa.model.Livro;
import com.rubix.vinimiraa.dao.LivroDAO;

class LivroDAOTest 
{
    private LivroDAO livroDAO;

    @Test
    void inserirLivroBD( )
    {
        Livro livro = new Livro.Builder("O Alquimista", "Paulo Coelho", 0.0).build( );

        livroDAO = new LivroDAO( );

        boolean inserido = livroDAO.inserir(livro);
        assertTrue(inserido, "O livro deve ser inserido com sucesso");
    }

}


