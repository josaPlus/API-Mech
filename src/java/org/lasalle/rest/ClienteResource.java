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
import org.lasalle.controller.ClienteController;
import org.lasalle.model.Cliente;

/**
 *
 * @author josaf
 */
@Path("clientes")
public class ClienteResource {

    // GET /api/clientes/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws SQLException {
        ClienteController controller = new ClienteController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getAll());
        return Response.ok(out).build();
    }

    // GET /api/clientes/getById/1
    @Path("getById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) throws SQLException {
        ClienteController controller = new ClienteController();
        Gson gson = new Gson();
        Cliente cliente = controller.getById(id);
        String out;

        if (cliente != null) {
            out = gson.toJson(cliente);
        } else {
            out = "{\"error\": \"Cliente no encontrado\"}";
        }

        return Response.ok(out).build();
    }

    // POST /api/clientes/save
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Cliente c) throws SQLException {
        ClienteController controller = new ClienteController();
        c = controller.create(c);
        String out;

        if (c.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(c);
        } else {
            out = "{\"error\": \"Error al insertar cliente\"}";
        }

        return Response.ok(out).build();
    }

    // PUT /api/clientes/update/1
    @Path("update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Cliente c) throws SQLException {
        ClienteController controller = new ClienteController();
        c = controller.update(id, c);
        String out;

        if (c.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(c);
        } else {
            out = "{\"error\": \"Error al actualizar cliente\"}";
        }

        return Response.ok(out).build();
    }

    // DELETE /api/clientes/delete/1
    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) throws SQLException {
        ClienteController controller = new ClienteController();
        boolean eliminado = controller.delete(id);
        String out;

        if (eliminado) {
            out = "{\"mensaje\": \"Cliente eliminado correctamente\"}";
        } else {
            out = "{\"error\": \"Cliente no encontrado\"}";
        }

        return Response.ok(out).build();
    }
}
