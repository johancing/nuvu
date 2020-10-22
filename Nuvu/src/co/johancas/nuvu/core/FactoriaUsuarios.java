package co.johancas.nuvu.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import co.johancas.nuvu.modelo.Usuario;
import co.johancas.nuvu.utilidades.Constantes;

public class FactoriaUsuarios extends AFactory {

	@Override
	public Object construirEntidad(String linea) {
		if (linea != null && !linea.isEmpty()) {
			try {
				String[] valores = linea.split(",");
				Usuario usuario = new Usuario();
				usuario.setLogin(valores[0]);
				usuario.setNombres(valores[1]);
				usuario.setApellidos(valores[2]);
				usuario.setPassword(valores[3]);
				usuario.setActivo(Boolean.getBoolean(valores[4]));
				SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
				usuario.setUltimoLogin(sdf.parse(valores[5]));
				setIndice(usuario.getLogin());
				return usuario;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}