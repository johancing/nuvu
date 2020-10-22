package co.johancas.nuvu.servicios;

import java.net.URI;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.johancas.nuvu.core.AGestorArchivos;
import co.johancas.nuvu.core.ArchivoUsuarios;
import co.johancas.nuvu.modelo.Usuario;
import co.johancas.nuvu.servicio.Autenticacion;
import co.johancas.nuvu.utilidades.Constantes;

@Path("/security")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ServiceLogin {
	
	private static Logger LOGGER = Logger.getLogger("ServiceLogin");
	
	@POST
	@Path("/login")
	public Response login(@Context HttpServletRequest request, Usuario usuario) {
		Autenticacion auth = new Autenticacion();
		usuario = auth.autenticar(usuario);
		LOGGER.log(Level.INFO, usuario + "");
		boolean isValido = (boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO); 
		if (!isValido || !usuario.isActivo()) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		request.getSession().setAttribute(Constantes.USUARIO_AUTORIZADO, usuario);
		LOGGER.log(Level.INFO, "SET USUARIO TO SESSION...");
		request.getSession().setAttribute(Constantes.USUARIO_VALIDO, Boolean.TRUE);
		return Response.status(Response.Status.OK).entity(usuario).build();
	}
	
	@GET
	public Response allLogins(@Context HttpServletRequest request) {
		if (!((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		AGestorArchivos archivo = new ArchivoUsuarios();
		Map<String, Object> usuarios = null;
		try {
			usuarios = archivo.getEntidades();
			if (usuarios.size() > 0) {
				return Response.status(Response.Status.OK).entity(usuarios.values()).build();
			}
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@POST
	@Path("/addlogin")
	public Response addLogin(@Context HttpServletRequest request, Usuario usuario) {
		if (!((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		AGestorArchivos archivo = new ArchivoUsuarios();
		Map<String, Object> usuarios = null;
		try {
			usuarios = archivo.getEntidades();
			if (usuarios.containsKey(usuario.getLogin())) {
				return Response.status(Response.Status.CONFLICT).build();
			}
			archivo.adicionar(usuario);
			return Response.created(URI.create("/login/" + usuario.getLogin())).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
 	
}
