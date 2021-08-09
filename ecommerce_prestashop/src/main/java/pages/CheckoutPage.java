package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

	private WebDriver driver; // variavel padrao para todas as classes de paginas

	public CheckoutPage(WebDriver driver) {// construtor padrão para todas as classes de páginas
		this.driver = driver;
	}

	private By totalTaxIncTotal = By.cssSelector("div.cart-total span.value");

	private By nomeCliente = By.cssSelector("div.address");

	private By botaoContinueAddress = By.name("confirm-addresses");

	private By shippingValor = By.cssSelector("span.carrier-price");

	private By botaoContinueShipping = By.name("confirmDeliveryOption");

	private By radioPayByCheck = By.id("payment-option-1");

	private By amountPayByCheck = By
			.cssSelector("#payment-option-1-additional-information >section>dl>dd:nth-child(2)");

	private By checkboxIAgree = By.id("conditions_to_approve[terms-and-conditions]");

	private By botaoConfirmaPedido = By.cssSelector("#payment-confirmation button");

	public String obter_totalTaxIncTotal() {
		return driver.findElement(totalTaxIncTotal).getText();
	}

	public String obter_nomeCliente() {
		return driver.findElement(nomeCliente).getText();
	}

	public void clicarBotaoContinueAddress() {
		driver.findElement(botaoContinueAddress).click();
	}

	public String obter_shippingValor() {
		return driver.findElement(shippingValor).getText();
	}

	public void clicarBotaoContinueShipping() {
		driver.findElement(botaoContinueShipping).click();
	}

	public void selecionarRadioPayByCheck() { // método
		driver.findElement(radioPayByCheck).click();
	}

	public String obter_amountPayByCheck() {
		return driver.findElement(amountPayByCheck).getText(); // uso do return pois se tratava de uma string ----
																// string retorna alguma coisa
	}

	public void selecionarCheckboxIAgree() {
		driver.findElement(checkboxIAgree).click();

	}

	// metodo para ter certeza que o checkbox está de fato selecionado

	public boolean estaSelecionadoCheckboxIAgree() { // esse metodo devolve um boolean se estiver selecionado esse
														// checkbox

		return driver.findElement(checkboxIAgree).isSelected(); // isSelected vai retornar verdadeiro se atualmente o
																// checkbox esta
																// selecionado
	}

	public PedidoPage clicarBotaoConfirmaPedido() { // apos clicar no botao confirma pedido ---- vai para a página de
													// pedido para confirmar as informações do pedido
		driver.findElement(botaoConfirmaPedido).click();
		return new PedidoPage(driver);
	}
}
