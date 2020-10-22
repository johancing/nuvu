package co.johancas.nuvu.servicios;

import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
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
import co.johancas.nuvu.core.ArchivoClientes;
import co.johancas.nuvu.core.ArchivoTarjetas;
import co.johancas.nuvu.modelo.Cliente;
import co.johancas.nuvu.modelo.Tarjeta;
import co.johancas.nuvu.utilidades.Constantes;

@Path("/clientes")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ServiceClientes {

	@GET
	public Response consultar(@Context HttpServletRequest request) {
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoClientes();
			Map<String, Object> listaClientes = null;
			try {
				listaClientes = listas.getEntidades();
				if (listaClientes.size() > 0) {
					addTarjetas(listaClientes);
					return Response.status(Response.Status.OK).entity(listaClientes.values()).build();
				}
				return Response.status(Response.Status.NO_CONTENT).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@GET
	@Path("{idCliente}")
	public Response consultarCliente(@Context HttpServletRequest request, @PathParam("idCliente") String idCliente) {
		if (idCliente == null || idCliente.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			ArchivoClientes clientes = new ArchivoClientes();
			Map<String, Object> listaClientes = null;
			try {
				listaClientes = clientes.getEntidades();
				if (listaClientes.containsKey(idCliente)) {
					Cliente cliente = (Cliente) listaClientes.get(idCliente);
					addTarjetas(listaClientes);
					return Response.status(Response.Status.OK).entity(cliente).build();
				}
				return Response.status(Response.Status.NOT_FOUND).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

	@POST
	public Response addCliente(@Context HttpServletRequest request, Cliente cliente) {
		if (cliente == null || cliente.getDocumento() == null || cliente.getDocumento().isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoClientes();
			Map<String, Object> listaClientes = null;
			try {
				listaClientes = listas.getEntidades();
				if (listaClientes.containsKey(cliente.getDocumento())) {
					return Response.status(Response.Status.CONFLICT).build();
				}
				listas.adicionar(cliente);
				return Response.created(URI.create("/" + cliente.getDocumento())).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@DELETE
	@Path("{idCliente}")
	public Response eliminarCliente(@Context HttpServletRequest request, @PathParam("idCliente") String idCliente) {
		if (idCliente == null || idCliente.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoClientes();
			Map<String, Object> listaClientes = null;
			try {
				listaClientes = listas.getEntidades();
				if (!listaClientes.containsKey(idCliente)) {
					return Response.status(Response.Status.NOT_FOUND).build();
				}
				listas.remover(idCliente);
				return Response.status(Response.Status.ACCEPTED).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
	
	@PUT
	@Path("{idCliente}")
	public Response actualizarCliente(@Context HttpServletRequest request, @PathParam("idCliente") String idCliente, Cliente cliente) {
		if (idCliente == null || idCliente.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();
		if (cliente == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		if ((Boolean) request.getSession().getAttribute(Constantes.USUARIO_VALIDO)) {
			AGestorArchivos listas = new ArchivoClientes();
			Map<String, Object> listaClientes = null;
			try {
				listaClientes = listas.getEntidades();
				if (!listaClientes.containsKey(idCliente)) {
					return Response.status(Response.Status.NOT_FOUND).build();
				}
				cliente = (Cliente) listas.actualizar(idCliente, cliente);
				return Response.status(Response.Status.ACCEPTED).entity(cliente).build();
			} catch (Exception e) {
				return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
			}
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
 
	private void addTarjetas(Map<String, Object> listaClientes) throws Exception {
		AGestorArchivos listas = new ArchivoTarjetas();
		Map<String, Object> listaTarjetas = listas.getEntidades();
		for (Entry<String, Object> tar : listaTarjetas.entrySet()) {
			Tarjeta t = (Tarjeta) tar.getValue();
			Cliente c = (Cliente) listaClientes.get(t.getCliente());
			if ((c != null)) {
				c.setTarjetas(t);
			}
		}

	}

}
