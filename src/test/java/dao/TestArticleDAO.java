package dao;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.DBConnectionPool;
import Model.Article;
import Model.User;
import Service.ArticleService;
import Service.UserService;

public class TestArticleDAO {
    private Connection db;
    private ArticleService articleDAO;
    private UserService userDAO;
    private Article articoloEsistente;
    private Article articoloNonEsistente;
    private User autore;

    public TestArticleDAO () {}

    @Before
    public void setUp () {
        try {
            db = DBConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        articleDAO = new ArticleService(db);
        userDAO = new UserService(db);
        
        userDAO.insertUser("Autore", "Password", "autore@gmail.com");
        autore = userDAO.getUserByUsername("Autore");
        System.out.println("Autore ha ID: "+autore.getId());


        articoloEsistente = new Article("Benvenuto su Shodan","Buona navigazione","html",autore);
        articoloNonEsistente = new Article(-1,"inestitente","inesistente","html",autore);

        articleDAO.addArticle(articoloEsistente);
        articoloEsistente = articleDAO.findArticle(articoloEsistente.getTitle(), articoloEsistente.getShortTitle(), articoloEsistente.getHtml());

    }

    @Test
    public void TestGetArticleExisting () {
        assertNotNull(articleDAO.getArticle(articoloEsistente.getId()));
    }

    @Test
    public void TestGetArticleNotExisting () {
        assertNull(articleDAO.getArticle(articoloNonEsistente.getId()));
    }

    @Test
    public void TestGetAllArticles () {
        assertNotNull(articleDAO.getAllArticles(100));
    }

    @Test
    public void TestAddArticle () {
        Article nuovo = new Article("nuovo","nuovo","nuovo",autore);
        assertEquals(true,articleDAO.addArticle(nuovo));
        nuovo = articleDAO.findArticle("nuovo", "nuovo", "nuovo");
        articleDAO.deleteArticle(nuovo.getId());
    }

    @Test
    public void TestFindArticleExisting () {
        assertNotNull(articleDAO.findArticle(articoloEsistente.getTitle(), articoloEsistente.getShortTitle(), articoloEsistente.getHtml()));
    }

    @Test
    public void TestFindArticleNotExisting () {
        assertNull(articleDAO.findArticle(articoloNonEsistente.getTitle(), articoloNonEsistente.getShortTitle(), articoloNonEsistente.getHtml()));
    }

    @Test
    public void TestDeleteArticleExisting () {
        assertEquals(true,articleDAO.deleteArticle(articoloEsistente.getId()));
        assertNotEquals(null, articoloEsistente.getAuthor().getId());
        articleDAO.addArticle(articoloEsistente);
        articoloEsistente = articleDAO.findArticle(articoloEsistente.getTitle(), articoloEsistente.getShortTitle(), articoloEsistente.getHtml());
        assertNotEquals(-1, articoloEsistente.getId());
    }
    
    @Test
    public void TestDeleteArticleNotExisting () {
        assertEquals(false, articleDAO.deleteArticle(articoloNonEsistente.getId()));
    }
    
    @After
    public void tearDown () {
        articleDAO.deleteArticle(articoloEsistente.getId());
        userDAO.deleteUser(autore.getId());
    }   


    
}
