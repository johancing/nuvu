package co.johancas.nuvu.aplicacion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import co.johancas.nuvu.modelo.Usuario;
import co.johancas.nuvu.utilidades.AES;
import co.johancas.nuvu.utilidades.Constantes;

public class Autorizador implements Filter {

	private static Logger LOGGER = Logger.getLogger("Autorizador");
	public Autorizador() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String contextPath = req.getRequestURI();
		String token = req.getHeader("Authorization");
		if (token == null || token.isEmpty()) {
			req.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.FALSE);
		} else {
			if (contextPath.contains(Constantes.LOGIN_PATH)) {
				try {
					if (Constantes.VERSION_APLICACION.equals(AES.decrypt(token))) {
						req.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.TRUE);
					} else {
						req.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.FALSE);
					}
				} catch (Exception e) {
					req.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.FALSE);
				}
			} else {
				Usuario usuario = (Usuario) req.getSession().getAttribute(Constantes.USUARIO_AUTORIZADO);
				if (usuario != null) {
					LOGGER.log(Level.INFO, usuario.getPassword());
				} else {
					LOGGER.log(Level.INFO, "NO EXISTE USUARIO");
				}
				LOGGER.log(Level.INFO, "TOKEN:" + token);
				if (usuario == null || !token.equals(usuario.getPassword())) {
					req.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.FALSE);
				} else {
					req.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.TRUE);
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	

}
