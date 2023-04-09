package com.timeline.daos;

import com.timeline.models.DBConnector;
import com.timeline.models.Event;
import com.timeline.models.Timeline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EventDao {
    Connection myCon = DBConnector.myCon;


    public boolean createEvent(String eventName, int idTimeline, String eventDesc, String startTime, String endTime, String imageName) {

        try {
            // Create prepared statement
            String query = "INSERT INTO event (eventname, idtimeline, description, startTime, endTime, imageName ) VALUES (?,?,?,?,?,?)";
            PreparedStatement createEvent = myCon.prepareStatement(query);

            // Set timeline properties
            createEvent.setString(1, eventName);
            createEvent.setInt(2, idTimeline);
            createEvent.setString(3, eventDesc);
            createEvent.setString(4, startTime);
            createEvent.setString(5, endTime);
            createEvent.setString(6, imageName);

            //Change .setString to whatever time type we decide to use (ex; .setInt)


            return createEvent.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean updateEvent(Event eventForUpdating) {
        try {
            //Create a prepared statement

            String query = "UPDATE event SET"
                    +" eventname ='" + eventForUpdating.getName()+"', "
                    +"startTime ='" + eventForUpdating.getStartTime()+"', "
                    +"endTime ='" + eventForUpdating.getEndTime()+"', "
                    +"description ='" + eventForUpdating.getDescription()+"', "
                    +"imageName ='" + eventForUpdating.getImageName()+"' "
                    + "WHERE idEvent=" + eventForUpdating.getId() + "; ";

            PreparedStatement updateEvent = myCon.prepareStatement(query);
            return updateEvent.executeUpdate()==1;        //Troubleshooting
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }
    public boolean deleteEventByName(String eventName) {

        try {
            //Create a prepared statement
            String query = "delete from event where eventname ='" + eventName + "'";
            PreparedStatement deleteEvent = myCon.prepareStatement(query);
            int result = deleteEvent.executeUpdate();
            if (result == 1) {
                System.out.println("Event " + eventName + " deleted!");
                return true;
            } else {
                System.out.println("Deletion failed with name: " + eventName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public boolean deleteEventByID(int id) {
        try {
            //Create a prepared statement
            String query = "delete from event where idEvent =" + id + "";
            PreparedStatement deleteEvent = myCon.prepareStatement(query);
            int result = deleteEvent.executeUpdate();
            if (result == 1) {
                System.out.println("Event " + id + " deleted!");
                return true;
            } else {
                System.out.println("Deletion failed with name: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
    public ArrayList<Event> getEventsByTimeline(int timelineId) {
        ArrayList<Event> events = new ArrayList<Event>();
        String query = "Select idEvent, eventname, startTime, endTime, description, imageName FROM event " +
                "where idtimeline=" + timelineId;
        PreparedStatement getTimelines = null;
        try {
            getTimelines = myCon.prepareStatement(query);
            //Collect result
            ResultSet result = getTimelines.executeQuery();

            // Create and add timelines to ArrayList
            while (result.next()) {
                Event event = new Event();
                event.setId(result.getInt(1));
                event.setName(result.getString(2));
                event.setStartTime(result.getString(3));
                event.setEndTime(result.getString(4));
                event.setDescription(result.getString(5));
                event.setImageName(result.getString(6));
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}
