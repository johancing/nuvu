package co.johancas.nuvu.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import co.johancas.nuvu.modelo.Tarjeta;
import co.johancas.nuvu.utilidades.Constantes;
import co.johancas.nuvu.utilidades.Directorio;

public class ArchivoTarjetas extends AGestorArchivos {

	public ArchivoTarjetas() {
		super();
		Directorio dir = new Directorio();
		this.archivo = new File(dir.getTarjetas());
		this.factoria = new FactoriaTarjetas();
	}

	@Override
	public void adicionar(Object objeto) {
		if (objeto instanceof Tarjeta) {
			Tarjeta tarjeta = (Tarjeta) objeto;
			entidades.put(tarjeta.getCodigo(), tarjeta);
			guardarArchivo();
		}
	}

	@Override
	public void remover(String id) {
		if (id != null && !id.isEmpty()) {
			entidades.remove(id);
			guardarArchivo();
		}
	}

	@Override
	protected void guardarArchivo() {
		try {
			FileWriter escribir = new FileWriter(archivo);
			for (Entry<String, Object> objeto : entidades.entrySet()) {
				Tarjeta tarjeta = (Tarjeta) objeto.getValue();
				escribir.write(tarjeta.toString());
				escribir.write(System.lineSeparator());
			}
			escribir.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object actualizar(String id, Object entidad) {
		if (id != null && !id.isEmpty()) {
			if (entidad instanceof Tarjeta) {
				Tarjeta nueva = (Tarjeta) entidad;
				Tarjeta actual = (Tarjeta) entidades.get(id);
				actual.setCliente((nueva.getCliente() != null) ? nueva.getCliente() : actual.getCliente());
				actual.setTipo((nueva.getTipo() != null) ? nueva.getTipo() : actual.getTipo());
				if (nueva.getFechaActivacion() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
					Date fecha = null;
					try {
						fecha = sdf.parse(nueva.getFechaActivacion());
						actual.setFechaActivacion(fecha);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				guardarArchivo();
				return actual;
			}
		}
		return entidad;	}

}
