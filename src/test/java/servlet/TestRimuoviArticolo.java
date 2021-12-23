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

public class TestRimuoviArticolo extends Mockito {

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
    public void TestRimuoviArticoloOK () throws ServletException, IOException {
        request.addParameter("action", "deleteArticle");
        request.addParameter("puppet", "true");
        request.addParameter("deleteArticleId", "1");

        String oracle = "Articolo eliminato con successo";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 
    @Test
    public void TestRimuoviArticoloInesistente () throws ServletException, IOException {
        request.addParameter("action", "deleteArticle");
        request.addParameter("puppet", "true");
        request.addParameter("deleteArticleId", "999");

        String oracle = "Articolo non esistente";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 

    @Test
    public void TestRimuoviArticoloIDMancante () throws ServletException, IOException {
        request.addParameter("action", "deleteArticle");
        request.addParameter("puppet", "true");
        request.addParameter("deleteArticleId", "");

        String oracle = "ID: campo obbligatorio";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 

    @Test
    public void TestRimuoviArticoloFormatoIDErrato () throws ServletException, IOException {
        request.addParameter("action", "deleteArticle");
        request.addParameter("puppet", "true");
        request.addParameter("deleteArticleId", "abc");

        String oracle = "Formato ID errato";

        servlet.doPost(request, response);
        assertEquals(oracle, request.getAttribute("testMessage"));
    } 

    @AfterEach
    void tearDown() throws Exception {
        request = null;
        response = null;
    }
}
