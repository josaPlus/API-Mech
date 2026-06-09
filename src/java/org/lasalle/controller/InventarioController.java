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
import org.lasalle.model.Pieza;

/**
 *
 * @author josaf
 */
public class InventarioController {
 
    private Pieza fill(ResultSet rs) throws SQLException {
        Pieza p = new Pieza();
        p.setId(rs.getInt("id"));
        p.setNombre(rs.getString("nombre"));
        p.setCategoria(rs.getString("categoria"));
        p.setCodigo(rs.getString("codigo"));
        p.setPrecio(rs.getDouble("precio"));
        p.setStockActual(rs.getInt("stock_actual"));
        p.setStockMinimo(rs.getInt("stock_minimo"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setEstadoStock(rs.getString("estado_stock"));
        p.setUpdatedAt(rs.getString("updated_at"));
        return p;
    }
 
    // GET todo el inventario
    public List<Pieza> getAll() throws SQLException {
        String sql = """
            SELECT *,
              CASE
                WHEN stock_actual = 0            THEN 'sin_stock'
                WHEN stock_actual < stock_minimo THEN 'critico'
                WHEN stock_actual = stock_minimo THEN 'bajo'
                ELSE 'disponible'
              END AS estado_stock
            FROM inventario
            ORDER BY nombre
            """;
 
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
 
        List<Pieza> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }
 
        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }
 
    // GET pieza por ID
    public Pieza getById(int id) throws SQLException {
        String sql = """
            SELECT *,
              CASE
                WHEN stock_actual = 0            THEN 'sin_stock'
                WHEN stock_actual < stock_minimo THEN 'critico'
                WHEN stock_actual = stock_minimo THEN 'bajo'
                ELSE 'disponible'
              END AS estado_stock
            FROM inventario
            WHERE id = ?
            """;
 
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
 
        Pieza pieza = null;
        if (rs.next()) {
            pieza = fill(rs);
        }
 
        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return pieza;
    }
 
    // GET inventario por estado de stock
    // FIX: HAVING con alias calculado no funciona en MySQL sin subconsulta.
    // Se envuelve en subconsulta para poder filtrar por estado_stock correctamente.
    public List<Pieza> getByEstado(String estado) throws SQLException {
        String sql = """
            SELECT * FROM (
              SELECT *,
                CASE
                  WHEN stock_actual = 0            THEN 'sin_stock'
                  WHEN stock_actual < stock_minimo THEN 'critico'
                  WHEN stock_actual = stock_minimo THEN 'bajo'
                  ELSE 'disponible'
                END AS estado_stock
              FROM inventario
            ) AS sub
            WHERE sub.estado_stock = ?
            ORDER BY nombre
            """;
 
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, estado);
        ResultSet rs = pstm.executeQuery();
 
        List<Pieza> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(fill(rs));
        }
 
        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return lista;
    }
 
    // POST crear pieza
    public Pieza create(Pieza p) throws SQLException {
        String sql = "INSERT INTO inventario VALUES(0, ?, ?, ?, ?, ?, ?, ?, DEFAULT)";
 
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pstm.setString(1, p.getNombre());
        pstm.setString(2, p.getCategoria());
        pstm.setString(3, p.getCodigo());
        pstm.setDouble(4, p.getPrecio());
        pstm.setInt(5, p.getStockActual());
        pstm.setInt(6, p.getStockMinimo());
        pstm.setString(7, p.getDescripcion());
        pstm.executeUpdate();
 
        ResultSet rs = pstm.getGeneratedKeys();
        if (rs.next()) {
            p.setId(rs.getInt(1));
            System.out.println("Pieza creada con id: " + rs.getInt(1));
        }
 
        rs.close();
        pstm.close();
        connMySQL.close();
        conn.close();
        return p;
    }
 
    // PUT actualizar pieza
    public Pieza update(int id, Pieza p) throws SQLException {
        String sql = """
            UPDATE inventario
            SET nombre = ?, categoria = ?, codigo = ?,
                precio = ?, stock_actual = ?, stock_minimo = ?,
                descripcion = ?
            WHERE id = ?
            """;
 
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, p.getNombre());
        pstm.setString(2, p.getCategoria());
        pstm.setString(3, p.getCodigo());
        pstm.setDouble(4, p.getPrecio());
        pstm.setInt(5, p.getStockActual());
        pstm.setInt(6, p.getStockMinimo());
        pstm.setString(7, p.getDescripcion());
        pstm.setInt(8, id);
        pstm.executeUpdate();
 
        pstm.close();
        connMySQL.close();
        conn.close();
        p.setId(id);
        return p;
    }
 
    // DELETE eliminar pieza
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM inventario WHERE id = ?";
 
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