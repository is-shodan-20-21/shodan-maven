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
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
public class AggiungiTitoloTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  private final String UT_NAMESPACE = "AggiungiTitolo_";
  JavascriptExecutor js;

  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "../commons/driver/chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void aggiungiTitoloNomeMancante() {
    String testName = UT_NAMESPACE + "NomeMancante";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-price")).sendKeys("55");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Nome: campo obbligatorio"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloPrezzoMancante() {
    String testName = UT_NAMESPACE + "PrezzoMancante";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    //driver.findElement(By.name("game-price")).sendKeys("55");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Prezzo: campo obbligatorio"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloDataMancante() {
    String testName = UT_NAMESPACE + "DataMancante";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("55");
    //driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Data: campo obbligatorio"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloDescrizioneMancante() {
    String testName = UT_NAMESPACE + "DescrizioneMancante";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("55");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    //driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Descrizione: campo obbligatorio"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloCoverMancante() {
    String testName = UT_NAMESPACE + "CoverMancante";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("55");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    //driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Cover: campo obbligatorio"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloSfondoMncante() {
    String testName = UT_NAMESPACE + "SfondoMancante";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("55");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    //driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Sfondo: campo obbligatorio"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloFormatoPrezzoNonValido() {
    String testName = UT_NAMESPACE + "FormatoPrezzoNonValido";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("abc");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Formato prezzo non valido"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloDataFuoriIntervallo() {
    String testName = UT_NAMESPACE + "DataFuoriIntervalloValido";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("2020-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Data fuori intervallo"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloFormatoDataNonValido() {
    String testName = UT_NAMESPACE + "FormatoDataNonValido";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("1995/01/01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Formato data non valido"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloTitoloEsistente() {
    String testName = UT_NAMESPACE + "TitoloEsistente";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("System Shock");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Titolo gia' presente"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloPrezzoNegativo() {
    String testName = UT_NAMESPACE + "PrezzoNegativo";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("-50");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Prezzo negativo"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloFormatoNomeNonValido() {
    String testName = UT_NAMESPACE + "FormatoNomeNonValido";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test__:^");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Formato nome non valido"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloFormatoCoverNonValido() {
    String testName = UT_NAMESPACE + "FormatoCoverNonValido";

    File cover = new File("example/dummy.txt");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Formato cover non valido"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloFormatoSfondoNonValido() {
    String testName = UT_NAMESPACE + "FormatoSfondoNonValido";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/dummy.txt");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Formato sfondo non valido"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }

  @Test
  public void aggiungiTitoloOK() {
    String testName = UT_NAMESPACE + "OK";

    File cover = new File("example/Cover.png");
    File sfondo = new File("example/Sfondo.png");

    driver.get("http://localhost:8080/shodan_maven/");
    driver.manage().window().setSize(new Dimension(1294, 1400));
    driver.findElement(By.id("login-username")).click();
    driver.findElement(By.id("login-username")).sendKeys("admin");
    driver.findElement(By.id("login-password")).click();
    driver.findElement(By.id("login-password")).sendKeys("123");
    driver.findElement(By.id("login-submit")).click();
    {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.id("roles-link")));
    }
    driver.findElement(By.name("game-name")).sendKeys("Test");
    driver.findElement(By.name("game-price")).sendKeys("50");
    driver.findElement(By.name("game-date")).sendKeys("1995-01-01");
    driver.findElement(By.id("textarea-game")).sendKeys("Descrizione");
    driver.findElement(By.id("image-loader")).sendKeys(cover.getAbsolutePath());
    driver.findElement(By.id("landscape-loader")).sendKeys(sfondo.getAbsolutePath());
    driver.findElement(By.cssSelector(".button:nth-child(9)")).click();
    try {
      assertThat(driver.findElement(By.id("add-game-result")).getText(), is("Gioco aggiunto"));
      System.out.println(testName + " PASS");
    } catch(AssertionError e) {
      System.out.println(testName + " FAIL");
    }
  }
}