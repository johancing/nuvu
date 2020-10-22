package co.johancas.nuvu.aplicacion;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import co.johancas.nuvu.core.AGestorArchivos;
import co.johancas.nuvu.core.ArchivoUsuarios;
import co.johancas.nuvu.modelo.Usuario;
import co.johancas.nuvu.utilidades.AES;

public class Aplicacion {
	
	public static void main(String[] args) {
		Usuario us = new Usuario();
		us.setActivo(true);
		us.setApellidos("CASTRO");
		us.setLogin("johncs");
		us.setNombres("JOHAN");
		us.setPassword("2352136");
		us.setUltimoLogin(new Date());
		try {
			String encode = AES.encrypt("NuvuAplicacionV2.0-2020");
			System.out.println(encode);
			String decode = AES.decrypt(encode);
			System.out.println(decode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
