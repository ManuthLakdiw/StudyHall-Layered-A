package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainLayoutFormController implements Initializable {

    @FXML
    private HBox hBoxDashBoard;

    @FXML
    private HBox hBoxStudent;

    @FXML
    private HBox hBoxTeacher;

    @FXML
    private HBox hBoxSubject;

    @FXML
    private HBox hBoxLogout;

    @FXML
    private Pane contentMainPane;

    @FXML
     private Button btnDashBoard;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnSubject;

    @FXML
    private Button btnTeacher;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblUser;

    @FXML
    private Button btnUser;

    @FXML
    private HBox hBoxUser;

    @FXML
    private HBox hBoxExam;

    @FXML
    private Button btnExam;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnResult;


    @FXML
    private HBox hBoxReport;

    @FXML
    private HBox hBoxResult;







    private String currentLoadedFXML = "";
    private HBox lastClickedHBox = null;


    @FXML
    void hBoxDashBoardOnClicked(MouseEvent event) {
        applyHBoxBackground(hBoxDashBoard);
        navigateTo("/view/dashBoardForm.fxml", "DashBoardForm");
    }

    @FXML
    void hBoxStudentOnClicked(MouseEvent event) {
        applyHBoxBackground(hBoxStudent);
        navigateTo("/view/StudentTableForm.fxml", "StudentForm");
    }

    @FXML
    void hBoxTeacherOnClicked(MouseEvent event) {
        applyHBoxBackground(hBoxTeacher);
        navigateTo("/view/teacherTableForm.fxml", "TeacherForm");
    }

    public void hBoxUserOnClicked(MouseEvent mouseEvent) {
        applyHBoxBackground(hBoxUser);
        navigateTo("/view/userTableForm.fxml", "UserForm");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUser .setText("Hi, "+LoginFormController.uName+"❖");
        applyHBoxBackground(hBoxDashBoard);
        navigateTo("/view/dashBoardForm.fxml", "DashBoardForm");
        btnDashBoard.setMouseTransparent(true);
        btnStudent.setMouseTransparent(true);
        btnTeacher.setMouseTransparent(true);
        btnLogout.setMouseTransparent(true);
        btnUser.setMouseTransparent(true);
        btnSubject.setMouseTransparent(true);
        btnExam.setMouseTransparent(true);
        btnResult.setMouseTransparent(true);
        btnReport.setMouseTransparent(true);

    }

    public  void navigateTo(String fxmlPath, String loadErrorpane) {
        if (fxmlPath.equals(currentLoadedFXML)) {
            return;
        }

        try {
            currentLoadedFXML = fxmlPath;

            // Load the new pane from FXML
            Pane newPane = FXMLLoader.load(getClass().getResource(fxmlPath));

            // Set initial position for the new pane off the screen to the left
            newPane.setTranslateX(-contentMainPane.getWidth());  // Start from the left side of the pane (off-screen)

            // Bind the width and height of the loaded pane to the contentMainPane
            newPane.prefWidthProperty().bind(contentMainPane.widthProperty());
            newPane.prefHeightProperty().bind(contentMainPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            contentMainPane.getChildren().clear();
            contentMainPane.getChildren().add(newPane);

            // Create a TranslateTransition to move the pane from left to right
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(newPane);
            transition.setDuration(Duration.millis(250));  // Set the duration of the transition
            transition.setToX(0);  // Move to the original position (0)
            transition.play();  // Start the transition

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.errorAlert(MainLayoutFormController.class, null, loadErrorpane);
        }
    }

    private void applyHBoxBackground(HBox hbox) {
        if (lastClickedHBox != null) {
            lastClickedHBox.getStyleClass().remove("selected");
        }

        // Add the 'selected' style to the currently clicked HBox
        hbox.getStyleClass().add("selected");

        // Update the last clicked HBox to the current one
        lastClickedHBox = hbox;
    }

    public void hBoxLogoutOnClicked(MouseEvent mouseEvent) {
        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.NO, ButtonType.YES);
        successAlert.setTitle("Confirmation");
        successAlert.setHeaderText(null);
        successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/assets/style/Style.css").toExternalForm());
        Optional<ButtonType> buttonType = successAlert.showAndWait();

        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.setTitle("StudyHall - Login");
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to load LoginForm!");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/assets/style/Style.css").toExternalForm());
                alert.showAndWait();
            }

        }
    }


    public void hBoxSubjectOnClicked(MouseEvent mouseEvent) {
        applyHBoxBackground(hBoxSubject);
        navigateTo("/view/subjectTableForm.fxml", "SubjectForm");

    }



    public void hBoxExamOnCLicked(MouseEvent mouseEvent) {
        applyHBoxBackground(hBoxExam);
        navigateTo("/view/examTableForm.fxml", "ExamForm");
    }

    public void hBoxResultOnCLicked(MouseEvent mouseEvent) {
        applyHBoxBackground(hBoxResult);
        navigateTo("/view/resultTableForm.fxml", "ResultForm");
    }

    public void hBoxReportOnClicked(MouseEvent mouseEvent) {
        applyHBoxBackground(hBoxReport);
        navigateTo("/view/reportForm.fxml", "ReportForm");
    }
}
