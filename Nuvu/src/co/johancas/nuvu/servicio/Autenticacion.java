package co.johancas.nuvu.servicio;

import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import co.johancas.nuvu.core.AGestorArchivos;
import co.johancas.nuvu.core.ArchivoUsuarios;
import co.johancas.nuvu.modelo.Usuario;
import co.johancas.nuvu.utilidades.AES;

public class Autenticacion {
	
	private static Logger LOGGER = Logger.getLogger("Autenticacion");

	public Usuario autenticar(Usuario usuario) {
		if (usuario == null) {
			return noLogin();
		}
		LOGGER.log(Level.INFO, usuario.toString());
		AGestorArchivos archivo = new ArchivoUsuarios();
		Map<String, Object> usuarios = null;
		try {
			 usuarios = archivo.getEntidades();
			 LOGGER.log(Level.INFO, usuarios.toString());
			 if (usuarios.containsKey(usuario.getLogin())) {
				usuario = (Usuario) usuarios.get(usuario.getLogin());
				usuario.setUltimoLogin(new Date());
				usuario.setActivo(true);
				usuario.setPassword(AES.encrypt(usuario.toString()));
				return usuario;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noLogin();
	}
		
	private Usuario noLogin() {
		Usuario usuario = new Usuario();
		usuario.setUltimoLogin(new Date());
		return usuario;
	}

}
