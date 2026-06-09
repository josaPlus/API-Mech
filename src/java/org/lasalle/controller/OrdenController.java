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
import org.lasalle.model.Orden;

/**
 *
 * @author josaf
 */
public class OrdenController {
    
    private Orden fill(ResultSet rs) throws SQLException {
        Orden o = new Orden();
        o.setId(rs.getInt("id"));
        o.setClienteId(rs.getInt("cliente_id"));
        o.setClienteNombre(rs.getString("cliente_nombre"));
        o.setVehiculoId(rs.getInt("vehiculo_id"));
        o.setVehiculoDesc(rs.getString("vehiculo_desc"));
        o.setMecanicoId(rs.getInt("mecanico_id"));
        o.setMecanicoNombre(rs.getString("mecanico_nombre"));
        o.setEstado(rs.getString("estado"));
        o.setCostoManoObra(rs.getDouble("costo_mano_obra"));
        o.setCostoRefacciones(rs.getDouble("costo_refacciones"));
        o.setCostoTotal(rs.getDouble("costo_total"));
        o.setDescripcion(rs.getString("descripcion"));
        o.setCreatedAt(rs.getString("created_at"));
        o.setUpdatedAt(rs.getString("updated_at"));
        return o;
    }

    // GET todas las órdenes
    public List<Orden> getAll() throws SQLException {
        String sql = """
            SELECT o.*,
                   c.nombre AS cliente_nombre,
                   CONCAT(v.marca, ' ', v.modelo, ' ', v.anio) AS vehiculo_desc,
                   m.nombre AS mecanico_nombre
            FROM ordenes o
            JOIN clientes  c ON o.cliente_id  = c.id
            JOIN vehiculos v ON o.vehiculo_id = v.id
            LEFT JOIN mecanicos m ON o.mecanico_id = m.id
            ORDER BY o.created_at DESC
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<Orden> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // GET orden por ID
    public Orden getById(int id) throws SQLException {
        String sql = """
            SELECT o.*,
                   c.nombre AS cliente_nombre,
                   CONCAT(v.marca, ' ', v.modelo, ' ', v.anio) AS vehiculo_desc,
                   m.nombre AS mecanico_nombre
            FROM ordenes o
            JOIN clientes  c ON o.cliente_id  = c.id
            JOIN vehiculos v ON o.vehiculo_id = v.id
            LEFT JOIN mecanicos m ON o.mecanico_id = m.id
            WHERE o.id = ?
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        Orden orden = null;
        if (rs.next()) {
            orden = fill(rs);
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return orden;
    }

    // GET órdenes por estado
    public List<Orden> getByEstado(String estado) throws SQLException {
        String sql = """
            SELECT o.*,
                   c.nombre AS cliente_nombre,
                   CONCAT(v.marca, ' ', v.modelo, ' ', v.anio) AS vehiculo_desc,
                   m.nombre AS mecanico_nombre
            FROM ordenes o
            JOIN clientes  c ON o.cliente_id  = c.id
            JOIN vehiculos v ON o.vehiculo_id = v.id
            LEFT JOIN mecanicos m ON o.mecanico_id = m.id
            WHERE o.estado = ?
            ORDER BY o.created_at DESC
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, estado);
        ResultSet rs = pstm.executeQuery();

        List<Orden> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // GET órdenes por cliente
    public List<Orden> getByCliente(int clienteId) throws SQLException {
        String sql = """
            SELECT o.*,
                   c.nombre AS cliente_nombre,
                   CONCAT(v.marca, ' ', v.modelo, ' ', v.anio) AS vehiculo_desc,
                   m.nombre AS mecanico_nombre
            FROM ordenes o
            JOIN clientes  c ON o.cliente_id  = c.id
            JOIN vehiculos v ON o.vehiculo_id = v.id
            LEFT JOIN mecanicos m ON o.mecanico_id = m.id
            WHERE o.cliente_id = ?
            ORDER BY o.created_at DESC
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, clienteId);
        ResultSet rs = pstm.executeQuery();

        List<Orden> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // POST crear orden
    public Orden create(Orden o) throws SQLException {
    // ✅ 9 valores: 0 + 8 ?
    String sql = """
        INSERT INTO ordenes
        VALUES(0, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)
        """;

    // Calcular costo_total en Java
    double total = o.getCostoManoObra() + o.getCostoRefacciones();
    o.setCostoTotal(total);

    ConnectionMySQL connMySQL = new ConnectionMySQL();
    Connection conn = connMySQL.open();
    PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    pstm.setInt(1, o.getClienteId());
    pstm.setInt(2, o.getVehiculoId());
    pstm.setInt(3, o.getMecanicoId());
    pstm.setString(4, o.getEstado());
    pstm.setDouble(5, o.getCostoManoObra());
    pstm.setDouble(6, o.getCostoRefacciones());
    pstm.setDouble(7, total);              // ← costo_total
    pstm.setString(8, o.getDescripcion()); // ← descripcion
    pstm.executeUpdate();

    ResultSet rs = pstm.getGeneratedKeys();
    if (rs.next()) {
        o.setId(rs.getInt(1));
        System.out.println("Orden creada con id: " + rs.getInt(1));
    }

    rs.close();
    pstm.close();
    connMySQL.close();
    conn.close();
    return o;
}

    // PUT actualizar orden
    public Orden update(int id, Orden o) throws SQLException {
        String sql = """
            UPDATE ordenes
            SET cliente_id = ?, vehiculo_id = ?, mecanico_id = ?,
                estado = ?, costo_mano_obra = ?, costo_refacciones = ?,
                descripcion = ?
            WHERE id = ?
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, o.getClienteId());
        pstm.setInt(2, o.getVehiculoId());
        pstm.setInt(3, o.getMecanicoId());
        pstm.setString(4, o.getEstado());
        pstm.setDouble(5, o.getCostoManoObra());
        pstm.setDouble(6, o.getCostoRefacciones());
        pstm.setString(7, o.getDescripcion());
        pstm.setInt(8, id);
        pstm.executeUpdate();

        pstm.close();
        connMySQL.close();
        conn.close();
        o.setId(id);
        return o;
    }

    // DELETE eliminar orden
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM ordenes WHERE id = ?";

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
