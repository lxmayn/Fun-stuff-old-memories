package com.timeline.controllers;

import com.timeline.daos.UserDao;
import com.timeline.models.User;
import com.timeline.models.DBConnector;
import com.timeline.SceneHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserScreenController {

	@FXML
	private TableView<User> tableView;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn adminStatusCol;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn userNameCol;

	@FXML
	private Button loadDatafromDB;
	@FXML
	private Button bakk;

	// Initialize observable list to hold out database data
	private ObservableList<User> data;

	//private DBConnector dc = new DBConnector();
	private UserDao userCon = new UserDao();

	
	//When button is pressed
	@FXML
	void backToTimeline(ActionEvent event1) throws Exception {
		SceneHandler gege= new SceneHandler();
		Scene MainScene;
		MainScene = gege.newScene("AdminPage.fxml", 525, 920);
		Stage window = (Stage) ((Node) event1.getSource()).getScene().getWindow();
						window.setTitle("TimeLine");
						window.setScene(MainScene);
						window.show();
	}
	@FXML
	void toggleAdmin(ActionEvent event) {

		//Pre-selects the first item in table
		//tableView.getSelectionModel().select(0);
		//tableView.requestFocus();
		//tableView.getFocusModel().focus(0);

		//Gets the selected item in the table and sets the username
		String username = tableView.getSelectionModel().getSelectedItem().getUsername();
		int isAdmin;

		//If the selected user is not an admin, set admin status to 1(true)
		if (!tableView.getSelectionModel().getSelectedItem().isAdmin()) {

			isAdmin = 1;
			tableView.getSelectionModel().getSelectedItem().setAdmin(true);

		}
		//Else set the user admin status to 0(false)
		else {
			tableView.getSelectionModel().getSelectedItem().setAdmin(false);
			isAdmin = 0;
		}

		userCon.toggleAdmin(username, isAdmin);
		tableView.getColumns().clear();
		initialize();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize() {
		//Insert data into list
		data = FXCollections.observableArrayList(userCon.getUsers());

		//Creates new columns
		userNameCol = new TableColumn("Username");
		userNameCol.setMinWidth(100);
		userNameCol.setCellValueFactory(new PropertyValueFactory<User, SimpleStringProperty>("username"));

		adminStatusCol = new TableColumn("Admin status");
		adminStatusCol.setMinWidth(100);
		adminStatusCol.setCellValueFactory(new PropertyValueFactory<>("admin"));

		
		//Inserts columns into table
		tableView.getColumns().addAll(userNameCol, adminStatusCol);

		tableView.setItems(data);
	}

}
