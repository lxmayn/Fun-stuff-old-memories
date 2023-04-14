package com.timeline.controllers;

import com.timeline.daos.TimelineDao;
import com.timeline.daos.UserDao;
import com.timeline.models.ActiveUser;
import com.timeline.models.DBConnector;
import com.timeline.models.User;
import com.timeline.SceneHandler;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller class for the FirstPage fxml document
 * 
 * @author Alexander Niklasson
 *
 */
public class FirstScreenController {

	// Variables for textfields
	@FXML
	private TextField userField;

	@FXML
	private PasswordField passField;

	@FXML
	private Label LoginConfirmation;

	@FXML
	private TextArea helpPrompt;

	private UserDao userCon = new UserDao();

	// When login button is clicked
	@FXML
	public void loginClicked(Event e) throws Exception {
		if (userField.getLength() == 0 && passField.getLength() == 0) {
			LoginConfirmation.setText("Please enter a username and password");
		} else if (userField.getLength() == 0) {
			LoginConfirmation.setText("Please enter a username.");
		} else if (passField.getLength() == 0) {
			LoginConfirmation.setText("Please enter a password.");
		} else {

			for (User i : userCon.getUsers()) {
				if (userField.getText().equals(i.getUsername())) {
					if (passField.getText().equals(i.getPassword())) {
						LoginConfirmation.setText("Logging in...");

						// Currently signed in user-tracker
						ActiveUser.user = i;
						ActiveUser.adminStatus = (i.isAdmin());

						SceneHandler SH = new SceneHandler();
						Scene MainScene;

						MainScene = SH.newScene("AdminPage.fxml", 525, 920);

						Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
						window.setTitle("TimeLine");
						window.setScene(MainScene);
						window.show();
						break;
					} else {
						LoginConfirmation.setText("Wrong password, try again.");
						break;
					}
				} else {
					LoginConfirmation.setText("No such user exists.");
				}
			}
		}
	};

	// When register button is clicked
	@FXML
	public void registerClicked(Event e) throws Exception {

		SceneHandler SH = new SceneHandler();
		Scene registerScene = SH.newScene("RegisterPage.fxml", 400, 700);
		// Creates a new stage for the new fxml
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(registerScene);
		window.show();

	}

	/*
	 * @FXML public void userClicked(Event e) throws Exception {
	 *
	 * SceneHandler sh = new SceneHandler(); Scene userScene =
	 * sh.newScene("UserPage.fxml",480,375);
	 *
	 * Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
	 * window.setScene(userScene); window.show();
	 *
	 *
	 * }
	 */
	@FXML
	public void exitApp(MouseEvent e) {
		System.exit(1);
	}

	public FirstScreenController() {
	}

	// Initialize stuff here...
	public void initialize() {
	}

	@FXML
	public void enterPressed(KeyEvent e) throws Exception {

		if (e.getCode() == KeyCode.ENTER) {

			if (userField.getLength() == 0 && passField.getLength() == 0) {
				LoginConfirmation.setText("Please enter a username and password");
			} else if (userField.getLength() == 0) {
				LoginConfirmation.setText("Please enter a username.");
			} else if (passField.getLength() == 0) {
				LoginConfirmation.setText("Please enter a password.");
			} else {

				for (User i : userCon.getUsers()) {
					if (userField.getText().equals(i.getUsername())) {
						if (passField.getText().equals(i.getPassword())) {
							LoginConfirmation.setText("Logging in...");

							// Currently signed in user-tracker
							ActiveUser.user = i;
							ActiveUser.adminStatus = (i.isAdmin());

							SceneHandler SH = new SceneHandler();
							Scene MainScene;

							MainScene = SH.newScene("AdminPage.fxml", 525, 920);

							Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
							window.setTitle("TimeLine");
							window.setScene(MainScene);
							window.show();
							break;
						} else {
							LoginConfirmation.setText("Wrong password, try again.");
							break;
						}
					} else {
						LoginConfirmation.setText("No such user exists.");
					}
				}
			}
		}
	};
}
