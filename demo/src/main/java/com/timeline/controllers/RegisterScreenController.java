package com.timeline.controllers;

import com.timeline.daos.UserDao;
import com.timeline.models.User;
import com.timeline.SceneHandler;
import com.timeline.models.DBConnector;
import com.timeline.models.PasswordCheck;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller class for RegisterPage fxml document
 * 
 * @author Alexander Niklasson
 *
 */
public class RegisterScreenController {

	// Variables for elements in fxml
	@FXML
	private TextField userField;

	@FXML
	private PasswordField passField;

	@FXML
	private PasswordField confirmField;

	@FXML
	private Text SignUpConfirmation;

	@FXML
	private TextArea helpPrompt;

	// DBConnection
	private UserDao userCon = new UserDao();

	// Checks if the sign up button is pressed
	@FXML
	public void signupClicked(ActionEvent e) throws Exception {

		PasswordCheck pass = new PasswordCheck();
		String username = userField.getText();
		String password = passField.getText();

		// Check if a username and password has been input and if password is legal
		if (username.length() == 0 && password.length() == 0) {
			helpPrompt.setText("Please enter a username and password");
			System.out.println("This is a test");
		} else if (username.length() == 0) {
			helpPrompt.setText("Please enter a username.");

		} else if (password.length() == 0) {
			helpPrompt.setText("Please enter a password.");

		} else if (!confirmField.getText().equals(password)) {
			helpPrompt.setText("Please enter the same password in both fields.");

		} else if (!pass.passwordCheck(password)) {
			initialize();
		}

		// else register user
		else {

			// Checks if username already exists in the database, otherwise save the user
			// credentials
			if (!userCon.createUser(new User(username, password, 0))) {
				SignUpConfirmation.setText("Username already exists!");
			} else {
				SignUpConfirmation.setText("Successfully Registered");

			}
		}
	}

	@FXML
	public void mouseEntered(MouseEvent e) {
		helpPrompt.setVisible(true);
		FadeTransition ft = new FadeTransition(Duration.millis(700), helpPrompt);
		ft.setFromValue(0);
		ft.setToValue(0.85);
		ft.play();
	}

	@FXML
	public void mouseExited(MouseEvent e) {
		helpPrompt.setVisible(false);
	}

	@FXML
	public void goBack(MouseEvent e) throws Exception {
		SceneHandler SH = new SceneHandler();
		Scene firstScene = SH.newScene("FirstPage.fxml", 400, 700);
		// Creates a new stage for the new fxml
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(firstScene);
		window.show();
	}

	@FXML
	public void exitApp(MouseEvent e) {
		System.exit(1);
	}

	public void initialize() {
		helpPrompt.setText("Your password must contain at least one uppercase letter,"
				+ " one lowercase letter and one number.");
	}
}
