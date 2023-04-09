package com.timeline.controllers;

import com.timeline.daos.TimelineDao;
import com.timeline.daos.UserDao;
import com.timeline.models.ActiveUser;
import com.timeline.models.Timeline;
import com.timeline.SceneHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SearchScreenController {

    @FXML
    public String timelineChoice;
    @FXML
    public String DateChoice;
    @FXML
    public String CreatorChoice;
    @FXML
    public javafx.scene.control.ChoiceBox ChoiceBox;
    public String All;
    @FXML
    public TextField searchBoxD;
    @FXML
    public TextField searchBoxC;
    @FXML
    public TextField searchBoxT;
    @FXML
    private Label pageHeader;
    @FXML
    private Label searchLabel;
    @FXML
    private TextField searchBox;
    @FXML
    private Button backButton;
    @FXML
    private TableView<Timeline> resultsTable;
    @FXML
    private TableColumn<Timeline, String> nameColumn;
    @FXML
    private TableColumn<Timeline, String> creatorColumn;
    @FXML
    private TableColumn<Timeline, String> dateColumn;

    private ObservableList<Timeline> dataList;
    private TimelineDao timelineConnect = new TimelineDao();

    @SuppressWarnings("unchecked")
    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

        dataList = FXCollections.observableArrayList(timelineConnect.getTimelines());
        dataList.addAll();

        FilteredList<Timeline> filteredData = new FilteredList<>(dataList, b -> true);

        searchBoxT.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(timelineConnect -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return timelineConnect.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });

        searchBoxT.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(timelineConnect -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return timelineConnect.getName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        searchBoxC.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(timelineConnect -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return timelineConnect.getCreatorName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        searchBoxD.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(timelineConnect -> {
                String lowerCaseFilter = newValue.toLowerCase();
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return timelineConnect.getTimeStamp().toString().contains(lowerCaseFilter);
            });
        });

        SortedList<Timeline> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(resultsTable.comparatorProperty());
        resultsTable.setItems(sortedData);
    }

    @FXML
    void backToTimeline(ActionEvent event1) throws Exception {

        if (ActiveUser.adminStatus) {
            SceneHandler sceneHandler = new SceneHandler();
            Scene MainScene;
            MainScene = sceneHandler.newScene("AdminPage.fxml", 525, 920);
            Stage window = (Stage) ((Node) event1.getSource()).getScene().getWindow();
            window.setTitle("TimeLine");
            window.setScene(MainScene);
            window.show();
        } else {
            SceneHandler sceneHandler = new SceneHandler();
            Scene MainScene;
            MainScene = sceneHandler.newScene("MainScene.fxml", 480, 840);
            Stage window = (Stage) ((Node) event1.getSource()).getScene().getWindow();
            window.setTitle("TimeLine");
            window.setScene(MainScene);
            window.show();
        }
    }
}
