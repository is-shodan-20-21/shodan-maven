package servlet.payment;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.CartServlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestPagaOra{

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @InjectMocks
    private CartServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestPagaOraOK () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "pay");
        request.setParameter("total", "10");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Acquisto effettuato con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestPagaOraSaldoInsufficiente () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "pay");
        request.setParameter("total", "900");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Saldo insufficiente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestPagaOraCarrelloVuoto () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "pay");
        request.setParameter("total", "900");
        request.setParameter("puppet", "true");
        request.setParameter("games", "0");
        servlet.doPost(request, response);
        String oracle = "Carrello non contiene giochi";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }



    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
