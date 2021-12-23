package dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.DBConnectionPool;
import Model.Card;
import Model.Role;
import Model.User;
import Service.CardService;
import Service.UserService;

public class TestCardDAO {
    private Connection db;
    private CardService cardDAO;
    private UserService userDAO;
    private Card cartaEsistente;
    private Card cartaNonEsistente;
    private User ownerUser;
    private User poorUser;

    public TestCardDAO () {}

    @Before
    public void setUp () {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cardDAO = new CardService(db);
        userDAO = new UserService(db);

        ownerUser = new User(-3,"Owner","password","owner@gmail.com",5000,"session",new ArrayList<Role>());
        userDAO.insertUser(ownerUser.getName(), ownerUser.getPassword(), ownerUser.getEmail());
        ownerUser = userDAO.getUserByUsername(ownerUser.getName());

        poorUser = new User(-4,"Poor","password","poor@gmail.com",0,"session",new ArrayList<Role>());
        userDAO.insertUser(poorUser.getName(), poorUser.getPassword(), poorUser.getEmail());
        poorUser = userDAO.getUserByUsername(poorUser.getName());

        cartaEsistente = new Card(-3,"VISA",5333171065934522L,"Owner",new Date(1000L),ownerUser);
        cartaNonEsistente = new Card(-4,"VISA",1234567891234567L,"unexisting",new Date(1000L),ownerUser);
        cardDAO.insertCard(cartaEsistente);
        cartaEsistente = cardDAO.getCardByNumber(cartaEsistente.getCard_number());

        System.out.println("Carta esistente ha id: "+cartaEsistente.getCard_id());
        System.out.println("Owner ha id: "+ownerUser.getId());
        System.out.println("Poor ha id: "+poorUser.getId());
    }

    @Test
    public void TestGetCardExisting () {
        assertNotNull(cardDAO.getCard(cartaEsistente.getCard_id()));
    }

    @Test
    public void TestGetCardNotExisting () {
        assertNull(cardDAO.getCard(cartaNonEsistente.getCard_id()));
    }

    @Test
    public void TestGetCardByNumberExisting () {
        assertNotNull(cardDAO.getCardByNumber(cartaEsistente.getCard_number()));
    }

    @Test
    public void TestGetCardByNumberNotExisting () {
        assertNull(cardDAO.getCardByNumber(cartaNonEsistente.getCard_number()));
    }

    @Test
    public void TestGetCardsByOwnerHasCards () {
        assertEquals(true,cardDAO.getCardsByOwner(ownerUser).size()>0);
    }

    @Test
    public void TestGetCardsByOwnerHasNoCards () {
        assertEquals(true,cardDAO.getCardsByOwner(poorUser).size()==0);
    }

    @Test
    public void TestGetAllCards () {
        assertEquals(true, cardDAO.getAllCards().size()>0);
    }

    @Test
    public void TestInsertCard () {
        assertEquals(true, cardDAO.insertCard(cartaNonEsistente));
        cartaNonEsistente = cardDAO.getCardByNumber(cartaNonEsistente.getCard_number());
        assertEquals(true,cardDAO.removeCard(cartaNonEsistente));
    }

    @Test
    public void TestRemoveCardExisting () {
        assertEquals(true,cardDAO.removeCard(cartaEsistente));
        assertEquals(true,cardDAO.insertCard(cartaEsistente));
        cartaEsistente = cardDAO.getCardByNumber(cartaEsistente.getCard_number());
    }

    @Test
    public void TestRemoveCardNotExisting () {
        assertEquals(false,cardDAO.removeCard(cartaNonEsistente));
    }

    @After
    public void tearDown () {
        cardDAO.removeCard(cartaEsistente);
        userDAO.deleteUser(ownerUser.getId());
        userDAO.deleteUser(poorUser.getId());
    }

}
