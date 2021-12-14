// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class LoginTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void loginOK() {
    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1900, 1020));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("app")));
    }
  }
  @Test
  public void loginPasswordErrata() {
    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1900, 1020));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("1");
    driver.findElement(By.id("login-submit")).click();
    assertThat(driver.findElement(By.id("login-message")).getText(), is("Password errata"));
  }
  @Test
  public void loginUtenteInesistente() {
    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1900, 1020));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("utenteNonEsistente");
    driver.findElement(By.id("login-password")).sendKeys("12345a");
    driver.findElement(By.id("login-submit")).click();
    assertThat(driver.findElement(By.id("login-message")).getText(), is("Utente non esistente"));
  }
}
