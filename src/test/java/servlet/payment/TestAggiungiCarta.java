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

public class TestAggiungiCarta{

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
    public void TestAggiungiCartaOK () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "1111222233334445");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Carta aggiunta con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaScaduta () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2020-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Carta scaduta";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaDataNonValida () throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2020-42-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Formato data non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }


    @Test
    public void TestAggiungiCartaCVVNonValido() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "5333");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Formato CVV non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaCVVMancante() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "CVV: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaTitolareNonValido() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "2");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Formato titolare non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaTitolareMancante() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Titolare: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaEsistente() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "5178916782087614");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Carta gia' esistente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaNumeroNonValido() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "11223344");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Formato numero carta non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaNumeroMancante() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "Visa");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Numero carta: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestAggiungiCartaCircuitoMancante() throws ServletException, IOException {
        request.setParameter("cookie", "true");
        request.setParameter("action", "newCard");
        request.setParameter("cardType", "");
        request.setParameter("cardOwner", "Giovanni Giorgio");
        request.setParameter("cardCVV", "533");
        request.setParameter("cardNumber", "1111222233334449");
        request.setParameter("cardDate", "2029-12-12");
        request.setParameter("puppet", "true");
        servlet.doPost(request, response);
        String oracle = "Circuito: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }



    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
