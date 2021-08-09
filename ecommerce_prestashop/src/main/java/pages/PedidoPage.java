package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import util.Funcoes;

public class PedidoPage {

	private WebDriver driver; // variavel padrao para todas as classes de paginas

	public PedidoPage(WebDriver driver) { // construtor padrão para todas as classes de páginas
		this.driver = driver;
	}

	private By textoPedidoConfirmado = By.cssSelector("#content-hook_order_confirmation h3");

	private By email = By.cssSelector("#content-hook_order_confirmation p");

	private By totalProdutos = By.cssSelector("div.order-confirmation-table div.order-line div.row div.bold");

	private By totalTaxIncl = By.cssSelector("div.order-confirmation-table table tr.total-value td:nth-child(2)");

	private By metodoPagamento = By.cssSelector("#order-details ul li:nth-child(2)");

	public String obter_textoPedidoConfirmado() {
		return driver.findElement(textoPedidoConfirmado).getText();
	}

	public String obter_email() { // método
		// marcelo@teste.com---- texto final que será devolvido apos a remoção da outra
		// parte do texto

		String texto = driver.findElement(email).getText();// String texto ------- variavel que está sendo recuperada
		texto = Funcoes.removeTexto(texto, "An email has been sent to the "); // texto que vai servir como base ----
																				// variavel "texto" e texto "inicial "
																				// que será removido "An email has been
																				// sent to the" ------

		texto = Funcoes.removeTexto(texto, " address."); // texto para remover ------ texto "final" --- "address."

		return texto; // retorna no final o texto esperado ------- email ----- "marcelo@teste.com"
	}

	public Double obter_totalProdutos() { // método que retira o $ (Cifrao) e devolve o valor apenas como double ------
											// $ 38.24 ----- devolve 38.24 -----

		return Funcoes.removeCifraoDevolveDouble(driver.findElement(totalProdutos).getText());
	}

	public Double obter_totalTaxIncl() {
		return Funcoes.removeCifraoDevolveDouble(driver.findElement(totalTaxIncl).getText());
	}

	public String obter_metodoPagamento() {
		return Funcoes.removeTexto(driver.findElement(metodoPagamento).getText(), "Payment method: Payments by ");
	}

}
