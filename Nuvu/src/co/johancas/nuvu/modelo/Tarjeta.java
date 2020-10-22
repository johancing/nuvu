package co.johancas.nuvu.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import co.johancas.nuvu.utilidades.Constantes;

public class Tarjeta {
	
	private String cliente;
	private String codigo;
	private String tipo;
	private String FechaActivacion;
	
	public Tarjeta() {
		setFechaActivacion(new Date());
	}
	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFechaActivacion() {
		return FechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMATO_FECHA);
		FechaActivacion = sdf.format(fechaActivacion);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(cliente);
		sb.append(",");
		sb.append(codigo);
		sb.append(",");
		sb.append(tipo);
		sb.append(",");
		sb.append(FechaActivacion);
		return sb.toString();
	}

}
