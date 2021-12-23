package dao;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.DBConnectionPool;
import Model.Game;
import Model.Role;
import Model.User;
import Service.GameService;
import Service.HasGameService;
import Service.UserService;

public class TestHasGameDAO {

    private Connection db;
    private HasGameService hasgameDAO;
    private UserService userDAO;
    private GameService gameDAO;
    private User gamerUser;
    private User normieUser;
    //private User unexistingUser;
    private Game sampleGame;
    //private Game unexistingGame;

    public TestHasGameDAO () {}

    @Before
    public void setUp () {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hasgameDAO = new HasGameService(db);
        userDAO = new UserService(db);
        gameDAO = new GameService(db);

        gamerUser = new User(-1, "gamer", "password", "gamer@email.com", 5000, "session", new ArrayList<Role>());
        userDAO.insertUser(gamerUser.getName(), gamerUser.getPassword(), gamerUser.getEmail());
        gamerUser = userDAO.getUserByUsername(gamerUser.getName());
        System.out.println("Gamer ha id: "+gamerUser.getId());

        normieUser = new User(-1,"normie","password","normie@email.com",500,"session",new ArrayList<Role>());
        userDAO.insertUser(normieUser.getName(), normieUser.getPassword(), normieUser.getEmail());
        normieUser = userDAO.getUserByUsername(normieUser.getName());
        System.out.println("Normie ha id: "+normieUser.getId());

        sampleGame = new Game(-1, 50, "Game example", "Game description", "image", new Date(1000L), "landscape");
        gameDAO.addGame(sampleGame.getName(), sampleGame.getImage(), sampleGame.getPrice(), sampleGame.getRelease(), sampleGame.getDescription(), sampleGame.getLandscape());
        sampleGame = gameDAO.getGameByName(sampleGame.getName());
        System.out.println("SampleGame ha id: "+sampleGame.getId());

        /*
        unexistingGame = new Game(-1,50, "unexisting","unexisting","image",new Date(1000L),"landscape");
        unexistingUser = new User(-1,"unexisting","unexisting","unexisting@email.com",0,"session",new ArrayList<Role>());
        */

        hasgameDAO.addGame(gamerUser, sampleGame);


    }

    @Test
    public void TestAddGameUnbinded () {
        assertEquals(true, hasgameDAO.addGame(normieUser, sampleGame));
        assertEquals(true, hasgameDAO.removeItem(normieUser, sampleGame));
    }

    @Test
    public void TestAddGameBinded () {
        assertEquals(false,hasgameDAO.addGame(gamerUser, sampleGame));
    }


    @Test
    public void TestHasGameUnbinded () {
        assertEquals(false, hasgameDAO.hasGame(normieUser, sampleGame));
    }

    @Test
    public void TestHasGameBinded () {
        assertEquals(true, hasgameDAO.hasGame(gamerUser, sampleGame));
    }

    @Test
    public void TestRemoveItemUnbinded () {
        assertEquals(false, hasgameDAO.removeItem(normieUser, sampleGame));
    }

    @Test
    public void TestRemoveItemBinded () {
        assertEquals(true,hasgameDAO.removeItem(gamerUser, sampleGame));
        assertEquals(true,hasgameDAO.addGame(gamerUser, sampleGame));
    }
    
    @After
    public void tearDown () {
        hasgameDAO.removeItem(gamerUser, sampleGame);
        gameDAO.deleteGame(sampleGame.getId());
        userDAO.deleteUser(gamerUser.getId());
        userDAO.deleteUser(normieUser.getId());
    }
    
    
}
