package com.timeline.controllers;

import java.util.ArrayList;

import com.timeline.App;
import com.timeline.daos.TimelineDao;
import com.timeline.daos.UserDao;
import com.timeline.models.ActiveUser;
import com.timeline.models.DBConnector;
import com.timeline.models.Timeline;
import com.timeline.models.User;
import com.timeline.SceneHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainSceneController {
	@FXML
	private AnchorPane pane;
	@FXML
	private TextField textField;
	@FXML
	private Text confirmation;
	@FXML
	private TableView<Timeline> tableView;
	@FXML
	private Button search;

	// DBConnection
	private TimelineDao timelineCon = new TimelineDao();

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn timelineNameCol;

	private ArrayList<User> users;

	private ObservableList<Timeline> data;

	public String getText() {
		return textField.getText();
	}

	// WHY is this here?? This is the scene for Users, they shouldn't be able to
	// list other users. Putting this in comment for now.
	// @FXML
	// public void showUserList(Event e) throws Exception {
	// SceneHandler sh = new SceneHandler();
	// Scene userScene = sh.newScene("UserPage.fxml", 480, 375);
	// Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
	// window.setScene(userScene);
	// window.show();
	// }

	public void showSearchPage(ActionEvent event) throws Exception {
		SceneHandler sh = new SceneHandler();
		Scene userScene = sh.newScene("SearchPage.fxml", 640, 660);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();

	}

	@SuppressWarnings("unchecked")
	public void initialize() {

		data = FXCollections.observableArrayList(timelineCon.getTimelines());

		tableView.setPrefWidth(200);

		// Create new Column
		timelineNameCol.setText("Timelines");
		timelineNameCol.setPrefWidth(180);
		timelineNameCol.setCellValueFactory(new PropertyValueFactory<Timeline, SimpleStringProperty>("name"));

		// tableView.getColumns().add(timelineNameCol);

		tableView.setItems(data);
	}
}
