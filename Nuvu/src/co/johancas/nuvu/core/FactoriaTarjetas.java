package co.johancas.nuvu.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import co.johancas.nuvu.modelo.Tarjeta;
import co.johancas.nuvu.utilidades.Constantes;

public class FactoriaTarjetas extends AFactory {

	@Override
	public Object construirEntidad(String linea) {
		if (linea != null && !linea.isEmpty()) {
			try {
				String[] valores = linea.split(",");
				Tarjeta tarjeta = new Tarjeta();
				tarjeta.setCliente(valores[0]);
				tarjeta.setCodigo(valores[1]);
				tarjeta.setTipo(valores[2]);
				SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
				tarjeta.setFechaActivacion(sdf.parse(valores[3]));
				setIndice(tarjeta.getCodigo());
				return tarjeta;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
