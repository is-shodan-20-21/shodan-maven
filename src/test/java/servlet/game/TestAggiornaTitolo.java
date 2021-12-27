package servlet.game;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.GameServlet;
import Service.GameService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestAggiornaTitolo extends Mockito {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    
    @Mock
    private GameService userDAO;

    @InjectMocks
    private GameServlet servlet;


    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestAggiornaTitoloCorretto () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "1");
        request.setParameter("updateGamePrice", "20");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Prezzo del titolo aggiornato con successo!";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiornaTitoloPrezzoNegativo () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "1");
        request.setParameter("updateGamePrice", "-20");
        servlet.doPost(request, response);
        String oracle = "Prezzo negativo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiornaTitoloFormatoPrezzoNonValido () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "1");
        request.setParameter("updateGamePrice", "abc");
        servlet.doPost(request, response);
        String oracle = "Formato prezzo non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiornaTitoloPrezzoCampoObbligatorio () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "1");
        request.setParameter("updateGamePrice", "");
        servlet.doPost(request, response);
        String oracle = "Prezzo: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiornaTitoloInesistente () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "100");
        request.setParameter("updateGamePrice", "20");
        servlet.doPost(request, response);
        String oracle = "Titolo non presente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiornaTitoloFormatoIdNonValido () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "q");
        request.setParameter("updateGamePrice", "20");
        servlet.doPost(request, response);
        String oracle = "Formato ID non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiornaTitoloIdCampoObbligatorio () throws ServletException, IOException{
        request.setParameter("action", "updateGame");
        request.setParameter("updateGameId", "");
        request.setParameter("updateGamePrice", "20");
        servlet.doPost(request, response);
        String oracle = "ID: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}