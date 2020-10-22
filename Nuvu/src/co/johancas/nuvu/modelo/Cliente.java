package co.johancas.nuvu.modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	
	private String login;
	private String nombres;
	private String apellidos;
	private String documento;
	private List<Tarjeta> tarjetas;
	
	public Cliente() {
		tarjetas = new ArrayList<>();
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(Tarjeta tarjeta) {
		this.tarjetas.add(tarjeta);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(login);
		sb.append(",");
		sb.append(nombres);
		sb.append(",");
		sb.append(apellidos);
		sb.append(",");
		sb.append(documento);
		return sb.toString();
	}

}
