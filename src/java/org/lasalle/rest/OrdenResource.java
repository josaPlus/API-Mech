/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import org.lasalle.controller.OrdenController;
import org.lasalle.model.Orden;

/**
 *
 * @author josaf
 */
@Path("ordenes")
public class OrdenResource {
    
    // GET /api/ordenes/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws SQLException {
        OrdenController controller = new OrdenController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getAll());
        return Response.ok(out).build();
    }

    // GET /api/ordenes/getById/1
    @Path("getById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) throws SQLException {
        OrdenController controller = new OrdenController();
        Gson gson = new Gson();
        Orden orden = controller.getById(id);
        String out;

        if (orden != null) {
            out = gson.toJson(orden);
        } else {
            out = "{\"error\": \"Orden no encontrada\"}";
        }

        return Response.ok(out).build();
    }

    // GET /api/ordenes/getByEstado/reparacion
    @Path("getByEstado/{estado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEstado(@PathParam("estado") String estado) throws SQLException {
        OrdenController controller = new OrdenController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getByEstado(estado));
        return Response.ok(out).build();
    }

    // GET /api/ordenes/getByCliente/1
    @Path("getByCliente/{clienteId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCliente(@PathParam("clienteId") int clienteId) throws SQLException {
        OrdenController controller = new OrdenController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getByCliente(clienteId));
        return Response.ok(out).build();
    }

    // POST /api/ordenes/save
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Orden o) throws SQLException {
        OrdenController controller = new OrdenController();
        o = controller.create(o);
        String out;

        if (o.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(o);
        } else {
            out = "{\"error\": \"Error al crear orden\"}";
        }

        return Response.ok(out).build();
    }

    // PUT /api/ordenes/update/1
    @Path("update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Orden o) throws SQLException {
        OrdenController controller = new OrdenController();
        o = controller.update(id, o);
        String out;

        if (o.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(o);
        } else {
            out = "{\"error\": \"Error al actualizar orden\"}";
        }

        return Response.ok(out).build();
    }

    // DELETE /api/ordenes/delete/1
    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) throws SQLException {
        OrdenController controller = new OrdenController();
        boolean eliminado = controller.delete(id);
        String out;

        if (eliminado) {
            out = "{\"mensaje\": \"Orden eliminada correctamente\"}";
        } else {
            out = "{\"error\": \"Orden no encontrada\"}";
        }

        return Response.ok(out).build();
    }
}
