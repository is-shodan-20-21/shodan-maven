package servlet.account;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.SignInServlet;
import Service.UserService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestRegistrazione {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @InjectMocks
    private SignInServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestRegistrazioneCorretta () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Registrazione avvenuta con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneUsernameMancante () throws ServletException, IOException {
        request.setParameter("username", "");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Username campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneUsernameFormatoInvalido () throws ServletException, IOException {
        request.setParameter("username", "15eugenio");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Formato username non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneUsernameGiaEsistente () throws ServletException, IOException {
        request.setParameter("username", "admin");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Username gia esistente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazionePasswordMancante () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Password campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazionePasswordFormatoInvalido () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "44");
        request.setParameter("password2", "44");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Formato password non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneRipetiPasswordMancante () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Ripeti password campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazionePasswordDiverse () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1969");
        request.setParameter("email", "emontale@gmail.com");
        servlet.doPost(request, response);
        String oracle = "Le password non coincidono";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneEmailMancante () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "");
        servlet.doPost(request, response);
        String oracle = "Email campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneEmailFormatoInvalido () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "emontale");
        servlet.doPost(request, response);
        String oracle = "Formato email non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestRegistrazioneEmailGiaEsistente () throws ServletException, IOException {
        request.setParameter("username", "eugeniomontale");
        request.setParameter("password", "milionidiscale1967");
        request.setParameter("password2", "milionidiscale1967");
        request.setParameter("email", "antonio@shodan.it");
        servlet.doPost(request, response);
        String oracle = "Email gia esistente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
