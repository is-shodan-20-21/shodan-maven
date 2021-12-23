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
import Model.HasCart;
import Model.Role;
import Model.User;
import Service.GameService;
import Service.HasCartService;
import Service.UserService;

public class TestHasCartDAO {
    private Connection db;
    private HasCartService hascartDAO;
    private GameService gameDAO;
    private UserService userDAO;

    private HasCart cartElement;
    private User gamer;
    private User normie;
    private User unexistingUser;
    private Game inCart;
    private Game notInCart;

    public TestHasCartDAO () {}

    @Before
    public void setUp () {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        hascartDAO = new HasCartService(db);
        gameDAO = new GameService(db);
        userDAO = new UserService(db);

        gamer = new User(-1, "Gamer", "password", "gamer@email.com", 5000, "session", new ArrayList<Role>());
        userDAO.insertUser(gamer.getName(), gamer.getPassword(), gamer.getEmail());
        gamer = userDAO.getUserByUsername(gamer.getName());

        normie = new User(-1, "Normie", "password","normie@email.com", 5000, "session",new ArrayList<Role>());
        userDAO.insertUser(normie.getName(), normie.getPassword(), normie.getEmail());
        normie = userDAO.getUserByUsername(normie.getName());

        inCart= new Game(-1, 50, "Nel carrello", "Descrizione", "image", new Date(1000L), "landscape");
        gameDAO.addGame(inCart.getName(), inCart.getImage(), inCart.getPrice(), inCart.getRelease(), inCart.getDescription(), inCart.getLandscape());
        inCart = gameDAO.getGameByName(inCart.getName());

        notInCart= new Game(-1, 50, "Non nel carrello", "Descrizione", "image", new Date(1000L), "landscape");
        gameDAO.addGame(notInCart.getName(), notInCart.getImage(), notInCart.getPrice(), notInCart.getRelease(), notInCart.getDescription(), notInCart.getLandscape());
        notInCart = gameDAO.getGameByName(notInCart.getName());

        unexistingUser = new User(-1, "unexisting", "unexisting", "unexisting@email.com", 0, "session", null);

        cartElement = new HasCart(gamer.getId(), inCart.getId());
        hascartDAO.addItem(cartElement);

    }

    @Test
    public void TestAddCartUnbinded () {
        HasCart temp = new HasCart(gamer.getId(), notInCart.getId());
        assertEquals(true, hascartDAO.addItem(temp));
        assertEquals(true, hascartDAO.removeItem(temp));
    }

    @Test
    public void TestAddCartBinded () {
        assertEquals(false, hascartDAO.addItem(cartElement));
    }

    @Test
    public void TestRemoveItemUnbinded () {
        assertEquals(false,hascartDAO.removeItem(new HasCart(normie.getId(),notInCart.getId())));
    }

    @Test
    public void TestRemoveItemBinded () {
        assertEquals(true, hascartDAO.removeItem(cartElement));
        assertEquals(true, hascartDAO.addItem(cartElement));
    }

    @Test
    public void TestRemoveItemForAllUnbinded () {
        assertEquals(false, hascartDAO.removeItemForAll(notInCart));
    }

    @Test
    public void TestRemoveItemForAllBinded () {
        assertEquals(true,hascartDAO.removeItemForAll(inCart));
        assertEquals(true, hascartDAO.addItem(cartElement));
    }

    @Test
    public void TestDropCartUserExists () {
        assertEquals(true, hascartDAO.dropCart(gamer));
        assertEquals(true, hascartDAO.addItem(cartElement));
    }

    @Test
    public void TestDropCartUserNotExists () {
        assertEquals(false,hascartDAO.dropCart(unexistingUser));
    }

    @Test
    public void TestSelectCart () {
        assertEquals(true, hascartDAO.selectCart(gamer).size()>0);
    }

    @Test
    public void TestSelectCartEmpty () {
        assertEquals(true, hascartDAO.selectCart(normie).size()==0);
    }

    @Test
    public void TestHasInCartBinded () {
        assertEquals(true, hascartDAO.hasInCart(gamer, inCart));
    }

    @Test
    public void TestHasInCartUnbinded () {
        assertEquals(false, hascartDAO.hasInCart(normie, inCart));
    }

    @Test
    public void TestHasInCartUnexistingUser () {
        assertEquals(false, hascartDAO.hasInCart(unexistingUser, inCart));
    }

    @After
    public void tearDown () {
        hascartDAO.removeItem(cartElement);
        gameDAO.deleteGame(inCart.getId());
        gameDAO.deleteGame(notInCart.getId());
        userDAO.deleteUser(gamer.getId());
        userDAO.deleteUser(normie.getId());
    }
}
