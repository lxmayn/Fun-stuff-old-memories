package com.timeline;

import com.timeline.daos.TimelineDao;
import com.timeline.models.DBConnector;
import com.timeline.SceneHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;

public class App extends Application {

	public boolean adminStatus = false;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Connect to database
		DBConnector.connect();

		primaryStage.initStyle(StageStyle.UNDECORATED);
		SceneHandler sceneH = new SceneHandler();
		Scene scene = sceneH.getScene();
		primaryStage.setTitle("Timeline");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {

		launch(args);
	}
}
