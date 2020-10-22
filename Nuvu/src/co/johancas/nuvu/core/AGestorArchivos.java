package co.johancas.nuvu.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import co.johancas.nuvu.utilidades.Directorio;

public abstract class AGestorArchivos {

	protected Map<String, Object> entidades;
	protected File archivo;
	protected AFactory factoria;
	
	public AGestorArchivos() {
		entidades = new TreeMap<>();
	}
	
	public abstract void adicionar(Object objeto);
	public abstract void remover(String id);
	public abstract Object actualizar(String id, Object entidad);
	protected abstract void guardarArchivo();
	
	
	public Map<String, Object> getEntidades() throws Exception {
		leerArchivo();
		return entidades;
	}

	protected void leerArchivo() throws Exception {
		crearArchivo();
		entidades.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
			String linea = br.readLine();
			while (linea != null) {
				Object entidad = factoria.construirEntidad(linea);
				entidades.put(factoria.getIndice(), entidad);
				linea = br.readLine();
			}
		}
	}
	
	protected void crearArchivo() throws IOException {
		if (archivo != null) {
			Directorio dir = new Directorio();
			File directorio = new File(dir.getPath());
			if (!directorio.exists()) {
				directorio.mkdir();
			}
			if (!archivo.exists()) {
				archivo.createNewFile();
			}
		}
	}

}
