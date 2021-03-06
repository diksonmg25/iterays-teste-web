package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	private WebDriver driver;

	List<WebElement> listaProdutos = new ArrayList<WebElement>(); // cria uma lista de objetos do tipo WebElement ------ nome da lista ------ listaProdutos ----- 
	
      // que recebe os produtos dentro de um ArrayList --------   instancia esse objeto criado ---- "listaProdutos" do mesmo tipo da Lista ----
	
	// no caso ------ uma Lista de WebElement ------ usando o ArrayList e passando como parametro um WebElement
	
	private By textoProdutosNoCarrinho = By.className("cart-products-count"); // elemento que possui essa classe "cart-products-count"

	private By produtos = By.className("product-description"); // elemento que possui essa classe "product-description"

	private By descricoesDosProdutos = By.cssSelector(".product-description a"); // elemento identificado de forma unica

	private By precoDosProdutos = By.className("price"); // elemento que possui essa classe "price"

	private By botaoSignIn = By.cssSelector("#_desktop_user_info span.hidden-sm-down"); // elemento identificado de forma unica

	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down"); // elemento identificado de forma unica

	private By botaoSignOut = By.cssSelector("a.logout "); // elemento identificado de forma unica
	
	public HomePage(WebDriver driver) { // construtor padrao
		this.driver = driver;
	}

	public int contarProdutos() {
		carregarListaProdutos(); // verifica se a lista de produtos tem de fato 8 produtos
		return listaProdutos.size();
	}

	private void carregarListaProdutos() {
		listaProdutos = driver.findElements(produtos); // varios elementos dentro da lista de WebElements 
	}

	public int obterQuantidadeProdutosNoCarrinho() {

		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosNoCarrinho).getText(); // retorna um WebElement de forma unica ------ retorna apenas a quantidade de produtos no carrinho 
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");

		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);

		return qtdProdutosNoCarrinho;
	}

	public String obterNomeProduto(int indice) {
		return driver.findElements(descricoesDosProdutos).get(indice).getText();
	}

	public String obterPrecoProduto(int indice) {
		return driver.findElements(precoDosProdutos).get(indice).getText();
	}

	public ProdutoPage clicarProduto(int indice) {
		driver.findElements(descricoesDosProdutos).get(indice).click();
		return new ProdutoPage(driver);
	}

	public LoginPage clicarBotaoSignIn() {
		driver.findElement(botaoSignIn).click();
		return new LoginPage(driver);
	}

	public boolean estaLogado(String texto) {
		return texto.contentEquals(driver.findElement(usuarioLogado).getText());
	}

	public void clicarBotaoSignOut() {
		driver.findElement(botaoSignOut).click();
	}

	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/"); // carregar pagina inicial

	}

	public String obterTituloPagina() { // metodo para obter titulo da pagina
		return driver.getTitle(); // getTitle ------- devolver o titulo da pagina

	}

	public boolean estaLogado() {
		return !"Sign in".contentEquals(driver.findElement(usuarioLogado).getText()); // se aparecer como resultado
																						// "Sign in" n??o esta logado
		// "Sign in" falso -------- n??o esta logado ------- sinal de nega????o (!) ---- resultado retornado diferente de "Sign in"
	}
}
