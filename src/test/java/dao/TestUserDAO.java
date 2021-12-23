package dao;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.DBConnectionPool;
import Model.Role;
import Model.User;
import Service.UserService;

public class TestUserDAO {

    private Connection db;
    private UserService userDAO;
    private User utenteEsistente;
    private User utenteNonEsistente;

    public TestUserDAO () {}

    @Before
    public void setUp() {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userDAO = new UserService(db);
        utenteEsistente = new User(-1, "SampleUsername", "SamplePassword", "sample@email.com", 500, "session", new ArrayList<Role>());
        utenteNonEsistente = new User(-2,"unexisting","unexisting","unexisting@email.com",500,"session",new ArrayList<Role>());

        userDAO.insertUser(utenteEsistente.getName(), utenteEsistente.getPassword(), utenteEsistente.getEmail());
        utenteEsistente.setId(userDAO.getIdByUsername(utenteEsistente.getName()));
        System.out.println("L'utente esistente ha ora id: "+utenteEsistente.getId());
    }

    @Test
    public void TestGetIdByUsernameExisting () {
        assertNotEquals(-1,userDAO.getIdByUsername(utenteEsistente.getName()));
    }

    @Test
    public void TestGetIdByUsernameNotExisting () {
        assertEquals(-1, userDAO.getIdByUsername(utenteNonEsistente.getName()));
    }

    @Test
    public void TestGetUserExisting () {
        assertNotNull(userDAO.getUser(utenteEsistente.getId()));
    }

    @Test
    public void TestGetUserNotExisting () {
        assertNull(userDAO.getUser(utenteNonEsistente.getId()));
    }

    @Test
    public void TestFindUserByEmailExisting () {
        assertEquals(true,userDAO.findUserByEmail(utenteEsistente.getEmail()));
    }

    @Test
    public void TestFindUserByEmailNotExisting () {
        assertEquals(false,userDAO.findUserByEmail(utenteNonEsistente.getEmail()));
    }

    /*
    @Test
    public void TestInsertUserExisting () {
        assertEquals(false,userDAO.insertUser(utenteEsistente.getName(), utenteEsistente.getPassword(), utenteEsistente.getEmail()));
    }
    */

    @Test
    public void TestInsertUserNotExisting () {
        assertEquals(true,userDAO.insertUser(utenteNonEsistente.getName(),utenteNonEsistente.getPassword(),utenteNonEsistente.getEmail()));
        utenteNonEsistente = userDAO.getUserByUsername("unexisting");
        System.out.println("L'id dell'utente appena inserito :"+utenteNonEsistente.getId());
        assertEquals(true,userDAO.deleteUser(utenteNonEsistente.getId()));
    }

    @Test
    public void TestDeleteUserExisting () {
        assertEquals(true,userDAO.deleteUser(utenteEsistente.getId()));
        assertEquals(true,userDAO.insertUser(utenteEsistente.getName(), utenteEsistente.getPassword(), utenteEsistente.getEmail()));
        utenteEsistente = userDAO.getUserByUsername("SampleUsername");
        assertNotEquals(-1, utenteEsistente.getId());
    }

    @Test
    public void TestGetUserByUsernameExisting () {
        assertNotNull(userDAO.getUserByUsername(utenteEsistente.getName()));
    }

    @Test
    public void TestGetUserByUsernameNotExisting () {
        assertNull(userDAO.getUserByUsername(utenteNonEsistente.getName()));
    }

    @After
    public void tearDown () {
        System.out.println("Sono entrato nella tearDown");
        userDAO.deleteUser(utenteEsistente.getId());
    }
    
    


}