package util;

public class Funcoes {

	public static Double removeCifraoDevolveDouble(String texto) { // string text ----- texto cifrao e o valor que n�s
																	// queremos

		// static ---- n�o precisa criar um objeto da classe Fun�oes ---- apenas fazer
		// uso dos metodos

		texto = texto.replace("$", ""); // substituir o $19.12 por espa�o em branco ---- retorna 19.12------
		return Double.parseDouble(texto);
	}

	public static int removeTextoItemsDevolveInt(String texto) {
		texto = texto.replace(" items", "");
		return Integer.parseInt(texto);
	}

	public static String removeTexto(String texto, String textoParaRemover) {

		texto = texto.replace(textoParaRemover, ""); // substitui o textoParaRemover pelo espa�o em branco-----
		return texto;
	}

}
