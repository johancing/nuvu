package co.johancas.nuvu.utilidades;

public class Directorio {
	
	private String usuarios;
	private String clientes;
	private String tarjetas;
	private String path;
	
	public Directorio() {
		path = this.getClass().getClassLoader().getResource("../").getPath();
		setUsuarios(path + "usuarios.dat");
		setClientes(path + "clientes.dat");
		setTarjetas(path + "tarjetas.dat");
	}

	public String getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(String usuarios) {
		this.usuarios = usuarios;
	}

	public String getClientes() {
		return clientes;
	}

	public void setClientes(String clientes) {
		this.clientes = clientes;
	}

	public String getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(String tarjetas) {
		this.tarjetas = tarjetas;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
