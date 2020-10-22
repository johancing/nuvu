package co.johancas.nuvu.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.johancas.nuvu.utilidades.Constantes;

public class Usuario {

	private String login;
	private String nombres;
	private String apellidos;
	private String password;
	private boolean activo;
	private String ultimoLogin;
	
	public Usuario() {
		setUltimoLogin(new Date());
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
		this.ultimoLogin = sdf.format(ultimoLogin);
	}
	
	public String objectString() {
		StringBuilder sb = new StringBuilder(login);
		sb.append(",");
		sb.append(nombres);
		sb.append(",");
		sb.append(apellidos);
		sb.append(",");
		sb.append(password);
		sb.append(",");
		sb.append(Boolean.toString(activo));
		sb.append(",");
		setUltimoLogin(new Date());
		sb.append(ultimoLogin);
		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(login);
		sb.append(",");
		sb.append(nombres);
		sb.append(",");
		sb.append(apellidos);
		sb.append(",");
		sb.append(Boolean.toString(activo));
		sb.append(",");
		setUltimoLogin(new Date());
		sb.append(ultimoLogin);
		return sb.toString();
	}

}
