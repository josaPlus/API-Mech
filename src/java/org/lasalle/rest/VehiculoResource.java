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
import org.lasalle.controller.VehiculoController;
import org.lasalle.model.Vehiculo;

/**
 *
 * @author josaf
 */
@Path("vehiculos")
public class VehiculoResource {
    // GET /api/vehiculos/getAll
    @Path("getAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws SQLException {
        VehiculoController controller = new VehiculoController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getAll());
        return Response.ok(out).build();
    }

    // GET /api/vehiculos/getById/1
    @Path("getById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) throws SQLException {
        VehiculoController controller = new VehiculoController();
        Gson gson = new Gson();
        Vehiculo vehiculo = controller.getById(id);
        String out;

        if (vehiculo != null) {
            out = gson.toJson(vehiculo);
        } else {
            out = "{\"error\": \"Vehículo no encontrado\"}";
        }

        return Response.ok(out).build();
    }

    // GET /api/vehiculos/getByCliente/1
    @Path("getByCliente/{clienteId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByCliente(@PathParam("clienteId") int clienteId) throws SQLException {
        VehiculoController controller = new VehiculoController();
        Gson gson = new Gson();
        String out = gson.toJson(controller.getByCliente(clienteId));
        return Response.ok(out).build();
    }

    // POST /api/vehiculos/save
    @Path("save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Vehiculo v) throws SQLException {
        VehiculoController controller = new VehiculoController();
        v = controller.create(v);
        String out;

        if (v.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(v);
        } else {
            out = "{\"error\": \"Error al insertar vehículo\"}";
        }

        return Response.ok(out).build();
    }

    // PUT /api/vehiculos/update/1
    @Path("update/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Vehiculo v) throws SQLException {
        VehiculoController controller = new VehiculoController();
        v = controller.update(id, v);
        String out;

        if (v.getId() != 0) {
            Gson gson = new Gson();
            out = gson.toJson(v);
        } else {
            out = "{\"error\": \"Error al actualizar vehículo\"}";
        }

        return Response.ok(out).build();
    }

    // DELETE /api/vehiculos/delete/1
    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) throws SQLException {
        VehiculoController controller = new VehiculoController();
        boolean eliminado = controller.delete(id);
        String out;

        if (eliminado) {
            out = "{\"mensaje\": \"Vehículo eliminado correctamente\"}";
        } else {
            out = "{\"error\": \"Vehículo no encontrado\"}";
        }

        return Response.ok(out).build();
    }
}
