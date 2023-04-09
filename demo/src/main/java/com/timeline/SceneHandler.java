package com.timeline;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Simple scene handling class
 * 
 * @author Alexander Niklasson
 *
 */
public class SceneHandler {

	private Scene scene;

	public SceneHandler() throws Exception {

		Parent root = FXMLLoader.load(getClass().getResource("views/FirstPage.fxml"));
		scene = new Scene(root);

	}

	public Scene getScene() {
		return scene;
	}

	// Method for changing scenes
	public Scene newScene(String fxml, int height, int width) throws Exception {
		Parent changeScene = FXMLLoader.load(getClass().getResource("views/" + fxml));

		return new Scene(changeScene, width, height);
	}

}
