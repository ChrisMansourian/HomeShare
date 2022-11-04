package com.bgs.homeshare.SQL;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private static Connection connection;

    public static Connection GetConnection() {
        if (connection == null) {
            //new Task().execute();
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
            //Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //ConnURL = "jdbc:jtds:sqlserver://homeshare1.database.windows.net:1433;database=homeshare;user=admin12345@homeshare1;password=root123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=require";//ssl=request;
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

    /*static class Task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Connection conn = null;
            String ConnURL = null;

            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnURL = "jdbc:jtds:sqlserver://homeshare1.database.windows.net:1433;database=homeshare;user=admin12345@homeshare1;password=root123!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;ssl=request;";
                conn = DriverManager.getConnection(ConnURL);
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

            connection = conn;
            return null;
        }
    }*/
}
