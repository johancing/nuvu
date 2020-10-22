package co.johancas.nuvu.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import co.johancas.nuvu.modelo.Usuario;
import co.johancas.nuvu.utilidades.Directorio;

public class ArchivoUsuarios extends AGestorArchivos {
	
	public ArchivoUsuarios() {
		super();
		Directorio dir = new Directorio();
		this.archivo = new File(dir.getUsuarios());
		this.factoria = new FactoriaUsuarios();
	}

	@Override
	protected void guardarArchivo() {
		try {
			FileWriter escribir = new FileWriter(archivo);
			for (Entry<String, Object> objeto : entidades.entrySet()) {
				Usuario usuario = (Usuario) objeto.getValue();
				escribir.write(usuario.objectString());
				escribir.write(System.lineSeparator());
			}
			escribir.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void adicionar(Object objeto) {
		if (objeto instanceof Usuario) {
			Usuario usuario = (Usuario) objeto;
			entidades.put(usuario.getLogin(), usuario);
			guardarArchivo();
		}
	}

	@Override
	public Object actualizar(String id, Object entidad) {
		return null;
	}

	@Override
	public void remover(String id) {
		// TODO Auto-generated method stub
		
	}

}
