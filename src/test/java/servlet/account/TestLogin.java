package servlet.account;

import org.junit.jupiter.api.AfterEach; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Control.LoginServlet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.servlet.ServletException;

public class TestLogin extends Mockito {
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    /*@Mock
    private UserService userDAO;*/

    @InjectMocks
    private LoginServlet servlet;


    @BeforeEach
    void setUp() throws Exception {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestLoginCorretto () throws ServletException, IOException{
        request.setParameter("username", "admin");
        request.setParameter("password", "123");
        request.setParameter("cookie", "true");
        servlet.doPost(request, response);
        String oracle = "Login riuscito";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestLoginPasswordErrata () throws ServletException, IOException {
        request.setParameter("username", "admin");
        request.setParameter("password", "124");
        request.setParameter("cookie", "true");
        servlet.doPost(request, response);
        String oracle = "Password errata";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @Test
    public void TestLoginUsernameInesistente () throws ServletException, IOException {
        request.setParameter("username", "beethoven");
        request.setParameter("password", "perelisa");
        request.setParameter("cookie", "true");
        servlet.doPost(request, response);
        String oracle = "Utente non esistente";
        assertEquals(oracle, request.getAttribute("testMessage"));
    }

    @AfterEach
    void tearDown() throws Exception {
        request=null;
        response=null;
    }
    
}
