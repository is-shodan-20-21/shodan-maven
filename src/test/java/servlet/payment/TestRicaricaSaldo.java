package servlet.payment;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.UserServlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestRicaricaSaldo{

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @InjectMocks
    private UserServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestRicaricaSaldoOK () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "updateBalance");
        request.setParameter("amount", "10");
        request.setParameter("cardId", "1");
        request.setParameter("puppet", "true");
        servlet.doGet(request, response);
        String oracle = "Ricarica effettuata con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRicaricaQuantitaNegativa () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "updateBalance");
        request.setParameter("amount", "-10");
        request.setParameter("cardId", "1");
        request.setParameter("puppet", "true");
        servlet.doGet(request, response);
        String oracle = "Quantita' non valida";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRicaricaQuantitaNonValida () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "updateBalance");
        request.setParameter("amount", "abc");
        request.setParameter("cardId", "1");
        request.setParameter("puppet", "true");
        servlet.doGet(request, response);
        String oracle = "Formato quantita' non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }



    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
