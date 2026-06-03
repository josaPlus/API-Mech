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
import org.lasalle.controller.MecanicoController;
import org.lasalle.model.Mecanico;

/**
 *
 * @author josaf
 */

@Path("mecanicos")
public class MecanicoResource {
    
    // GET /api/mecanicos/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws SQLException {
        MecanicoController controller = new MecanicoController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getAll());
        return Response.ok(out).build();
    }

    // GET /api/mecanicos/getById/1
    @Path("getById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) throws SQLException {
        MecanicoController controller = new MecanicoController();
        Gson gson = new Gson();
        Mecanico mecanico = controller.getById(id);
        String out;

        if (mecanico != null) {
            out = gson.toJson(mecanico);
        } else {
            out = "{\"error\": \"Mecánico no encontrado\"}";
        }

        return Response.ok(out).build();
    }

    // POST /api/mecanicos/login
    @Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Mecanico m) throws SQLException {
        MecanicoController controller = new MecanicoController();
        Mecanico result = controller.login(m.getCorreo(), m.getPasswordHash());
        String out;

        if (result != null) {
            Gson gson = new Gson();
            out = gson.toJson(result);
        } else {
            out = "{\"error\": \"Credenciales incorrectas\"}";
        }

        return Response.ok(out).build();
    }

    // POST /api/mecanicos/save
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Mecanico m) throws SQLException {
        MecanicoController controller = new MecanicoController();
        m = controller.create(m);
        String out;

        if (m.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(m);
        } else {
            out = "{\"error\": \"Error al crear mecánico\"}";
        }

        return Response.ok(out).build();
    }

    // PUT /api/mecanicos/update/1
    @Path("update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Mecanico m) throws SQLException {
        MecanicoController controller = new MecanicoController();
        m = controller.update(id, m);
        String out;

        if (m.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(m);
        } else {
            out = "{\"error\": \"Error al actualizar mecánico\"}";
        }

        return Response.ok(out).build();
    }

    // DELETE /api/mecanicos/delete/1
    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) throws SQLException {
        MecanicoController controller = new MecanicoController();
        boolean eliminado = controller.delete(id);
        String out;

        if (eliminado) {
            out = "{\"mensaje\": \"Mecánico eliminado correctamente\"}";
        } else {
            out = "{\"error\": \"Mecánico no encontrado\"}";
        }

        return Response.ok(out).build();
    }
}
