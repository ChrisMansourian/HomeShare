package com.bgs.homeshare.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private static Connection connection;

    public static Connection GetConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    private static void connect() {
        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ConnURL = "jdbc:sqlserver://homeshare1.database.windows.net:1433;database=homeshare;user=admin12345@homeshare1;password=root123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(ConnURL);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        connection = conn;
    }
}
