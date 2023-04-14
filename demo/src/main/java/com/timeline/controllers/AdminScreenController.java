package com.timeline.controllers;

import com.timeline.daos.EventDao;
import com.timeline.daos.RateDao;
import com.timeline.daos.TimelineDao;
import com.timeline.daos.UserDao;
import com.timeline.models.*;
import com.timeline.SceneHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

import java.util.Optional;

public class AdminScreenController {

	// Tabs
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab eventTab;
	@FXML
	private Tab createTab;
	@FXML
	private Tab timelineTab;

	@FXML
	private Label userStatusLbl;

	@FXML
	private TableView<Timeline> tableView;
	@FXML
	private Label closeScreen;
	@FXML
	private Button userList;
	@FXML
	private Button create;
	@FXML
	private Button search;
	@FXML
	private TextField timelineName;
	@FXML
	private Text TimelineConfirmation;
	@FXML
	private TableView<Timeline> tableView1;
	@FXML
	private TableView<Event> tableViewEvent;
	@FXML
	private TableColumn eventNameCol1;
	@FXML
	private TableColumn eventStartDateCol2;
	@FXML
	private TableColumn eventEndDateCol3;
	@FXML
	private TableColumn eventDescCol4;
	@FXML
	private Button createEventButton;
	@FXML
	private Button updateEventButton;
	@FXML
	private Button newEventButton;
	@FXML
	private Button deleteEventButton;
	@FXML
	private Label goBackToRoot;
	@FXML
	private AnchorPane editRootPane;

	// Timeline tab
	@FXML
	private TextField timeLineName;
	@FXML
	private TextField timeLineKey;
	@FXML
	private TextField timeLineStart;
	@FXML
	private TextField timeLineEnd;
	@FXML
	private TextField timeLineDesc;
	@FXML
	private TextField timeLineBGI;
	@FXML
	private DatePicker timeLineSPick;
	@FXML
	private DatePicker timeLineEPick;
	@FXML
	private Text editErrorMessage;
	@FXML
	public ImageView showBG;
	@FXML
	public Label closeBG;

	// Event tab
	@FXML
	private TextField setEvent;
	@FXML
	private TextField addEventChange;
	@FXML
	private TextField addEventDesc;
	@FXML
	private TextField addEventStart;
	@FXML
	private TextField addEventEnd;
	@FXML
	private DatePicker eventDateStart;
	@FXML
	private DatePicker eventDateEnd;
	@FXML
	private String imageName;
	@FXML
	private ImageView show;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn timelineNameCol1;

	// Create tab
	@FXML
	public Label closeImageC;
	@FXML
	private TextField datePickTextField2;
	@FXML
	private TextField datePickTextField1;
	@FXML
	private DatePicker datePick1;
	@FXML
	private DatePicker datePick2;
	@FXML
	private CheckBox otherDatesCheckBox;
	@FXML
	private TextField descriptionTxtBox;
	@FXML
	private TextField keywordsTxtBox;
	@FXML
	private Text TimelineErrorMessage;
	@FXML
	private Label timelineImageName;
	@FXML
	private Button timelineImageSelect;
	@FXML
	public ImageView showBGT;

	// Search tab
	@FXML
	private TextField searchBox;
	@FXML
	private TableColumn<Timeline, Double> ratingColumn;
	@FXML
	private TableView<Timeline> resultsTable;
	@FXML
	private TableColumn<Timeline, String> nameColumn;
	@FXML
	private TableColumn<Timeline, String> creatorColumn;
	@FXML
	private TableColumn<Timeline, Timestamp> dateColumn;
	@FXML
	public Label searchLabel;
	@FXML
	public TextField searchBoxD;
	@FXML
	public TextField searchBoxC;
	@FXML
	public TextField searchBoxT;
	@FXML
	public TextField searchBoxR;
	@FXML
	private ChoiceBox choiceBox;

	private ObservableList<Timeline> dataList;

	@FXML
	private Button addBackground;
	@FXML
	private Button removeBackground;

	@FXML
	private VBox graphicTimeline;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableColumn timelineNameCol;

	// private DBConnector dc = new DBConnector();

	// DBConnection
	private TimelineDao timelineCon = new TimelineDao();
	private UserDao userCon = new UserDao();
	private EventDao eventCon = new EventDao();
	private RateDao rateCon = new RateDao();

