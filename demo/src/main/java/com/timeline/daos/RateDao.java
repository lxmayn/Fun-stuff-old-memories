package com.timeline.daos;

import com.timeline.models.DBConnector;
import com.timeline.models.Rate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RateDao {
    Connection myCon = DBConnector.myCon;



    public boolean createRate(Rate rate) {
        try {
            String query = "INSERT INTO rate (userId, timelineId, rate) VALUES (?,?,?)";
            PreparedStatement createRate = myCon.prepareStatement(query);
            createRate.setInt(1, rate.getUserId());
            createRate.setInt(2, rate.getTimelineId());
            createRate.setInt(3, rate.getRate());

            System.out.println(createRate.toString());
            if (createRate.executeUpdate() == 1) {
                System.out.println("Rate saved successfully!");

                return true;
            } else {
                System.out.println("Rate couldn't saved.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }


    }

    public Rate getRateByTimelineIdAndUserId(int timelineId, int userId) {

        try {
            String query = "SELECT * from rate WHERE timelineId=? and userId=?";
            PreparedStatement getRate = myCon.prepareStatement(query);
            getRate.setInt(1, timelineId);
            getRate.setInt(2, userId);

            ResultSet resultSet = getRate.executeQuery();
            while (resultSet.next()) {
                Rate rate = new Rate();
                rate.setId(resultSet.getInt(1));
                rate.setTimelineId(resultSet.getInt(2));
                rate.setUserId(resultSet.getInt(3));
                rate.setRate(resultSet.getInt(4));
                return rate;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateRate(Rate rateForUpdating) {
        try {
            String query = "UPDATE rate SET"
                    + " rate = " + rateForUpdating.getRate()
                    + " WHERE idrate=" + rateForUpdating.getId() + "; ";
            PreparedStatement updateEvent = myCon.prepareStatement(query);
            System.out.println("Rate updated successfully!");

            return updateEvent.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

}
