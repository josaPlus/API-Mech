/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.lasalle.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author josaf
 */
public class ConnectionMySQL {

    Connection conn;

    public Connection open() {
        String user = "avnadmin";
        String password = "AVNS_f0y5wA0EJzJVExEo_WP";
        String db_name = "MechDB";
        String host = "mechdb-josafat061-a77d.l.aivencloud.com";
        String port = "12944";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db_name;
        String parametros = "?useSSL=true"
                + "&requireSSL=true"
                + "&verifyServerCertificate=false"
                + "&useUnicode=true"
                + "&characterEncoding=utf-8"
                + "&serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url + parametros, user, password);
            System.out.println("CONEXION OK");
            return conn;
        } catch (Exception e) {
            System.out.println("ERROR DE CONEXION: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static String getEnvOrDefault(String key, String defaultValue) {
        // Primero busca en variables de entorno
        String value = System.getenv(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        // Luego busca en propiedades del sistema (-D)
        value = System.getProperty(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }
        // Si no encuentra nada usa el default
        return defaultValue;
    }

    // crrrar conexion con la base de datos
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
