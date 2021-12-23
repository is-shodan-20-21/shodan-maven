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
import Control.BlogServlet;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import javax.servlet.ServletException;

public class TestAggiungiArticolo extends Mockito {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @InjectMocks
    private BlogServlet servlet;
    
    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestAggiungiArticoloOK () throws ServletException, IOException {
        request.addParameter("action", "addArticle");
        request.addParameter("puppet", "true");
        request.addParameter("title", "Nuovo Titolo");
        request.addParameter("shortTitle", "Nuovo Sottotitolo");
        request.addParameter("html", "Nuovo Contenuto");

        String oracle = "Articolo aggiunto con successo";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 
    
    @Test
    public void TestAggiungiArticoloContenutoMancante () throws ServletException, IOException {
        request.addParameter("action", "addArticle");
        request.addParameter("puppet", "true");
        request.addParameter("title", "Nuovo Titolo");
        request.addParameter("shortTitle", "Nuovo Sottotitolo");
        //request.addParameter("html", "Nuovo Contenuto");

        String oracle = "Contenuto: campo obbligatorio";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 

    @Test
    public void TestAggiungiArticoloTitoloMancante () throws ServletException, IOException {
        request.addParameter("action", "addArticle");
        request.addParameter("puppet", "true");
        //request.addParameter("title", "Nuovo Titolo");
        request.addParameter("shortTitle", "Nuovo Sottotitolo");
        request.addParameter("html", "Nuovo Contenuto");

        String oracle = "Titolo: campo obbligatorio";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 

    @Test
    public void TestAggiungiArticoloSottotitoloMancante () throws ServletException, IOException {
        request.addParameter("action", "addArticle");
        request.addParameter("puppet", "true");
        request.addParameter("title", "Nuovo Titolo");
        //request.addParameter("shortTitle", "Nuovo Sottotitolo");
        request.addParameter("html", "Nuovo Contenuto");

        String oracle = "Sottotitolo: campo obbligatorio";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 

    @AfterEach
    void tearDown() throws Exception {
        request = null;
        response = null;
    }
}
