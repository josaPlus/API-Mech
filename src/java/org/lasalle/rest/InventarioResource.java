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
import org.lasalle.controller.InventarioController;
import org.lasalle.model.Pieza;

/**
 *
 * @author josaf
 */

@Path("inventario")
public class InventarioResource {
    
    // GET /api/inventario/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws SQLException {
        InventarioController controller = new InventarioController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getAll());
        return Response.ok(out).build();
    }

    // GET /api/inventario/getById/1
    @Path("getById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) throws SQLException {
        InventarioController controller = new InventarioController();
        Gson gson = new Gson();
        Pieza pieza = controller.getById(id);
        String out;

        if (pieza != null) {
            out = gson.toJson(pieza);
        } else {
            out = "{\"error\": \"Pieza no encontrada\"}";
        }

        return Response.ok(out).build();
    }

    // GET /api/inventario/getByEstado/critico
    @Path("getByEstado/{estado}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByEstado(@PathParam("estado") String estado) throws SQLException {
        InventarioController controller = new InventarioController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getByEstado(estado));
        return Response.ok(out).build();
    }

    // POST /api/inventario/save
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Pieza p) throws SQLException {
        InventarioController controller = new InventarioController();
        p = controller.create(p);
        String out;

        if (p.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(p);
        } else {
            out = "{\"error\": \"Error al crear pieza\"}";
        }

        return Response.ok(out).build();
    }

    // PUT /api/inventario/update/1
    @Path("update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Pieza p) throws SQLException {
        InventarioController controller = new InventarioController();
        p = controller.update(id, p);
        String out;

        if (p.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(p);
        } else {
            out = "{\"error\": \"Error al actualizar pieza\"}";
        }

        return Response.ok(out).build();
    }

    // DELETE /api/inventario/delete/1
    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) throws SQLException {
        InventarioController controller = new InventarioController();
        boolean eliminado = controller.delete(id);
        String out;

        if (eliminado) {
            out = "{\"mensaje\": \"Pieza eliminada correctamente\"}";
        } else {
            out = "{\"error\": \"Pieza no encontrada\"}";
        }

        return Response.ok(out).build();
    }
}
