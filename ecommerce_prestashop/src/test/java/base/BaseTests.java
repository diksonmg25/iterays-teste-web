package base;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//JUnit 5
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

//JUnit 4
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

import pages.HomePage;

public class BaseTests {

	private static WebDriver driver; // novo objeto criado do WebDriver
	protected HomePage homePage; // novo objeto criado da HomePage
	
	@BeforeAll // JUnit 5 // executa antes de qualquer coisa ------- ACONTECE UMA UNICA VEZ ANTES DE QUALQUER COISA
//	@BeforeClass //JUnit 4
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver\\84\\chromedriver.exe"); // setar o driver do WebDriver 
		driver = new ChromeDriver(); // instancia um novo objeto do tipo WebDriver ------ instanciar com o uso do operador "new" -----  
		
		// e usa o objeto criado "driver" para invocar um "webdriver" de um navegador específico 
		
		// Aqui, a variável de referência do tipo WebDriver ----- variavel "driver" permite atribuir o objeto "driver" a diferentes drivers específicos do navegador
		
		// Nesse caso o webdriver do Google Chrome ----- ChromeDriver -----  
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // ESPERA GERAL ------- WAIT EXPLICITO -----
	}

	@BeforeEach // JUnit 5
//	@Before //JUnit 4 ----  ACONTECE NO COMEÇO DE CADA TESTE  
	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage = new HomePage(driver);
	}

	public void capturarTela(String nomeTeste, String resultado) {
		var camera = (TakesScreenshot) driver;
		File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
		try {
			Files.move(capturaDeTela, new File("resources/screenshots/" + nomeTeste + "_" + resultado + ".png"));
		} catch (IOException e) { // tenta mover o arquivo e se encontrar uma exce��o de IO
			// ------ ele vai mostrar no StackTrace

			e.printStackTrace(); // linha com as informa��es detalhadas dessa exce��o caso ocorra uma exce��o ao
									// executar os testes
		}
	}

	@AfterAll // JUnit 5 // metodo de teste para ser excutado por ultimo -------- ACONTECE NO FINAL DE TODOS OS TESTES
//	@AfterClass //JUnit 4
	public static void finalizar() {
		driver.quit(); // sair da pagina ------ fechar a pagina 
	}

}
