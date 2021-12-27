package servlet.account;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.SettingsServlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestModificaPassword {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @InjectMocks
    private SettingsServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestModificaPasswordCorretto () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("action", "updatePassword");
        request.setParameter("old_password", "123");
        request.setParameter("new_password", "newPass33");
        request.setParameter("new_password_again", "newPass33");
        servlet.doPost(request, response);
        String oracle = "Password modificata con successo";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaPasswordNonCoincidenti () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("action", "updatePassword");
        request.setParameter("old_password", "123");
        request.setParameter("new_password", "newPass33");
        request.setParameter("new_password_again", "newPass44");
        servlet.doPost(request, response);
        String oracle = "Le password non coincidono";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaPasswordRipetiMancante () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("action", "updatePassword");
        request.setParameter("old_password", "123");
        request.setParameter("new_password", "newPass33");
        request.setParameter("new_password_again", "");
        servlet.doPost(request, response);
        String oracle = "Ripeti password: campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaPasswordFormatoNuovaInvalido () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("action", "updatePassword");
        request.setParameter("old_password", "123");
        request.setParameter("new_password", "newPass");
        request.setParameter("new_password_again", "newPass");
        servlet.doPost(request, response);
        String oracle = "Formato nuova password non valido";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaPasswordNuovaMancante () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("action", "updatePassword");
        request.setParameter("old_password", "123");
        request.setParameter("new_password", "");
        request.setParameter("new_password_again", "newPass33");
        servlet.doPost(request, response);
        String oracle = "Nuova password campo obbligatorio";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestModificaPasswordErrata () throws ServletException, IOException {
        request.setParameter("puppet", "true");
        request.setParameter("action", "updatePassword");
        request.setParameter("old_password", "456");
        request.setParameter("new_password", "newPass33");
        request.setParameter("new_password_again", "newPass33");
        servlet.doPost(request, response);
        String oracle = "Password errata";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
