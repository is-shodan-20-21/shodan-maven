package dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.DBConnectionPool;
import Model.Game;
import Service.GameService;

public class TestGameDAO {

    private Connection db;
    private GameService gameDAO;
    private Game giocoEsistente;
    private Game giocoNonEsistente;

    public TestGameDAO() {}

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
    public void TestGetGameExisting() {
        assertNotNull(gameDAO.getGame(giocoEsistente.getId()));
    }

    @Test
    public void TestGetGameNotExisting() {
        assertNull(gameDAO.getGame(giocoNonEsistente.getId()));
    }

    @Test
    public void TestGetGameByNameExisting () {
        assertNotNull(gameDAO.getGameByName(giocoEsistente.getName()));
    }

    @Test
    public void TestGetGameByNameNotExisting () {
        assertNull(gameDAO.getGameByName(giocoNonEsistente.getName()));
    }

    @Test
    public void TestGetAllGamesByUserHasGames() {
        assertNotNull(gameDAO.getAllGamesByUser(1));
    }

    @Test
    public void TestGetAllGamesByUserHasNoGames() {
        assertNull(gameDAO.getAllGamesByUser(2));
    }

    @Test
    public void TestSearchGamesExisting() {
        assertNotNull(gameDAO.searchGames(giocoEsistente.getName()));
    }

    @Test
    public void TestSearchGamesNotExisting() {
        assertNull(gameDAO.searchGames(giocoNonEsistente.getName()));
    }

    @Test
    public void TestSearchGamesInLibraryHasGames() {
        assertNotNull(gameDAO.searchGamesInLibrary(1,"Dark Souls"));
    }

    @Test
    public void TestSearchGamesInLibraryHasNoGames () {
        assertNull(gameDAO.searchGamesInLibrary(2, "Dark Souls"));
    }

    @Test
    public void TestAddGameNotExisting () {
        Game game = new Game(-10, 50, "I pirati dei Caraibi", "All'avventura negli oceani", "image", new Date(1000L), "landscape");
        assertEquals(true, gameDAO.addGame(game.getName(),game.getImage(),game.getPrice(),game.getRelease(),game.getDescription(),game.getLandscape()));
        gameDAO.deleteGame(gameDAO.getGameByName("I pirati dei Caraibi").getId());
    }

    @Test
    public void TestUpdateGameExisting () {
        Game game = new Game(-10, 50, "I pirati dei Caraibi", "All'avventura negli oceani", "image", new Date(1000L), "landscape");
        gameDAO.addGame(game.getName(),game.getImage(),game.getPrice(),game.getRelease(),game.getDescription(),game.getLandscape());
        game = gameDAO.getGameByName("I pirati dei Caraibi");
        game.setPrice(120);
        assertEquals(true,gameDAO.updateGame(game));
        gameDAO.deleteGame(game.getId());
    }

    
    @Test
    public void TestUpdateGameNotExisting () {
        Game game = new Game(-10, 50, "I pirati dei Caraibi", "All'avventura negli oceani", "image", new Date(1000L), "landscape");
        assertEquals(false, gameDAO.updateGame(game));
    }
    

    @Test
    public void TestDeleteGameExisting () {
        Game game = new Game(-10, 50, "I pirati dei Caraibi", "All'avventura negli oceani", "image", new Date(1000L), "landscape");
        gameDAO.addGame(game.getName(),game.getImage(),game.getPrice(),game.getRelease(),game.getDescription(),game.getLandscape());
        game = gameDAO.getGameByName("I pirati dei Caraibi");
        assertEquals(true, gameDAO.deleteGame(game.getId()));
    }

    @Test
    public void TestDeleteGameNotExisting () {
        Game game = new Game(-10, 50, "I pirati dei Caraibi", "All'avventura negli oceani", "image", new Date(1000L), "landscape");
        assertEquals(false, gameDAO.deleteGame(game.getId()));
    }

    @After
    public void tearDown() {
        gameDAO.deleteGame(giocoEsistente.getId());
        
    }
}