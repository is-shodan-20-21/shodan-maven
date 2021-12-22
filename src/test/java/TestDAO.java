
import static org.junit.Assert.assertNotNull;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Database.DBConnectionPool;
import Model.Game;
import Service.GameService;

public class TestDAO {

    private Connection db;
    private GameService gameDAO;
    private Game giocoEsistente;
    private Game giocoNonEsistente;

    public TestDAO() {}

    @Before
    public void setUp() {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        gameDAO = new GameService(db);
        giocoEsistente = new Game(-1, 50, "Serious Sam", "Miglior gioco di sempre", "serious_sam", new Date(10000L), "landscape");
        giocoNonEsistente = new Game(-2,55,"Gioco non esistente","Peggior gioco di sempre","non_esistente",new Date(10000L),"landscape");
        gameDAO.addGame(giocoEsistente.getName(), giocoEsistente.getImage(), giocoEsistente.getPrice(), giocoEsistente.getRelease(), giocoEsistente.getDescription(), giocoEsistente.getLandscape());

        int test_id = gameDAO.getGameByName("Serious Sam").getId();
        giocoEsistente.setId(test_id);
    }

    @Test
    public void TestGetGameEsistente() {
        assertNotNull(gameDAO.getGame(giocoEsistente.getId()));
    }

    /* aggiungere altri metodi qui */

    @After
    public void tearDown() {
        gameDAO.deleteGame(giocoEsistente.getId());
    }
}