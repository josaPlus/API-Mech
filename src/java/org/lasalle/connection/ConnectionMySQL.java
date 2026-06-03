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
        String user = getEnvOrDefault("DB_USER", "root");
        String password = getEnvOrDefault("DB_PASSWORD", "root");
        String db_name = getEnvOrDefault("DB_NAME", "MechDB");
        String host = getEnvOrDefault("DB_HOST", "localhost");
        String port = getEnvOrDefault("DB_PORT", "3306");
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
        String value = System.getenv(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
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
