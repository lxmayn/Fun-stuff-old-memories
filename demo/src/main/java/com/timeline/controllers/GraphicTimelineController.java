package com.timeline.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.image.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphicTimelineController extends AnchorPane {
	@FXML
	private ImageView imageV;
	@FXML
	private Label TimelineName;
	@FXML
	private Label TimelineKeywords;
	@FXML
	private Label TimelineDescription;
	@FXML
	private Label TimelineDate;
	@FXML
	private Label Counter;
	@FXML
	private ChoiceBox rateBox;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Pane starParent;
	@FXML
	private SVGPath star1;
	@FXML
	private SVGPath star2;
	@FXML
	private SVGPath star3;
	@FXML
	private SVGPath star4;
	@FXML
	private SVGPath star5;

	private double rating;
	ObservableList<Integer> rateList = FXCollections.observableArrayList(1, 2, 3, 4, 5);

	public GraphicTimelineController() {
		FXMLLoader fxmlLoader = new FXMLLoader();

		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		try {
			FileInputStream fileInputStream = new FileInputStream(
					new File("src/main/java/com/timeline/views/GraphicalTimeline.fxml"));
			fxmlLoader.load(fileInputStream);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void initialize() {

		if (rating == 0) {
			starParent.setVisible(false);
		} else if (rating > 0) {
			starParent.setVisible(true);
		}

		if ((int) rating == 1) {
			star1.setStyle("-fx-fill:black;");
		} else if ((int) rating == 2) {
			star1.setStyle("-fx-fill:black;");
			star2.setStyle("-fx-fill:black;");
		} else if ((int) rating == 3) {
			star1.setStyle("-fx-fill:black;");
			star2.setStyle("-fx-fill:black;");
			star3.setStyle("-fx-fill:black;");
		} else if ((int) rating == 4) {
			star1.setStyle("-fx-fill:black;");
			star2.setStyle("-fx-fill:black;");
			star3.setStyle("-fx-fill:black;");
			star4.setStyle("-fx-fill:black;");
		} else if ((int) rating == 5) {
			star1.setStyle("-fx-fill:black;");
			star2.setStyle("-fx-fill:black;");
			star3.setStyle("-fx-fill:black;");
			star4.setStyle("-fx-fill:black;");
			star5.setStyle("-fx-fill:black;");
		}
	}

	public void setImage(String imageName) {
		File file = new File(imageName);
		Image image = new Image(file.toURI().toString());
		this.imageV.setImage(image);
	}

	public void setRating(double rating) {
		this.rating = rating;
		rating = (int) Math.ceil(rating);
	}

	public void setTimelineTitle(String newName) {
		this.TimelineName.setText(newName);
	}

	public void setTimelineKeywords(String timelineKeywords) {
		this.TimelineKeywords.setText(timelineKeywords);
	}

	public void setTimelineDesc(String timelineDesc) {
		this.TimelineDescription.setText(timelineDesc);
	}

	public void setTimelineDate(String creationDate) {
		this.TimelineDate.setText(creationDate);
	}

	public void setCounter(int num) {
		this.Counter.setText(Integer.toString(num));
	}

	public void initializeChoiceBox() {
		rateBox.setItems(rateList);
	}

	public ChoiceBox getRateBox() {
		return rateBox;
	}

}
