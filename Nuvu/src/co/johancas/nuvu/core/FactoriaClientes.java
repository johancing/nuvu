package co.johancas.nuvu.core;

import co.johancas.nuvu.modelo.Cliente;

public class FactoriaClientes extends AFactory {

	@Override
	public Object construirEntidad(String linea) {
		if (linea != null && !linea.isEmpty()) {
			String[] valores = linea.split(",");
			Cliente cliente = new Cliente();
			cliente.setLogin(valores[0]);
			cliente.setNombres(valores[1]);
			cliente.setApellidos(valores[2]);
			cliente.setDocumento(valores[3]);
			setIndice(cliente.getDocumento());
			return cliente;
		}
		return null;
	}

}
