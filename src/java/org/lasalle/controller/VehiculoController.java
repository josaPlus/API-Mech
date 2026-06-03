/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.lasalle.connection.ConnectionMySQL;
import org.lasalle.model.Vehiculo;

/**
 *
 * @author josaf
 */
public class VehiculoController {

    private Vehiculo fill(ResultSet rs) throws SQLException {
        Vehiculo v = new Vehiculo();
        v.setId(rs.getInt("id"));
        v.setClienteId(rs.getInt("cliente_id"));
        v.setClienteNombre(rs.getString("cliente_nombre"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        v.setAnio(rs.getInt("anio"));
        v.setPlacas(rs.getString("placas"));
        v.setVin(rs.getString("vin"));
        v.setCreatedAt(rs.getString("created_at"));
        return v;
    }

    // GET todos los vehículos (con nombre del cliente)
    public List<Vehiculo> getAll() throws SQLException {
        String sql = """
            SELECT v.*, c.nombre AS cliente_nombre
            FROM vehiculos v
            JOIN clientes c ON v.cliente_id = c.id
            ORDER BY c.nombre
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<Vehiculo> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // GET vehículo por ID
    public Vehiculo getById(int id) throws SQLException {
        String sql = """
            SELECT v.*, c.nombre AS cliente_nombre
            FROM vehiculos v
            JOIN clientes c ON v.cliente_id = c.id
            WHERE v.id = ?
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        Vehiculo vehiculo = null;
        if (rs.next()) {
            vehiculo = fill(rs);
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return vehiculo;
    }

    // GET vehículos por cliente
    public List<Vehiculo> getByCliente(int clienteId) throws SQLException {
        String sql = """
            SELECT v.*, c.nombre AS cliente_nombre
            FROM vehiculos v
            JOIN clientes c ON v.cliente_id = c.id
            WHERE v.cliente_id = ?
            ORDER BY v.marca
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, clienteId);
        ResultSet rs = pstm.executeQuery();

        List<Vehiculo> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // POST crear vehículo
    public Vehiculo create(Vehiculo v) throws SQLException {
        String sql = "INSERT INTO vehiculos VALUES(0, ?, ?, ?, ?, ?, ?, DEFAULT)";

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstm.setInt(1, v.getClienteId());
        pstm.setString(2, v.getMarca());
        pstm.setString(3, v.getModelo());
        pstm.setInt(4, v.getAnio());
        pstm.setString(5, v.getPlacas());
        pstm.setString(6, v.getVin());
        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) {
            v.setId(rs.getInt(1));
            System.out.println("Vehículo creado con id: " + rs.getInt(1));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return v;
    }

    // PUT actualizar vehículo
    public Vehiculo update(int id, Vehiculo v) throws SQLException {
        String sql = """
            UPDATE vehiculos
            SET cliente_id = ?, marca = ?, modelo = ?, anio = ?, placas = ?, vin = ?
            WHERE id = ?
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, v.getClienteId());
        pstm.setString(2, v.getMarca());
        pstm.setString(3, v.getModelo());
        pstm.setInt(4, v.getAnio());
        pstm.setString(5, v.getPlacas());
        pstm.setString(6, v.getVin());
        pstm.setInt(7, id);
        pstm.executeUpdate();

        pstm.close();
        connMySQL.close();
        conn.close();
        v.setId(id);
        return v;
    }

    // DELETE eliminar vehículo
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM vehiculos WHERE id = ?";

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        int rows = pstm.executeUpdate();

        pstm.close();
        connMySQL.close();
        conn.close();
        return rows > 0;
    }
}
