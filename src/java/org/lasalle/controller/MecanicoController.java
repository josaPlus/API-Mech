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
import org.lasalle.model.Mecanico;

/**
 *
 * @author josaf
 */
public class MecanicoController {
    
    private Mecanico fill(ResultSet rs) throws SQLException {
        Mecanico m = new Mecanico();
        m.setId(rs.getInt("id"));
        m.setNombre(rs.getString("nombre"));
        m.setCorreo(rs.getString("correo"));
        m.setTelefono(rs.getString("telefono"));
        m.setCargo(rs.getString("cargo"));
        m.setEmpleadoNum(rs.getString("empleado_num"));
        m.setEstado(rs.getString("estado"));
        m.setFechaIngreso(rs.getString("fecha_ingreso"));
        m.setCreatedAt(rs.getString("created_at"));
        return m;
    }

    // GET todos los mecánicos (sin password)
    public List<Mecanico> getAll() throws SQLException {
        String sql = """
            SELECT id, nombre, correo, telefono, cargo,
                   empleado_num, estado, fecha_ingreso, created_at
            FROM mecanicos
            ORDER BY nombre
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        List<Mecanico> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }

    // GET mecánico por ID (sin password)
    public Mecanico getById(int id) throws SQLException {
        String sql = """
            SELECT id, nombre, correo, telefono, cargo,
                   empleado_num, estado, fecha_ingreso, created_at
            FROM mecanicos
            WHERE id = ?
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();

        Mecanico mecanico = null;
        if (rs.next()) {
            mecanico = fill(rs);
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return mecanico;
    }

    // POST login
    public Mecanico login(String correo, String password) throws SQLException {
        String sql = """
            SELECT id, nombre, correo, telefono, cargo,
                   empleado_num, estado, fecha_ingreso, created_at
            FROM mecanicos
            WHERE correo = ? AND password_hash = ? AND estado = 'activo'
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, correo);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();

        Mecanico mecanico = null;
        if (rs.next()) {
            mecanico = fill(rs);
            System.out.println("Login exitoso: " + mecanico.getNombre());
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return mecanico;
    }

    // POST crear mecánico
    public Mecanico create(Mecanico m) throws SQLException {
        String sql = """
            INSERT INTO mecanicos
            VALUES(0, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, m.getNombre());
        pstm.setString(2, m.getCorreo());
        pstm.setString(3, m.getTelefono());
        pstm.setString(4, m.getCargo());
        pstm.setString(5, m.getPasswordHash());
        pstm.setString(6, m.getEmpleadoNum());
        pstm.setString(7, m.getEstado() != null ? m.getEstado() : "activo");
        pstm.setString(8, m.getFechaIngreso());
        pstm.executeUpdate();

        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) {
            m.setId(rs.getInt(1));
            System.out.println("Mecánico creado con id: " + rs.getInt(1));
        }

        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return m;
    }

    // PUT actualizar mecánico
    public Mecanico update(int id, Mecanico m) throws SQLException {
        String sql = """
            UPDATE mecanicos
            SET nombre = ?, correo = ?, telefono = ?,
                cargo = ?, empleado_num = ?, estado = ?, fecha_ingreso = ?
            WHERE id = ?
            """;

        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, m.getNombre());
        pstm.setString(2, m.getCorreo());
        pstm.setString(3, m.getTelefono());
        pstm.setString(4, m.getCargo());
        pstm.setString(5, m.getEmpleadoNum());
        pstm.setString(6, m.getEstado());
        pstm.setString(7, m.getFechaIngreso());
        pstm.setInt(8, id);
        pstm.executeUpdate();

        pstm.close();
        connMySQL.close();
        conn.close();
        m.setId(id);
        return m;
    }

    // DELETE eliminar mecánico
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM mecanicos WHERE id = ?";

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
