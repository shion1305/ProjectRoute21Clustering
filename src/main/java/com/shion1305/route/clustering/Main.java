package com.shion1305.route.clustering;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:" + new File("database/database.db").getAbsolutePath();
        Connection conn;
        try{
            conn= DriverManager.getConnection(url);
            System.out.println("connection has been established");
            Statement statement=conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS xpot_data (\n" +
                    " id integer PRIMARY KEY,\n" +
                    "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
