package com.timeline.daos;

import com.timeline.models.DBConnector;
import com.timeline.models.Timeline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class 	TimelineDao {
	Connection myCon = DBConnector.myCon;

	public int getTimelineIDByName(String name) {
		try {
			String query = "SELECT idtimeline FROM timelinedb.timeline WHERE name = '" + name + "'";
			PreparedStatement getTimelineID = myCon.prepareStatement(query);

			// Collect timeline ids
			ResultSet result = getTimelineID.executeQuery();

			// Return the timeline id
			if (result.next()) {
				return result.getInt(1);
			}
		} catch (SQLException e) {
			// System.out.println(e.getMessage());
		}
		// Return 0 if ID not found.
		return 0;

	}

	public boolean createTimeline(Timeline aTimeline) {

		try {
			// Create prepared statement
			String query = "INSERT INTO timeline (name, creationTime, iduser, startTime, endTime, keywords, description, backgroundImage) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement createTimeline = myCon.prepareStatement(query);

			// Set timeline properties
			createTimeline.setString(1, aTimeline.getName());
			createTimeline.setTimestamp(2, aTimeline.getTimeStamp());
			createTimeline.setInt(3, aTimeline.getCreatorID());
			createTimeline.setString(4, aTimeline.getStartTime());
			createTimeline.setString(5, aTimeline.getEndTime());
			createTimeline.setString(6, aTimeline.getKeyword());
			createTimeline.setString(7, aTimeline.getDescription());
			createTimeline.setString(8, aTimeline.getBackgroundImage());


			// Execute statement and return true if one row has been altered (added)
			return createTimeline.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	// copied from createTimeline method, currently doesn't work, needs adjustment
	public boolean editTimeline(Timeline et) {

		try {
			int id = et.getId();
			// Create prepared statement
			String query = "UPDATE timeline SET name = ?, startTime = ?, endTime = ?, keywords = ?, description = ?, backgroundImage = ?"
					+" WHERE timeline.idtimeline = '"+id+"'";
			// String query = "UPDATE timeline"
			PreparedStatement createTimeline = myCon.prepareStatement(query);

			// Set timeline properties
			createTimeline.setString(1, et.getName());
			// createTimeline.setTimestamp(2, eTimeline.getTimeStamp());
			// createTimeline.setInt(3, eTimeline.getCreatorID());
			createTimeline.setString(2, et.getStartTime());
			createTimeline.setString(3, et.getEndTime());
			createTimeline.setString(4, et.getKeyword());
			createTimeline.setString(5, et.getDescription());
			createTimeline.setString(6, et.getBackgroundImage());

			// Execute statement and return true if one row has been altered (added)
			return createTimeline.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public ArrayList<Timeline> getTimelines() {

		try {
			ArrayList<Timeline> timelines = new ArrayList<Timeline>();

			String query = "Select idtimeline, name, creationTime, timeline.iduser, startTime, endTime, keywords, description,backgroundImage, user.username FROM timeline "
					+ "JOIN user ON timeline.iduser = user.iduser";
			PreparedStatement getTimelines = myCon.prepareStatement(query);

			// Collect result
			ResultSet result = getTimelines.executeQuery();

			// Create and add timelines to ArrayList
			while (result.next()) {
				Timeline timeline = new Timeline();
				timeline.setId(result.getInt(1));
				timeline.setName(result.getString(2));
				timeline.setTimeStamp(result.getTimestamp(3));
				timeline.setCreatorID(result.getInt(4));
				timeline.setStartTime(result.getString(5));
				timeline.setEndTime(result.getString(6));
				timeline.setKeyword(result.getString(7));
				timeline.setDescription(result.getString(8));
				timeline.setBackgroundImage(result.getString(9));
				timeline.setCreatorName(result.getString(10));
				timeline.setAverageRating(getAverageRatingByTimelineId(result.getInt(1)));

				timelines.add(timeline);
			}
			return timelines;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean deleteTimelineByName(String name) {
		try {
			String query = "delete from timeline where name ='" + name + "'";
			PreparedStatement deleteTimeline = myCon.prepareStatement(query);
			int result = deleteTimeline.executeUpdate();
			if (result == 1) {
				System.out.println("Timeline " + name + " deleted!");
				return true;
			} else {
				System.out.println("Deletion failed with name: " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public Double getAverageRatingByTimelineId(int timelineId){
		try {

			//Get average rate from database
			String query = "Select AVG(rate) from rate where timelineId ='" + timelineId + "'";
			PreparedStatement getRating = myCon.prepareStatement(query);

			// Collect average
			ResultSet result = getRating.executeQuery();
			double average = 0.0;
			while(result.next()){
				average = result.getDouble(1);
			}

			return average;
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}

		return 0.0;
	}

}
