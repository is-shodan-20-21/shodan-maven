package servlet;

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
import Model.Game;
import Service.GameService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestGameServlet extends Mockito {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private GameService gameDAO;

    @InjectMocks
    private GameServlet gameServlet;
    
    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestAddGameDescrizioneMancante () throws ServletException, IOException {
        request.addParameter("action", "addGame");
        //request.addParameter("endpoint", "View/Game.jsp");
        request.addParameter("game-name", "Ciccio Fusco");
        request.addParameter("game-price", "55");
        request.addParameter("game-date", "1999-10-01");
        request.addParameter("game-description", "");
        //request.addParameter("game-image", "Prova");
        //request.addParameter("game-landscape", "Prova");
        gameServlet.doPost(request, response);
        String oracle = "Descrizione: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }


}
