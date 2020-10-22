package co.johancas.nuvu.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import co.johancas.nuvu.modelo.Cliente;
import co.johancas.nuvu.utilidades.Directorio;

public class ArchivoClientes extends AGestorArchivos {

	public ArchivoClientes() {
		super();
		Directorio dir = new Directorio();
		this.archivo = new File(dir.getClientes());
		this.factoria = new FactoriaClientes();
	}

	@Override
	public void adicionar(Object objeto) {
		if (objeto instanceof Cliente) {
			Cliente cliente = (Cliente) objeto;
			entidades.put(cliente.getDocumento(), cliente);
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
				Cliente cliente = (Cliente) objeto.getValue();
				escribir.write(cliente.toString());
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
			if (entidad instanceof Cliente) {
				Cliente nuevo = (Cliente) entidad;
				Cliente actual = (Cliente) entidades.get(id);
				actual.setApellidos((nuevo.getApellidos() != null) ? nuevo.getApellidos() : actual.getApellidos());
				actual.setLogin((nuevo.getLogin() != null) ? nuevo.getLogin() : actual.getLogin());
				actual.setNombres((nuevo.getNombres() != null) ? nuevo.getNombres() : actual.getNombres());
				guardarArchivo();
				return actual;
			}
		}
		return entidad;
	}

}
