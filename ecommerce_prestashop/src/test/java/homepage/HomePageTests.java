package homepage;

//Importa��es Hamcrest (opcional). Pode utilizar ao inv�s disso os asserts do JUnit (4 ou 5) conforme desejar
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Importa��es JUnit 4
//Asserts
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//Annotation Test
//import org.junit.Test;

import java.util.List;

//Importa��es JUnit 5
//Asserts
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//Annotation Test
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTests extends BaseTests { // Classe HomePageTests deriva da classe BaseTests

	// Isso quer dizer que ela herda as propriedades e os métodos dessa classe

	// (classe-mãe---Base Tests)

	@Test
	public void testContarProdutos_oitoProdutosDiferentes() {
		carregarPaginaInicial();
		assertThat(homePage.contarProdutos(), is(8)); // Anotação HAMCREST ---- ASSERTTHAT
//		assertEquals(8, homePage.contarProdutos());	// Anotação JUNIT ------ ASSERTEQUALS ------ ASSERTTRUE	
	}

	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutosNoCarrinho();
		assertThat(produtosNoCarrinho, is(0));
//		assertEquals(0, produtosNoCarrinho);
	}

	ProdutoPage produtoPage;
	String nomeProduto_ProdutoPage;

	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);

		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		System.out.println(nomeProduto_ProdutoPage);
		System.out.println(precoProduto_ProdutoPage);

		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));

//		assertEquals(nomeProduto_HomePage.toUpperCase(), (nomeProduto_ProdutoPage.toUpperCase()));
//		assertEquals(precoProduto_HomePage, (precoProduto_ProdutoPage));

	}

	LoginPage loginPage;

	@Test // ---- TESTE EM SI
	public void testLoginComSucesso_UsuarioLogado() {
		// Clicar no bot�o Sign In na home page
		loginPage = homePage.clicarBotaoSignIn();

		// Preencher usuario e senha
		loginPage.preencherEmail("marcelo@teste.com");
		loginPage.preencherPassword("marcelo");

		// Clicar no bot�o Sign In para logar
		loginPage.clicarBotaoSignIn();

		// Validar se o usu�rio est� logado de fato
		assertThat(homePage.estaLogado("Marcelo Bittencourt"), is(true));
//		assertTrue(homePage.estaLogado("Marcelo Bittencourt"));

		carregarPaginaInicial();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';') // numLinesToSkip ----
																								// pular a 1 ª linha
	public void testLogin_UsuarioLogadoComDadosValidos(String nomeTeste, String email, String password,
			String nomeUsuario, String resultado) {
		// Clicar no bot�o Sign In na home page
		loginPage = homePage.clicarBotaoSignIn();

		// Preencher usuario e senha
		loginPage.preencherEmail(email);
		loginPage.preencherPassword(password);

		// Clicar no bot�o Sign In para logar
		loginPage.clicarBotaoSignIn();

		boolean esperado_loginOK;
		if (resultado.equals("positivo"))
			esperado_loginOK = true;
		else
			esperado_loginOK = false;

		// Validar se o usu�rio est� logado de fato
		assertThat(homePage.estaLogado(nomeUsuario), is(esperado_loginOK));
//		assertTrue(homePage.estaLogado("Marcelo Bittencourt"));

		// esse metodo vai tirar uma foto da nossa tela e salvar em um arquivo de
		// maneira automatica

		capturarTela(nomeTeste, resultado);

		if (esperado_loginOK)
			homePage.clicarBotaoSignOut(); // verifica se o booleano "esperado_loginOK" ja foi executado
											// e clica no botao "SignOut" para deslogar do site

		carregarPaginaInicial();

	}

	ModalProdutoPage modalProdutoPage; // essa variavel modalProdutoPage pode ser acessada por qualquer um dos metodos
										// dentro da classe HomePageTests

	@Test
	public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() {

		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;

		// --Pr�-condi��o
		// usu�rio logado
		if (!homePage.estaLogado("Marcelo Bittencourt")) {
			testLoginComSucesso_UsuarioLogado();
		}

		// --Teste
		// Selecionando produto
		testValidarDetalhesDoProduto_DescricaoEValorIguais();

		// Selecionar tamanho
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();

		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());

		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);

		listaOpcoes = produtoPage.obterOpcoesSelecionadas();

		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());

		// Selecionar cor
		produtoPage.selecionarCorPreta();

		// Selecionar quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		// Adicionar no carrinho
		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// Valida��es

		assertThat(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"), is(true));
//		assertTrue(modalProdutoPage.obterMensagemProdutoAdicionado()
//				.endsWith("Product successfully added to your shopping cart"));

		System.out.println();

		assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
//		assertEquals((nomeProduto_ProdutoPage.toUpperCase()), modalProdutoPage.obterDescricaoProduto().toUpperCase());

		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);

		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto))); //

		// assertThat (o que é esperado, is(o que o programa vai encontrar ------
		// digitado pelo usuario)

		// assertThat ----- asserção HAMCREST

		// assertEquals / assertTrue ------- asserção JUNIT

		// assertEquals(o que o programa vai encontrar ------- digitado pelo usuario, o
		// que é esperado)

		// Integer.toString ------ converter um inteiro para String

