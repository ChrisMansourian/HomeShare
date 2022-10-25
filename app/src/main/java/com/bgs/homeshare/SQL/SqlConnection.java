package com.bgs.homeshare.SQL;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private static String port = "1433";
    //private static String className = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "homeshare";
    private static String username = "admin12345@homeshare1";
    private static String password = "root123!";

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
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            ConnURL = "jdbc:jtds:sqlserver://homeshare1.database.windows.net:1433;"
                    + "databaseName=" + db + ";user=" + username + ";password="
                    + password + ";";
            ConnURL = "jdbc:sqlserver://homeshare1.database.windows.net:1433;database=homeshare;user=admin12345@homeshare1;password=root123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        connection = conn;
    }
}
