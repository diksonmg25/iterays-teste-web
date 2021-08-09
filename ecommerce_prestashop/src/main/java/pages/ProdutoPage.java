package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProdutoPage {

	private WebDriver driver; // declaração de um objeto do tipo WebDriver ----- 

	private By nomeProduto = By.className("h1");

	private By precoProduto = By.cssSelector(".current-price span:nth-child(1)");

	private By tamanhoProduto = By.id("group_1");

	private By inputCorPreta = By.xpath("//ul[@id='group_2']//input[@value='11']");

	private By quantidadeProduto = By.id("quantity_wanted");

	private By botaoAddToCart = By.className("add-to-cart");

	public ProdutoPage(WebDriver driver) { // Construtor 
		this.driver = driver;
	}

	public String obterNomeProduto() {
		return driver.findElement(nomeProduto).getText();

	}

	public String obterPrecoProduto() {
		return driver.findElement(precoProduto).getText();
	}

	public void selecionarOpcaoDropDown(String opcao) {
		encontrarDropdownSize().selectByVisibleText(opcao);
	}

	public List<String> obterOpcoesSelecionadas() {
		List<WebElement> elementosSelecionados = encontrarDropdownSize().getAllSelectedOptions();

		// List<WebElement> --- cria uma lista de objetos do tipo WebElement que recebe os elementos selecionados do DropDown 
		
					
		List<String> listaOpcoesSelecionadas = new ArrayList<String>(); // instancia o novo objeto criado "listaOpcoesSelecionadas"  
		
		
		// List<String> ------ cria uma lista de objetos do tipo String que recebe as opções selecionadas do DropDown 
		
		
		// dentro de um ArrayList ------- instancia esse objeto criado ---- "listaOpcoesSelecionadas" do mesmo tipo da Lista ----
		
		
		// no caso ------ uma Lista de String ------ usando o ArrayList e passando como parametro uma String	
		
		
		
		for (WebElement elemento : elementosSelecionados) { // loop que usa uma Lista de WebElement ----
			
			
			// cria um objeto para essa lista de WebElement --- nome do objeto criado --- "elemento" ----
			
						
			//  percorre cada uma dos elementosSelecionados desse DropDown -------- 
			
					
			// "elementosSelecionados" ------- objeto criado da lista de WebElement -------- 
			
			
			// e adiciona na Lista de String ----- "listaOpcoesSelecionadas" cada um dos itens selecionados dessa lista
			
			
			// passa como parametro o novo objeto criado da lista de WebElement  
			
			
			// objeto criado ----- "elemento" e pega o texto desse objeto
			
			
			listaOpcoesSelecionadas.add(elemento.getText());
		}
		return listaOpcoesSelecionadas; // retorna o objeto criado da lista de String 
		
		
		// objeto criado ---- "listaOpcoesSelecionadas" -------- que ficam armazenados dentro de um ArrayList 
	}

	public Select encontrarDropdownSize() { // Select ----- tipo de objeto que sera retornado 
		
		return new Select(driver.findElement(tamanhoProduto)); 
		
		// retorna esse metodo como um WebElement do tipo Select 
		
		
		// Uso do Casting para fazer esse retorno 
		
		
		// Criação de um metodo que vai encontrar esse DropDown 
		
				
		// e retornar esse DropDown no formato de um objeto do tipo Select 
		
	}

	public void selecionarCorPreta() {
		driver.findElement(inputCorPreta).click();
	}

	public void alterarQuantidade(int quantidade) {
		driver.findElement(quantidadeProduto).clear();
		driver.findElement(quantidadeProduto).sendKeys(Integer.toString(quantidade)); // sendkeys  --- jeito de escrever sempre uso com sendkeys ----- keys ------ teclado ------ sendkeys ---- enviar para o teclado
	}

	public ModalProdutoPage clicarBotaoAddToCart() { // ModalProdutoPage ---- tipo de objeto que sera retornado
		driver.findElement(botaoAddToCart).click();
		return new ModalProdutoPage(driver);
		
        // retorna esse metodo como um WebElement do tipo ModalProdutoPage
		
		
		// Uso do Casting para fazer esse retorno 
		
		
		// Criação de um metodo que vai encontrar o elemento HTML do botão "AddToCart"
		
		
		// E apos encontrar esse elemento e clicar nesse botao  
		
				
		
		// Retornar a mensagem de produto adicionado 
		
				
		// no formato de um objeto do tipo ModalProdutoPage
		
					
	}
}