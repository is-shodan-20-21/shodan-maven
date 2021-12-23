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
import Model.Transaction;
import Model.User;
import Service.GameService;
import Service.TransactionService;
import Service.UserService;

public class TestTransactionDAO {
    
    private Connection db;
    private TransactionService transactionDAO;
    private UserService userDAO;
    private GameService gameDAO;

    private User gamer;
    private User normie;
    private User unexistingUser;
    private Game bought;
    private Game notBought;
    private Transaction transaction;

    public TestTransactionDAO () {}

    @Before
    public void setUp () {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        transactionDAO = new TransactionService(db);
        userDAO = new UserService(db);
        gameDAO = new GameService(db);

        gamer = new User(-1, "Gamer", "password", "gamer@email.com", 5000, "session", new ArrayList<Role>());
        userDAO.insertUser(gamer.getName(), gamer.getPassword(), gamer.getEmail());
        gamer = userDAO.getUserByUsername(gamer.getName());

        normie = new User(-1, "Normie", "password","normie@email.com", 5000, "session",new ArrayList<Role>());
        userDAO.insertUser(normie.getName(), normie.getPassword(), normie.getEmail());
        normie = userDAO.getUserByUsername(normie.getName());

        bought= new Game(-1, 50, "Nel carrello", "Descrizione", "image", new Date(1000L), "landscape");
        gameDAO.addGame(bought.getName(), bought.getImage(), bought.getPrice(), bought.getRelease(), bought.getDescription(), bought.getLandscape());
        bought = gameDAO.getGameByName(bought.getName());

        notBought= new Game(-1, 50, "Non nel carrello", "Descrizione", "image", new Date(1000L), "landscape");
        gameDAO.addGame(notBought.getName(), notBought.getImage(), notBought.getPrice(), notBought.getRelease(), notBought.getDescription(), notBought.getLandscape());
        notBought = gameDAO.getGameByName(notBought.getName());

        unexistingUser = new User(-1, "unexisting", "unexisting", "unexisting@email.com", 0, "session", null);
        
        transaction = new Transaction(gamer, bought, new Date(1000L), bought.getPrice());
        transactionDAO.insertTransaction(transaction);
    }

    @Test
    public void TestGetTransactionsByUser () {
        assertEquals(true,transactionDAO.getTransactionsByUser(gamer).size()>0);
    }

    @Test
    public void TestGetTransactionByUserNotExisting () {
        assertEquals(true, transactionDAO.getTransactionsByUser(unexistingUser).size()==0);
    }

    @Test
    public void TestGetTransactionByUserNoTransactions () {
        assertEquals(true, transactionDAO.getTransactionsByUser(normie).size()==0);
    }

    @Test
    public void TestGetTransactions () {
        assertEquals(true, transactionDAO.getTransactions().size()>0);
    }

    @Test
    public void TestInsertTransactionsNewTransaction () {
        Transaction newTransaction = new Transaction(gamer, notBought, new Date(1000L), notBought.getPrice());
        assertEquals(true, transactionDAO.insertTransaction(newTransaction));
        assertEquals(true, transactionDAO.deleteTransaction(newTransaction));
    }

    @Test
    public void TestInsertTransactionAlreadyExist () {
        assertEquals(false, transactionDAO.insertTransaction(transaction));
    }

    @Test
    public void TestDeleteTransactionExisting () {
        assertEquals(true, transactionDAO.deleteTransaction(transaction));
        assertEquals(true, transactionDAO.insertTransaction(transaction));
    }

    @Test
    public void TestDeleteTransactionNotExisting () {
        Transaction newTransaction = new Transaction(gamer, notBought, new Date(1000L), notBought.getPrice());
        assertEquals(false, transactionDAO.deleteTransaction(newTransaction));
    }

    @After
    public void tearDown () {
        transactionDAO.deleteTransaction(transaction);
        gameDAO.deleteGame(bought.getId());
        gameDAO.deleteGame(notBought.getId());
        userDAO.deleteUser(gamer.getId());
        userDAO.deleteUser(normie.getId());
    }

}
