package servlet.game;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.GameServlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestRimuoviTitolo extends Mockito {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @InjectMocks
    private GameServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestRimozioneCorretta () throws ServletException, IOException{
        request.setParameter("action", "deleteGame");
        request.setParameter("deleteGameId", "1");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Titolo rimosso con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRimozioneTitoloNonPresente () throws ServletException, IOException{
        request.setParameter("action", "deleteGame");
        request.setParameter("deleteGameId", "100");
        servlet.doPost(request, response);
        String oracle = "Il titolo non e' presente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRimozioneIdFormatoErrato () throws ServletException, IOException{
        request.setParameter("action", "deleteGame");
        request.setParameter("deleteGameId", "q");
        servlet.doPost(request, response);
        String oracle = "Formato ID errato";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRimozioneIdCampoObbligatorio () throws ServletException, IOException{
        request.setParameter("action", "deleteGame");
        request.setParameter("deleteGameId", "");
        servlet.doPost(request, response);
        String oracle = "ID Gioco: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}