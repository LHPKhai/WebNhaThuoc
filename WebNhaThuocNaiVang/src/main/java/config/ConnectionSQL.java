package config;

import java.sql.*;

public class ConnectionSQL {
    private static String url = "jdbc:mysql://localhost:3306/webshop";
    private static String user = "root";
    private static String password = "";
    public static Connection getConnection() {
    	Connection connection = null;
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	connection = DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(connection==null) System.out.println("Connect failed!");

        return connection;
    }

}
