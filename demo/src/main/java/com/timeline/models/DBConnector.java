package com.timeline.models;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
    public static Connection myCon = null;
    private static String url = "jdbc:mysql://localhost/timelinedb?serverTimezone=UTC";
    private static String user = "root";
    private static String password = "123123";

    public static boolean connect() {

        System.out.println("\nConnecting to database..");
        try {
            // Connect
            myCon = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

}