	@SuppressWarnings("unchecked")
	public void initialize() {

		if (!ActiveUser.adminStatus) {
			tabPane.getTabs().removeAll(createTab, timelineTab, eventTab);
			userList.setVisible(false);
			userStatusLbl.setText("User");
		} else {
			userStatusLbl.setText("Admin");
			userList.setVisible(true);
		}

		tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, tab) -> {

			if (tab.getText().equals("Create")) {
				refreshTableViews();
			} else if (tab.getText().equals("Events")) {
				refreshTableViews();
			} else if (tab.getText().equals("Main")) {
				refreshTableViews();
			} else if (tab.getText().equals("Timelines")) {
				refreshTableViews();
			} else if (tab.getText().equals("Search for Timeline")) {
				refreshTableViews();
			}
		});

		tableView.setPrefWidth(200);

		// Create new Column
		timelineNameCol.setText("Timelines");
		timelineNameCol.setPrefWidth(180);
		timelineNameCol.setCellValueFactory(new PropertyValueFactory<Timeline, SimpleStringProperty>("name"));

		timelineNameCol1.setText("Timelines");
		timelineNameCol1.setPrefWidth(150);
		timelineNameCol1.setCellValueFactory(new PropertyValueFactory<Timeline, SimpleStringProperty>("name"));

		// Search
		ratingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

		// init events table columns
		eventNameCol1.setCellValueFactory(new PropertyValueFactory<Event, SimpleStringProperty>("name"));
		eventStartDateCol2.setCellValueFactory(new PropertyValueFactory<Event, SimpleStringProperty>("startTime"));
		eventEndDateCol3.setCellValueFactory(new PropertyValueFactory<Event, SimpleStringProperty>("endTime"));
		eventDescCol4.setCellValueFactory(new PropertyValueFactory<Event, SimpleStringProperty>("description"));

		updateEventButton.setVisible(false);
		newEventButton.setVisible(false);
		deleteEventButton.setVisible(false);
		closeImageC.setVisible(false);

	}

	@FXML
	void showUserList(ActionEvent event) throws Exception {
		SceneHandler sh = new SceneHandler();
		Scene userScene = sh.newScene("UserPage.fxml", 480, 375);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}

	@FXML
	public void createTimeline(ActionEvent actionEvent) {
		String name = timelineName.getText();
		Timeline timeline = new Timeline();

		// Check if timeline name is legal
		if (name.length() == 0) {
			TimelineErrorMessage.setText("Please enter a valid name");
			System.out.println("Please enter a valid name");
			return;
		}
		timeline.setName(name);
		if (!otherDatesCheckBox.isSelected()) {
			LocalDate startTime = datePick1.getValue();
			if (startTime == null) {
				TimelineErrorMessage.setText("Please select a valid start date");
				System.out.println("Please select a valid start date");
				return;
			}
			LocalDate endTime = datePick2.getValue();
			if (endTime == null) {
				TimelineErrorMessage.setText("Please select a valid end date");
				System.out.println("Please select a valid end date");
				return;
			}
			if (!startTime.isBefore(endTime)) {
				TimelineErrorMessage.setText("End date should be after start date!");
				System.out.println("End date should be after start date!");
				return;
			}
			timeline.setStartTime(startTime.toString());
			timeline.setEndTime(endTime.toString());
		} else {
			String startTime = datePickTextField1.getText();
			if (startTime.isEmpty()) {
				TimelineErrorMessage.setText("Start date can not be empty!");
				System.out.println("Start date can not be empty!");
				return;
			}
			String endTime = datePickTextField2.getText();
			if (endTime.isEmpty()) {
				TimelineErrorMessage.setText("End date can not be empty!");
				System.out.println("End date can not be empty!");
				return;
			}
			timeline.setStartTime(startTime);
			timeline.setEndTime(endTime);
		}
		String keywords = keywordsTxtBox.getText();
		timeline.setKeyword(keywords);
		String description = descriptionTxtBox.getText();
		if (description.length() > 1000) {
			TimelineErrorMessage.setText("Description must be less than 1000 characters");
			System.out.println("Description must be less than 1000 characters");
			return;
		}
		timeline.setDescription(description);
		if (timelineCon.getTimelineIDByName(name) != 0) {
			TimelineErrorMessage.setText("Timeline already exists!");
			System.out.println("Timeline already exists!");
			return;
		}
		timeline.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		timeline.setCreatorID(userCon.getUserID(ActiveUser.user.getUsername()));
		timeline.setCreatorName(ActiveUser.user.getUsername());
		if (timelineImageName.getText().equals("No image selected")) {
			timeline.setBackgroundImage("");
		} else {
			timeline.setBackgroundImage(timelineImageName.getText());
		}

		// Checks if timeline name already exists in the database, otherwise save the
		// timeline, creator ID is 1 by default
		if (timelineCon.createTimeline(timeline)) {
			TimelineErrorMessage.setText("");
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Timeline successfully created!");
			alert.showAndWait();
			System.out.println("Timeline successfully created");
			refreshTableViews();
		} else {
			TimelineErrorMessage.setText("A database error has occurred!");
		}

		showBGT.setImage(null);
		closeImageC.setVisible(false);
		timelineImageName.setText("No image selected");
		timelineName.setText(null);
		datePickTextField1.setText(null);
		datePickTextField2.setText(null);
		keywordsTxtBox.setText(null);
		descriptionTxtBox.setText(null);
	}

	// Edit a timeline
	private Timeline editTimeline;

	/*
	 * Insert initial data from selected timeline to TextFields
	 */
	@FXML
	public void onTableItemSelect() {
		editTimeline = tableView.getSelectionModel().getSelectedItem();
		editTimeline.setId(timelineCon.getTimelineIDByName(editTimeline.getName()));
		if (editTimeline != null) {
			timeLineName.setText(editTimeline.getName());
			timeLineStart.setText(editTimeline.getStartTime());
			timeLineEnd.setText(editTimeline.getEndTime());
			timeLineKey.setText(editTimeline.getKeyword());
			timeLineDesc.setText(editTimeline.getDescription());
			timeLineBGI.setText(editTimeline.getBackgroundImage());
		}
	}

	@FXML
	public void saveEditPressed(ActionEvent event) {

		// If no timeline has been chosen
		if (editTimeline == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("No timeline selected");
			alert.setHeaderText(null);
			alert.setContentText("Please select a timeline to edit");
			alert.showAndWait();
			return;
		}

		// Set data
		editTimeline.setName(timeLineName.getText());
		editTimeline.setStartTime(timeLineStart.getText());
		editTimeline.setEndTime(timeLineEnd.getText());
		editTimeline.setKeyword(timeLineKey.getText());
		editTimeline.setDescription(timeLineDesc.getText());
		editTimeline.setBackgroundImage(timeLineBGI.getText());

		// Check so no TextField is empty if it's not allowed and range
		if (editTimeline.getName().length() == 0) {
			editErrorMessage.setText("You can not leave the name empty!");
			return;
		} else if (editTimeline.getKeyword().length() > 45) {
			editErrorMessage.setText("Too many keywords!");
			return;
		} else if (editTimeline.getDescription().length() > 1000) {
			editErrorMessage.setText("The description can max be of lengh 1000");
			return;
		} else {
			editErrorMessage.setText("");
			timelineCon.editTimeline(editTimeline);
		}
		ArrayList<Timeline> timelineEdit = new ArrayList<Timeline>();

		for (Timeline timeline : timelineCon.getTimelines()) {
			if (timeline.getCreatorName().equals(ActiveUser.user.getUsername())) {
				timelineEdit.add(timeline);
			}
		}

		tableView.setPrefWidth(200);

		// Create new Column
		timelineNameCol.setText("Timelines");
		timelineNameCol.setPrefWidth(180);
		timelineNameCol.setCellValueFactory(new PropertyValueFactory<Timeline, SimpleStringProperty>("name"));

		refreshTableViews();

		// imageView
		showBG.setImage(null);

	}

	@FXML
	void deleteClicked(ActionEvent event) {
		// get selected timeline object
		Timeline selectedTimeline = tableView.getSelectionModel().getSelectedItem();

		// if user did not select any timeline
		if (selectedTimeline == null) {
			// show please select a time line or make passive the button
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Please select a timeline to delete!");
			alert.showAndWait();
			System.out.println("Please select a timeline to delete!");
			return;
		}
		// deletion confirmation box opened
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Delete Timeline " + selectedTimeline.getName());
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you would like to delete \"" + selectedTimeline.getName() + "\"?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			// ... user chose OK
			if (timelineCon.deleteTimelineByName(selectedTimeline.getName())) {
				// deletion success
				// remove the deleted timeline from view
				File file = new File(selectedTimeline.getBackgroundImage());
				if (file.delete()) {
					System.out.println("File deleted successfully");
				} else {
					System.out.println("Failed to delete the file");
				}
				tableView.getItems().remove(selectedTimeline);

				Alert feedback = new Alert(Alert.AlertType.INFORMATION);
				feedback.setTitle(selectedTimeline.getName() + " Deleted");
				feedback.setContentText(selectedTimeline.getName() + " Deleted Successfully");
				feedback.setHeaderText(null);
				feedback.showAndWait();

				refreshTableViews();

			} else {
				// deletion failed
				alert.setTitle("Information Dialog");
				alert.setHeaderText(null);
				alert.setContentText("The timeline " + selectedTimeline + " could not be deleted!");
				alert.showAndWait();
				System.out.println("The timeline " + selectedTimeline + " could not be deleted!");
			}
		} else {
			// ... user chose CANCEL or closed the dialog
			alert.close();
		}
	}

	/*
	 * this method is used to validate a timelines range, if a timeline has a proper
	 * date range (2020-01-01 - 2022-01-01) it will return true, if a timeline has a
	 * custom range(beginning - end of time) it will return false.
	 */
	public boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	@FXML
	void editClicked() throws Exception {
		Timeline selectedTimeline = tableView1.getSelectionModel().getSelectedItem();

		if (selectedTimeline == null) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Please select a timeline to create an event!");
			alert.showAndWait();
			System.out.println("Please select a timeline to create an event!");
			return;
		}
		// Check if name of event exists
		if (addEventChange.getLength() < 1) {
			Alert dateAlert = new Alert(Alert.AlertType.ERROR);
			dateAlert.setTitle("Cannot create event without valid name");
			dateAlert.setContentText("Please select a name for the event");
			dateAlert.setHeaderText(null);
			dateAlert.showAndWait();
			return;
		}

		String eventName = addEventChange.getText();
		String eventStart = "";
		String eventEnd = "";

		String eventDesc = addEventDesc.getText();

		// if end date is null user should be able to create event
		// still cannot get this to work,
		if (eventDateStart.isVisible() && eventDateEnd.isVisible()) {

			if (eventDateStart.getValue() == null) {
				Alert dateAlert = new Alert(Alert.AlertType.ERROR);
				dateAlert.setTitle("Cannot create event without valid start date");
				dateAlert.setContentText("Please select a valid start date to create an event.");
				dateAlert.setHeaderText(null);
				dateAlert.showAndWait();
				return;
			}

			LocalDate eventSrt = eventDateStart.getValue();
			eventStart = eventSrt.toString();

			if (eventDateEnd.getValue() != null) {
				LocalDate eventEndd = eventDateEnd.getValue();
				eventEnd = eventEndd.toString();

				if (eventDateEnd.getValue().isBefore(eventDateStart.getValue())) {
					Alert dateAlert = new Alert(Alert.AlertType.ERROR);
					dateAlert.setTitle("Selected End Date is Before Start Date!");
					dateAlert.setContentText("Please select end date that is after start date.");
					dateAlert.setHeaderText(null);
					dateAlert.showAndWait();
					return;
				}
			} else {
				eventEnd = null;
			}

		} else if (addEventStart.isVisible() && addEventEnd.isVisible()) {
			eventStart = addEventStart.getText();
			eventEnd = addEventEnd.getText();
		}

		if (eventStart.length() == 0) {
			Alert dateAlert = new Alert(Alert.AlertType.ERROR);
			dateAlert.setTitle("Start is empty");
			dateAlert.setContentText("Please set start text.");
			dateAlert.setHeaderText(null);
			dateAlert.showAndWait();
			return;
		}

		// if end date is null user should be able to create event
		// still cannot get this to work,
		if (eventDateStart.isVisible() && eventDateEnd.isVisible()) {
			LocalDate eventSrt = eventDateStart.getValue();

			if (eventDateEnd.getValue() != null) {
				LocalDate eventEndd = eventDateEnd.getValue();
				eventEnd = eventEndd.toString();

				if (eventDateEnd.getValue().isBefore(eventDateStart.getValue())) {
					Alert dateAlert = new Alert(Alert.AlertType.ERROR);
					dateAlert.setTitle("Selected End Date is Before Start Date!");
					dateAlert.setContentText("Please select end date that is after start date.");
					dateAlert.setHeaderText(null);
					dateAlert.showAndWait();
					return;
				}
			} else {
				eventEnd = null;
			}
			eventStart = eventSrt.toString();

		} else if (eventDateStart.isVisible()) {
			LocalDate eventSrt = eventDateStart.getValue();
			// LocalDate eventEndd = eventDateEnd.getValue();
			eventStart = eventSrt.toString();
			eventEnd = null;
		}

		if (!eventCon.createEvent(eventName, timelineCon.getTimelineIDByName(selectedTimeline.getName()), eventDesc,
				eventStart, eventEnd, getImageName())) {

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("You have already made that event in " + selectedTimeline.getName());
			alert.showAndWait();
			return;
		} else {
			Timeline activeTimeline = tableView1.getSelectionModel().getSelectedItem();
			int timelineId = timelineCon.getTimelineIDByName(activeTimeline.getName());
			tableViewEvent.setItems(FXCollections.observableArrayList(eventCon.getEventsByTimeline(timelineId)));
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Event has now been created in " + selectedTimeline.getName());
			alert.showAndWait();
			eventDateStart.setValue(null);
			eventDateEnd.setValue(null);
			addEventStart.clear();
			addEventEnd.clear();
			addEventDesc.clear();
			addEventChange.clear();
			return;
		}
	}

	@FXML
	void eventTableClicked(MouseEvent e) {
		tableView1.setOnMouseClicked(new ListViewHandler() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				clearEventForm();
				if (isValidDate(tableView1.getSelectionModel().getSelectedItem().getStartTime())) {
					eventDateStart.setVisible(true);
					eventDateEnd.setVisible(true);
					addEventStart.setVisible(false);
					addEventEnd.setVisible(false);
					// for demonstration purposes
				} else {
					eventDateStart.setVisible(false);
					eventDateEnd.setVisible(false);
					addEventStart.setVisible(true);
					addEventEnd.setVisible(true);
				}

				Timeline selectedTimeline = tableView1.getSelectionModel().getSelectedItem();
				int timelineId = timelineCon.getTimelineIDByName(selectedTimeline.getName());
				tableViewEvent.setItems(FXCollections.observableArrayList(eventCon.getEventsByTimeline(timelineId)));
			}
		});
	}

	@FXML
	void showSearchPage(ActionEvent event) throws Exception {
		System.out.println("yo");
		SceneHandler sh = new SceneHandler();
		Scene userScene = sh.newScene("SearchPage.fxml", 640, 660);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(userScene);
		window.show();
	}

	@FXML
	void checkBoxClicked(ActionEvent event) {
		if (otherDatesCheckBox.isSelected()) {
			datePick1.setVisible(false);
			datePick2.setVisible(false);
			datePickTextField1.setVisible(true);
			datePickTextField2.setVisible(true);
		} else {
			datePick1.setVisible(true);
			datePick2.setVisible(true);
			datePickTextField1.setVisible(false);
			datePickTextField2.setVisible(false);
		}
	}

	@FXML
	void addPicClicked(ActionEvent event) throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load The Pic");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(null);
		Image image = new Image(selectedFile.toURI().toString());
		// saveToFile(image, selectedFile.getName());
		show.setImage(image);
	}

	/*
	 * private void saveToFile(Image image, String name) throws IOException {
	 * File outputFile = new File(name);
	 * BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
	 * try {
	 * ImageIO.write(bImage, "png", outputFile);
	 * } catch (IOException e) {
	 * throw new RuntimeException(e);
	 * }
	 * this.imageName = name;
	 * }
	 */

	public String getImageName() {
		return imageName;
	}
	/* Search related functions */

	public void deleteImg(MouseEvent mouseEvent) throws Exception {
		show.setImage(null);
		Event selectedEvent = tableViewEvent.getSelectionModel().getSelectedItem();
		if (selectedEvent != null) {
			File file = new File(selectedEvent.getImageName());
			if (file.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
			this.imageName = null;
		}
	}

	public void deleteBG(MouseEvent mouseEvent) {
		showBGT.setImage(null);

		File file = new File(getImageName());
		if (file.delete()) {
			System.out.println("File deleted successfully");
		} else {
			System.out.println("Failed to delete the file");
		}
		this.imageName = null;
		closeImageC.setVisible(false);
	}

	@FXML
	void goBack(ActionEvent event) {
	}

	@FXML
	void exitApp(MouseEvent event) {
		System.exit(1);
	}

	public void eventItemClicked(MouseEvent mouseEvent) {
		tableViewEvent.setOnMouseClicked(new ListViewHandler() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				clearEventForm();
				Event selectedEvent = tableViewEvent.getSelectionModel().getSelectedItem();
				if (selectedEvent != null) {
					if (selectedEvent.getEndTime() == null)
						selectedEvent.setEndTime("");
					addEventChange.setText(selectedEvent.getName());
					addEventDesc.setText(selectedEvent.getDescription());
					if (eventDateStart.isVisible()) {
						eventDateStart.setValue(LOCAL_DATE(selectedEvent.getStartTime()));
					} else {
						addEventStart.setText(selectedEvent.getStartTime());
					}
					if (eventDateEnd.isVisible()
							&& (!selectedEvent.getEndTime().trim().isEmpty() && selectedEvent.getEndTime() != null)) {
						eventDateEnd.setValue(LOCAL_DATE(selectedEvent.getEndTime()));
					} else if (addEventEnd.isVisible()) {
						addEventEnd.setText(selectedEvent.getEndTime());
					}
					updateEventButton.setVisible(true);
					newEventButton.setVisible(true);
					deleteEventButton.setVisible(true);
					createEventButton.setVisible(false);

					if (selectedEvent.getImageName() != null) {
						File file = new File(selectedEvent.getImageName());
						Image image = new Image(file.toURI().toString());
						show.setImage(image);
					} else {
						show.setImage(null);
					}
				}
			}
		});
	}

	public static final LocalDate LOCAL_DATE(String dateString) {
		try {
			DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(dateString, input);
			localDate.format(output);
			return localDate;
		} catch (Exception e) {
			System.out.println(e.getMessage() + ". Checking other format...");
		}

		try {
			DateTimeFormatter input = DateTimeFormatter.ofPattern("M/d/yyyy");
			DateTimeFormatter output = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(dateString, input);
			localDate.format(output);
			return localDate;
		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setContentText("Invalid date format. " + "\nExpected: yyyy-MM-dd, M/d/yyyy or dd\\.mm\\.yyyy"
					+ "\nYour input: " + dateString);
			alert.showAndWait();
			return null;
		}

	}

	public void clearEventForm() {
		addEventChange.setText("");
		addEventDesc.setText("");
		eventDateStart.setValue(null);
		addEventStart.setText("");
		eventDateEnd.setValue(null);
		addEventEnd.setText("");
		newEventButton.setVisible(false);
		deleteEventButton.setVisible(false);
		updateEventButton.setVisible(false);
		createEventButton.setVisible(true);
	}

	public void updateEvent(ActionEvent actionEvent) {
		Event eventForUpdating = tableViewEvent.getSelectionModel().getSelectedItem();
		eventForUpdating.setName(addEventChange.getText());
		eventForUpdating.setDescription(addEventDesc.getText());
		eventForUpdating.setImageName(getImageName());
		if (eventDateStart.isVisible()) {
			if (eventDateStart.getEditor().getText().trim().isEmpty()) {
				eventForUpdating.setStartTime("");
			} else {
				eventForUpdating.setStartTime(LOCAL_DATE(eventDateStart.getEditor().getText()).toString());
			}

		} else {
			eventForUpdating.setStartTime(addEventStart.getText());
		}
		if (eventDateEnd.isVisible()) {
			if (eventDateEnd.getEditor().getText().length() < 1) {
				eventForUpdating.setEndTime("");
			} else {
				eventForUpdating.setEndTime(LOCAL_DATE(eventDateEnd.getEditor().getText()).toString());
			}
		} else {
			eventForUpdating.setEndTime(addEventEnd.getText());
		}
		if (controlEventObject(eventForUpdating, addEventEnd.isVisible())) {
			boolean result = eventCon.updateEvent(eventForUpdating);
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			if (result) {
				tableViewEvent.refresh();
				alert.setContentText("Event successfully updated!");
				alert.showAndWait();
			} else {

				alert.setContentText("Event name must be unique!");
				alert.showAndWait();
			}
		}
	}

	public void newEventButtonClicked(ActionEvent actionEvent) {
		clearEventForm();
	}

	public void deleteEvent(ActionEvent actionEvent) {
		Event selectedEvent = tableViewEvent.getSelectionModel().getSelectedItem();

		Alert deleteEventConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
		deleteEventConfirmation.setTitle("Delete Timeline " + selectedEvent.getName());
		deleteEventConfirmation.setHeaderText(null);
		deleteEventConfirmation.setContentText("Are you sure you would like to delete " + selectedEvent.getName()
				+ "? \nAny picture files will also be deleted from the drive.");

		Alert deletedEventNotification = new Alert(Alert.AlertType.INFORMATION);
		deletedEventNotification.setTitle("Delete Timeline " + selectedEvent.getName());
		deletedEventNotification.setHeaderText(null);

		Optional<ButtonType> confirmationResult = deleteEventConfirmation.showAndWait();
		if (confirmationResult.get() == ButtonType.OK) {
			// ... user chose OK
			Boolean result = eventCon.deleteEventByID(selectedEvent.getId());
			if (result) {
				tableViewEvent.getItems().remove(selectedEvent);
				tableViewEvent.refresh();
				// image deletion
				if (selectedEvent.getImageName() != null) {
					File image = new File(selectedEvent.getImageName());
					image.getAbsolutePath();
					image.delete();
				}
				clearEventForm();
				deletedEventNotification.setContentText(" " + selectedEvent.getName() + " was successfully deleted.");
				deletedEventNotification.showAndWait();
			}
		}
	}

	public void addBPressed(ActionEvent actionEvent) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load The Picture");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(null);
		Image image = new Image(selectedFile.toURI().toString());
		timeLineBGI.setText(selectedFile.getName());
		// saveToFile(image, selectedFile.getName());
		showBG.setImage(image);
	}

	public void removeBPressed(ActionEvent actionEvent) throws Exception {
		if (timeLineBGI.getText() != null) {
			File file = new File(timeLineBGI.getText());
			if (file.delete()) {
				System.out.println("File deleted successfully");
			} else {
				System.out.println("Failed to delete the file");
			}
			timeLineBGI.setText("");
			showBG.setImage(null);
		}
	}

	public void addBackgroundImage(ActionEvent actionEvent) throws Exception {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load The Picture");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(null);
		Image image = new Image(selectedFile.toURI().toString());
		timelineImageName.setText(selectedFile.getName());
		// saveToFile(image, selectedFile.getName());
		showBGT.setImage(image);
		closeImageC.setVisible(true);
	}

	private class ListViewHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
		}
	}

	public void refreshTableViews() {
		ArrayList<Timeline> timelines = new ArrayList<>();
		// Filter timelines which belong to this admin
		for (Timeline timeline : timelineCon.getTimelines()) {
			if (timeline.getCreatorName().equals(ActiveUser.user.getUsername())) {
				timelines.add(timeline);
			}
		}
		tableView1.setItems(FXCollections.observableArrayList(newestFirst(timelines)));
		tableView.setItems(FXCollections.observableArrayList(newestFirst(timelines)));
		tableViewEvent.setItems(FXCollections.observableArrayList(new ArrayList<Event>()));

		dataList = FXCollections.observableArrayList(newestFirst(timelineCon.getTimelines()));
		// dataList = FXCollections.observableArrayList();

		FilteredList<Timeline> filteredData = new FilteredList<>(dataList, b -> true);

		searchBoxR.textProperty().addListener((observable, oldValue, newValue) -> {

			filteredData.setPredicate(timelineConnect -> {

				if (searchBoxR.getText().isEmpty()) {
					return timelineConnect.getName().toLowerCase().contains(searchBoxT.getText().toLowerCase()) &&
							timelineConnect.getCreatorName().toLowerCase().contains(searchBoxC.getText().toLowerCase())
							&&
							timelineConnect.getTimeStamp().toString().contains(searchBoxD.getText().toLowerCase());
				} else
					return

					(timelineConnect.getAverageRating() >= Double.parseDouble(newValue)) &&
							timelineConnect.getName().toLowerCase().contains(searchBoxT.getText().toLowerCase()) &&
							timelineConnect.getCreatorName().toLowerCase().contains(searchBoxC.getText().toLowerCase())
							&&
							timelineConnect.getTimeStamp().toString().contains(searchBoxD.getText().toLowerCase());

			});

		});

		searchBoxT.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(timelineConnect -> {
				String lowerCaseFilter = newValue.toLowerCase();

				if (searchBoxR.getText().isEmpty()) {
					return timelineConnect.getName().toLowerCase().contains(lowerCaseFilter) &&
							timelineConnect.getCreatorName().toLowerCase().contains(searchBoxC.getText().toLowerCase())
							&&
							timelineConnect.getTimeStamp().toString().contains(searchBoxD.getText().toLowerCase());
				} else
					return timelineConnect.getAverageRating() >= Double.parseDouble(searchBoxR.getText()) &&
							timelineConnect.getName().toLowerCase().contains(lowerCaseFilter) &&
							timelineConnect.getCreatorName().toLowerCase().contains(searchBoxC.getText().toLowerCase())
							&&
							timelineConnect.getTimeStamp().toString().contains(searchBoxD.getText().toLowerCase());
			});
		});
		searchBoxC.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(timelineConnect -> {
				String lowerCaseFilter = newValue.toLowerCase();
				if (searchBoxR.getText().equalsIgnoreCase("")) {
					return timelineConnect.getName().toLowerCase().contains(searchBoxT.getText().toLowerCase()) &&
							timelineConnect.getCreatorName().toLowerCase().contains(lowerCaseFilter) &&
							timelineConnect.getTimeStamp().toString().contains(searchBoxD.getText().toLowerCase());
				} else
					return timelineConnect.getAverageRating() >= Double.parseDouble(searchBoxR.getText()) &&
							timelineConnect.getName().toLowerCase().contains(searchBoxT.getText().toLowerCase()) &&
							timelineConnect.getCreatorName().toLowerCase().contains(lowerCaseFilter) &&
							timelineConnect.getTimeStamp().toString().contains(searchBoxD.getText().toLowerCase());

			});
		});
		searchBoxD.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(timelineConnect -> {
				String lowerCaseFilter = newValue.toLowerCase();

				if (searchBoxR.getText().equalsIgnoreCase("")) {

					return timelineConnect.getName().toLowerCase().contains(searchBoxT.getText().toLowerCase()) &&
							timelineConnect.getCreatorName().toLowerCase().contains(searchBoxC.getText().toLowerCase())
							&&
							timelineConnect.getTimeStamp().toString().contains(lowerCaseFilter);
				} else
					return timelineConnect.getAverageRating() > Double.parseDouble(searchBoxR.getText()) &&
							timelineConnect.getName().toLowerCase().contains(searchBoxT.getText().toLowerCase()) &&
							timelineConnect.getCreatorName().toLowerCase().contains(searchBoxC.getText().toLowerCase())
							&&
							timelineConnect.getTimeStamp().toString().contains(lowerCaseFilter);

			});
		});

		SortedList<Timeline> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(resultsTable.comparatorProperty());
		resultsTable.setItems(sortedData);
	}

	private ArrayList<Timeline> newestFirst(ArrayList<Timeline> timeline) {
		ArrayList<Timeline> newestfirst = new ArrayList<Timeline>();
		for (int i = 0; i < timeline.size(); i++) {
			newestfirst.add(timeline.get(timeline.size() - i - 1));
		}
		return newestfirst;
	}

	public boolean controlEventObject(Event event, boolean abstractDate) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		if (event.getName().length() < 1) {
			alert.setContentText("Event should has name!");
			alert.showAndWait();
			return false;
		} else if (event.getStartTime().length() < 1) {
			alert.setContentText("Event should has start date!");
			alert.showAndWait();
			return false;
		} else if (event.getEndTime() != "" && !abstractDate) {
			LocalDate start = LOCAL_DATE(event.getStartTime());
			LocalDate end = LOCAL_DATE(event.getEndTime());
			if (start != null && end != null && end.isBefore(start)) {
				alert.setContentText("Please select end date that is after start date.");
				alert.showAndWait();
				return false;
			}
		} else {

		}
		return true;
	}

	@FXML
	void logoutClicked(ActionEvent e) throws Exception {
		ActiveUser.user = null;
		ActiveUser.adminStatus = false;
		SceneHandler sh = new SceneHandler();
		Scene switchScene;
		switchScene = sh.newScene("FirstPage.fxml", 400, 700);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setTitle("TimeLine");
		window.setScene(switchScene);
		window.show();

	}

	public void refreshGraphicTimeline() {

		graphicTimeline.getChildren().clear(); // clears VBox in which the graphical timelines are stored
		int counter = 1; // Counter to display each timelines position in the graphical timeline list

		ArrayList<Timeline> timelines = timelineCon.getTimelines(); // Arraylist containg all timneline objects
		for (int i = timelines.size() - 1; i >= 0; i--) { // Loop that iterates from last to first element in order to
															// display newest->oldest timeline

			Timeline timeline = timelines.get(i);

			GraphicTimelineController unit = new GraphicTimelineController(); // Here we setup each graphical timeline
			unit.setTimelineTitle(timeline.getName());
			unit.setTimelineDesc(timeline.getDescription());
			unit.setTimelineDate(timeline.getTimeStamp().toString());
			unit.setTimelineKeywords(timeline.getKeyword());
			unit.setImage(timeline.getBackgroundImage());
			unit.setCounter(counter);
			counter++; // iterate counter by 1 to keep track and display timeline postion
			unit.setRating(Math.ceil(timeline.getAverageRating()));
			unit.initialize();

			if (timeline.getCreatorName().equals(ActiveUser.user.getUsername())) { // Check if User = timeline creater
				unit.getRateBox().setDisable(true); // if true disable rating feature
			}

			else { // if user != timeline creater, enable rating feature
				unit.initializeChoiceBox();
				int activeUserId = userCon.getUserID(ActiveUser.user.getUsername());
				Rate rate = rateCon.getRateByTimelineIdAndUserId(timeline.getId(), activeUserId);

				if (rate != null)
					unit.getRateBox().setValue(rate.getRate());

				unit.getRateBox().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
						int rateValue = (int) unit.getRateBox().getItems().get((Integer) t1);
						Rate rate = rateCon.getRateByTimelineIdAndUserId(timeline.getId(), activeUserId);

						if (rate != null) {
							rate.setRate(rateValue);
							rateCon.updateRate(rate);

						} else {
							rate = new Rate();
							rate.setRate(rateValue);
							rate.setUserId(activeUserId);
							rate.setTimelineId(timeline.getId());
							rateCon.createRate(rate);
						}
						refreshGraphicTimeline();
					}
				});
			}
			graphicTimeline.getChildren().addAll(unit); // Add each graphical timeline unit to VBox to be displayed
		}
	}
}
