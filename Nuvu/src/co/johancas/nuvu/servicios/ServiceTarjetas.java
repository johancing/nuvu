package co.johancas.nuvu.servicios;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.johancas.nuvu.core.AGestorArchivos;
import co.johancas.nuvu.core.ArchivoTarjetas;
import co.johancas.nuvu.modelo.Tarjeta;
import co.johancas.nuvu.utilidades.Constantes;

@Path("/tarjetas")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ServiceTarjetas {
	
	@GET
	public Response consultar(@Context HttpServletRequest request) {
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoTarjetas();
			Map<String, Object> listaTarjetas = null;
			try {
				listaTarjetas = listas.getEntidades();
				if (listaTarjetas.size() > 0) {
					return Response.status(Response.Status.OK).entity(listaTarjetas.values()).build();
				}
				return Response.status(Response.Status.NO_CONTENT).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("{idTarjeta}")
	public Response consultarTarjeta(@Context HttpServletRequest request, @PathParam("idTarjeta") String idTarjeta) {
		if (idTarjeta == null || idTarjeta.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos tarjetas = new ArchivoTarjetas();
			Map<String, Object> listaTarjetas = null;
			try {
				listaTarjetas = tarjetas.getEntidades();
				if (listaTarjetas.containsKey(idTarjeta)) {
					Tarjeta tarjeta = (Tarjeta) listaTarjetas.get(idTarjeta);
					return Response.status(Response.Status.OK).entity(tarjeta).build();
				}
				return Response.status(Response.Status.NOT_FOUND).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	@Path("{idCliente}")
	public Response addTarjeta(@Context HttpServletRequest request, @PathParam("idCliente") String idCliente, Tarjeta tarjeta) {
		if (idCliente == null || idCliente.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if (tarjeta == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoTarjetas();
			Map<String, Object> listaTarjetas = null;
			try {
				listaTarjetas = listas.getEntidades();
				if (listaTarjetas.containsKey(tarjeta.getCodigo())) {
					return Response.status(Response.Status.CONFLICT).build();
				}
				tarjeta.setCliente(idCliente);
				tarjeta.setFechaActivacion(new Date());
				listas.adicionar(tarjeta);
				return Response.created(URI.create("/" + tarjeta.getCodigo())).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@DELETE
	@Path("{idTarjeta}")
	public Response eliminarCliente(@Context HttpServletRequest request, @PathParam("idTarjeta") String idTarjeta) {
		if (idTarjeta == null || idTarjeta.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoTarjetas();
			Map<String, Object> listaTarjetas = null;
			try {
				listaTarjetas = listas.getEntidades();
				if (!listaTarjetas.containsKey(idTarjeta)) {
					return Response.status(Response.Status.NOT_FOUND).build();
				}
				listas.remover(idTarjeta);
				return Response.status(Response.Status.ACCEPTED).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@PUT
	@Path("{idTarjeta}")
	public Response actualizarTarjeta(@Context HttpServletRequest request, @PathParam("idTarjeta") String idTarjeta, Tarjeta tarjeta) {
		if (idTarjeta == null || idTarjeta.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if (tarjeta == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoTarjetas();
			Map<String, Object> listaTarjetas = null;
			try {
				listaTarjetas = listas.getEntidades();
				if (!listaTarjetas.containsKey(idTarjeta)) {
					return Response.status(Response.Status.NOT_FOUND).build();
				}
				tarjeta = (Tarjeta) listas.actualizar(idTarjeta, tarjeta);
				return Response.status(Response.Status.ACCEPTED).entity(tarjeta).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}	

}
