package servlet.account;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.SettingsServlet;
import Service.UserService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestModificaEmail extends Mockito {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Mock
    private UserService userDAO;

    @InjectMocks
    private SettingsServlet servlet;


    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestModificaEmailCorretto () throws ServletException, IOException{
        request.setParameter("puppet", "true");
        request.setParameter("action", "updateEmail");
        request.setParameter("lastEmail", "slave@shodan.it");
        request.setParameter("email", "newslave@shodan.it");
        servlet.doPost(request, response);
        String oracle = "Email modificata con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaEmailGiaInUso () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("cookie", "false");
        request.setParameter("jsession","BF5C822F7398080DAA8D93DCF03FC905");
        request.setParameter("action", "updateEmail");
        request.setParameter("lastEmail", "slave@shodan.it");
        request.setParameter("email", "dummy@shodan.it");
        servlet.doPost(request, response);
        String oracle = "Email gia in uso";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaEmailFormatoNonValido () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("cookie", "false");
        request.setParameter("jsession","BF5C822F7398080DAA8D93DCF03FC905");
        request.setParameter("action", "updateEmail");
        request.setParameter("lastEmail", "slave@shodan.it");
        request.setParameter("email", "emailshodanit");
        servlet.doPost(request, response);
        String oracle = "Formato nuova email non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaEmailNuovaMailCampoObbligatorio () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("cookie", "false");
        request.setParameter("jsession","BF5C822F7398080DAA8D93DCF03FC905");
        request.setParameter("action", "updateEmail");
        request.setParameter("lastEmail", "slave@shodan.it");
        request.setParameter("email", "");
        servlet.doPost(request, response);
        String oracle = "Nuova mail campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaEmailErrata () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("cookie", "false");
        request.setParameter("jsession","BF5C822F7398080DAA8D93DCF03FC905");
        request.setParameter("action", "updateEmail");
        request.setParameter("lastEmail", "dummy@shodan.it");
        request.setParameter("email", "dummy2@shodan.it");
        servlet.doPost(request, response);
        String oracle = "Email errata";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
