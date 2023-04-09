package com.timeline.daos;

import com.timeline.models.DBConnector;
import com.timeline.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    Connection myCon = DBConnector.myCon;

    public boolean createUser(User aUser) {

        try {
            // Create prepared statement
            String query = "INSERT INTO user (username, password) VALUES(?,?)";
            PreparedStatement createUser = myCon.prepareStatement(query);

            // Set username and password, admin is set to false(0) by default
            createUser.setString(1, aUser.getUsername());
            createUser.setString(2, aUser.getPassword());

            // Execute statement and return true if one row has been altered (added)
            return createUser.executeUpdate() == 1;

        } catch (SQLException e) {
            return false;
        }

    }

    public int getUserID(String username) {
        try {
            String query = "SELECT iduser FROM user WHERE username = '" + username + "'";
            PreparedStatement getUserID = myCon.prepareStatement(query);
            // Collect user
            ResultSet result = getUserID.executeQuery();
            // Return the user id
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // Return 0 if ID not found.
        return 0;
    }

    public ArrayList<User> getUsers() {

        try {
            ArrayList<User> users = new ArrayList<>();

            String query = "SELECT username, password, admin FROM user";
            PreparedStatement getUsers = myCon.prepareStatement(query);

            // Collect result
            ResultSet result = getUsers.executeQuery();

            // Create and add users to ArrayList
            while (result.next()) {
                users.add(new User(result.getString(1), result.getString(2), result.getInt(3)));

            }

            return users;
        } catch (SQLException e) {
            e.getMessage();
            System.exit(-1);
        }

        return null;

    }

    // (Un)set admin. 1 = admin 0 = user
    public boolean toggleAdmin(String username, int isAdmin) {

        try {
            String query = "UPDATE user SET admin = '" + isAdmin + "' WHERE " +
                    "iduser>0 AND iduser = (SELECT iduser WHERE username = '"
                    + username + "')";
            PreparedStatement toggleAdmin = myCon.prepareStatement(query);

            return toggleAdmin.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
