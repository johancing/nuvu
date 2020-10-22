package co.johancas.nuvu.core;

public abstract class AFactory {

	private String indice;

	public abstract Object construirEntidad(String linea);

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

}