//		assertEquals(tamanhoProduto, modalProdutoPage.obterTamanhoProduto());
//		assertEquals(corProduto, modalProdutoPage.obterCorProduto());
//		assertEquals(Integer.toString(quantidadeProduto), modalProdutoPage.obterQuantidadeProduto());

		String subtotalString = modalProdutoPage.obterSubtotal();
		subtotalString = subtotalString.replace("$", "");
		Double subtotal = Double.parseDouble(subtotalString);

		Double subtotalCalculado = quantidadeProduto * precoProduto;

		assertThat(subtotal, is(subtotalCalculado));
//		assertEquals(subtotalCalculado, subtotal);
	}

	// Valores esperados

	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;

	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_shippingTotal = 7.00;
	Double esperado_totalTaxExclTotal = esperado_subtotalTotal + esperado_shippingTotal;
	Double esperado_totalTaxIncTotal = esperado_totalTaxExclTotal;
	Double esperado_taxesTotal = 0.00;

	String esperado_nomeCliente = "Marcelo Bittencourt";

	CarrinhoPage carrinhoPage;

	@Test
	public void testIrParaCarrinho_InformacoesPersistidas() { // informa��es v�o precisar ser iguais na tela do
																// ModalPage �s que aparecem no carrinho de compras

		// Pr�-Condi��es
		// Produto inclu�do na tela ModalProdutoPage ----- teste anterior ----
		// testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso()

		testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();

		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();

		// Teste

		// Validar todos os elementos da tela ---- valida��o ser� feita na classe
		// CarrinhoPage

		System.out.println("*** TELA DO CARRINHO***");

		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));

		System.out.println("*** ITENS DE TOTAIS ***");

		System.out.println(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));

		// Asser��es Hamcrest ----- seguem um padr�o ------ assertThat-----

		// assertThat ------ primeiro o que n�s vamos encontrar ---- com o que n�s vamos
		// comparar----

		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_corProduto));
		assertThat(Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()),
				is(esperado_input_quantidadeProduto)); // Interger.parseInt() ------ converter String em Inteiro
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()),
				is(esperado_subtotalProduto));

		assertThat(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()),
				is(esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()), is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()),
				is(esperado_totalTaxExclTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()),
				is(esperado_totalTaxIncTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()), is(esperado_taxesTotal));

		// Asser��es JUnit
		/*
		 * assertEquals(esperado_nomeProduto, carrinhoPage.obter_nomeProduto());
		 * assertEquals(esperado_precoProduto,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		 * assertEquals(esperado_tamanhoProduto, carrinhoPage.obter_tamanhoProduto());
		 * assertEquals(esperado_corProduto, carrinhoPage.obter_corProduto());
		 * assertEquals(esperado_input_quantidadeProduto,
		 * Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()));
		 * assertEquals(esperado_subtotalProduto,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
		 * 
		 * assertEquals(esperado_numeroItensTotal,
		 * Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		 * assertEquals(esperado_subtotalTotal,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotal()));
		 * assertEquals(esperado_shippingTotal,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		 * assertEquals(esperado_totalTaxExclTotal,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		 * assertEquals(esperado_totalTaxIncTotal,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		 * assertEquals(esperado_taxesTotal,
		 * Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesTotal()));
		 */
	}

	CheckoutPage checkoutPage; // vari�vel acess�vel para uso em qualquer um dos metodos

	@Test
	public void testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk() {

		// Pr�-condi��es

		// Produto dispon�vel no carrinho de compras ------- metodo do teste anterior

		testIrParaCarrinho_InformacoesPersistidas();

		// Teste

		// Clicar no bot�o

		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();

		// Preencher informa��es de Pagamento/ Frete / certificar que o endere�o est� ok

		// Validar Informa��es na tela

		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxIncTotal()),
				is(esperado_totalTaxIncTotal));

		// assertThat(checkoutPage.obter_nomeCliente(), is(esperado_nomeCliente)); -----
		// assertThat ------ valida todo o nome do cliente obtido com o nome do cliente
		// mas ele encontra alem do nome do clinte o endere�o dele-----

		// o mais recomendavel � usar um assert do JUnit ---- no caso um assertTrue
		// -----

		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente)); // � esperado que ele comece com
																						// o cliente esperado ----

		checkoutPage.clicarBotaoContinueAddress();

		// assertThat ------ checar que o valor mostrado na tela realmente � o valor
		// esperado para ser mostrado ------

		String encontrado_shippingValor = checkoutPage.obter_shippingValor(); // recuperar o valor do shipping
		encontrado_shippingValor = Funcoes.removeTexto(encontrado_shippingValor, " tax excl."); // remover o final da
																								// String ---- no caso
																								// ----- tax excl.
		Double encontrado_shippingValor_Double = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor); // remover
																												// o //
																												// o
																												// cifrao
																												// do
																												// valor
																												// e
																												// devolver
																												// apenas
																												// o
																												// valor
																												// em
																												// double

		assertThat(encontrado_shippingValor_Double, is(esperado_shippingTotal)); // assertThat faz uma compara��o do
																					// double encontrado com o double
																					// esperado

		checkoutPage.clicarBotaoContinueShipping(); // clique no bot�o "continue" depois do shipping

		// Selecionar op��o "Pay by Check"

		checkoutPage.selecionarRadioPayByCheck();

		// Validar valor do cheque (amount)

		String encontrado_amountPayByCheck = checkoutPage.obter_amountPayByCheck(); // Recuperar o valor do Cheque

		encontrado_amountPayByCheck = Funcoes.removeTexto(encontrado_amountPayByCheck, " (tax incl.)"); // texto a ser
																										// retirado
																										// ------ " (tax
																										// incl.)"

		Double encontrado_amountPayByCheck_Double = Funcoes.removeCifraoDevolveDouble(encontrado_amountPayByCheck);

		assertThat(encontrado_amountPayByCheck_Double, is(esperado_totalTaxIncTotal)); // comparar o
																						// encontrado_amountPayByCheck_Double
																						// com o valor que se espera
																						// encontrar das taxas

		// Clicar na op��o "I agree"

		checkoutPage.selecionarCheckboxIAgree();

		// Asser��o do JUnit

		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());

	}

	@Test
	public void testFinalizarPedido_PedidoFinalizadoComSucesso() {

		// Pr�-condi��es

		// Checkout completamente conclu�do

		testIrParaCheckout_FreteMeioPagamentoEnderecoListadosOk();

		// Teste

		// Clicar no bot�o para confirmar o pedido

		PedidoPage pedidoPage = checkoutPage.clicarBotaoConfirmaPedido();

		// Validar os valores da tela

		// Validar se o texto retornado termina com a String "YOUR ORDER IS CONFIRMED"
		// ------- se terminar com essa String retorna "True" , caso contrario retorna
		// "False" ------

		assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));

		// assertThat(pedidoPage.obter_textoPedidoConfirmado().toUpperCase(), is("YOUR
		// ORDER IS CONFIRMED"));

		assertThat(pedidoPage.obter_email(), is("marcelo@teste.com"));

		assertThat(pedidoPage.obter_totalProdutos(), is(esperado_subtotalProduto));

		assertThat(pedidoPage.obter_totalTaxIncl(), is(esperado_totalTaxIncTotal));

		assertThat(pedidoPage.obter_metodoPagamento(), is("check"));

	}

}