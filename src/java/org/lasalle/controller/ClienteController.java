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
import org.lasalle.model.Cliente;

/**
 *
 * @author josaf
 */
public class ClienteController {

    // ── Método fill: mapea ResultSet → Cliente ────────────────────────────────
    private Cliente fill(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setTelefono(rs.getString("telefono"));
        c.setCorreo(rs.getString("correo"));
        c.setCreatedAt(rs.getString("created_at"));
        return c;
    }

    // ── GET todos los clientes ────────────────────────────────────────────────
    public List<Cliente> getAll() throws SQLException {
        String sql = "SELECT * FROM clientes ORDER BY nombre";

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<Cliente> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // ── GET cliente por ID ────────────────────────────────────────────────────
    public Cliente getById(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        Cliente cliente = null;
        if (rs.next()) {
            cliente = fill(rs);
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return cliente;
    }

    // ── POST crear cliente ────────────────────────────────────────────────────
    public Cliente create(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes VALUES(0, ?, ?, ?, DEFAULT)";

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, c.getNombre());
        pstm.setString(2, c.getTelefono());
        pstm.setString(3, c.getCorreo());
        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) {
            c.setId(rs.getInt(1));
            System.out.println("Cliente creado con id: " + rs.getInt(1));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return c;
    }

    // ── PUT actualizar cliente ────────────────────────────────────────────────
    public Cliente update(int id, Cliente c) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, correo = ? WHERE id = ?";

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, c.getNombre());
        pstm.setString(2, c.getTelefono());
        pstm.setString(3, c.getCorreo());
        pstm.setInt(4, id);
        pstm.executeUpdate();

        pstm.close();
        connMySQL.close();
        conn.close();
        c.setId(id);
        return c;
    }

    // ── DELETE eliminar cliente ───────────────────────────────────────────────
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";

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
