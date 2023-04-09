package com.timeline.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Timeline {

	private int id = 0;
	private Timestamp creationTime;
	private String name;
	private int creatorID;
	private String creatorName;
	private String startTime;
	private String endTime;
	private String keyword;
	private String description;
	private String date; // <- Timestamp.toString()
	private String backgroundImage;
	private double averageRating;
	private String ratingString;

	public Timeline() {

	}

	public Timeline(String name, int creator) {
		setTimeStamp(new Timestamp(System.currentTimeMillis()));
		setName(name);
		setCreatorID(creator);
	}

	public int getId() {
			return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getTimeStamp() {
		return creationTime;
	}

	public void setTimeStamp(Timestamp timestamp) {	this.creationTime = timestamp; }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creator) {
		this.creatorID = creator;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBackgroundImage(){
		return backgroundImage;
	}

	public void setBackgroundImage(String bgI){
		backgroundImage = bgI;
	}

	public double getAverageRating(){
		return averageRating;
	}

	public void setAverageRating(Double averageRating){
		this.averageRating =  Math.round(averageRating * 10.0) / 10.0;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public String getDate() {
		return getTimeStamp().toString();
	}

	public String getRatingString(Double AvergageRating){
		String rating = Double.toString(getAverageRating());
		return rating;
	}

}