package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.StudentBO;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.SubjectBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.StudentTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class StudentOtherInfoTableFormController implements Initializable {

    private String currentLoadedFXML = "";
//    StudentModel studentModel = new StudentModel();
    StudentBO studentBO= (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    SendMailToStudentFormController sendMailToStudentFormController = new SendMailToStudentFormController();

    @FXML
    private Pane StudentOtherInfoPane;

    @FXML
    private Button btnSendMail;

    @FXML
    private TableColumn<StudentTm, String> colAddress;

    @FXML
    private TableColumn<StudentTm, String> colEmail;

    @FXML
    private TableColumn<StudentTm, String> colParentName;

    @FXML
    private TableColumn<StudentTm, String> colPhoneNumber;

    @FXML
    private TableColumn<StudentTm, String> colStudentID;

    @FXML
    private TableColumn<StudentTm, String> colStudentName;

    @FXML
    private Label lblBack;

    @FXML
    private TableView<StudentTm> tblStudentOtherInfo;

    @FXML
    private TextField txtFindStudent;

    FilteredList filter;

    @FXML
    void btnSendMailOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendMailToStudentForm.fxml"));
            Parent load = loader.load();

            SendMailToStudentFormController controller = loader.getController();

            controller.setTableStudentController(this);

            this.sendMailToStudentFormController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Email Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnSendMail.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void lblBackOnAction(MouseEvent event) {
        navigateTo("/view/StudentTableForm.fxml", "StudentForm");
    }

    @FXML
    void tblStudentOtherInfoOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void txtFindStudentOnKeyRelesed(KeyEvent event) {
        txtFindStudent.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super StudentTm>) (StudentTm studentTm) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Return all subjects if the search text is empty
                } else {
                    // Perform case-insensitive matching
                    return studentTm.getId().toLowerCase().contains(newValue.toLowerCase()) ||
                            studentTm.getName().toLowerCase().contains(newValue.toLowerCase()) ||
                            studentTm.getParentName().toLowerCase().contains(newValue.toLowerCase()) ||
                            studentTm.getPhone().toLowerCase().contains(newValue.toLowerCase()) ||
                            studentTm.getEmail().toLowerCase().contains(newValue.toLowerCase());
                }
            });

            SortedList<StudentTm> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(tblStudentOtherInfo.comparatorProperty());
            tblStudentOtherInfo.setItems(sortedList);
        });

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
            newPane.setTranslateX(-StudentOtherInfoPane.getWidth());  // Start from the left side of the pane (off-screen)

            // Bind the width and height of the loaded pane to the contentMainPane
            newPane.prefWidthProperty().bind(StudentOtherInfoPane.widthProperty());
            newPane.prefHeightProperty().bind(StudentOtherInfoPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            StudentOtherInfoPane.getChildren().clear();
            StudentOtherInfoPane.getChildren().add(newPane);

            // Create a TranslateTransition to move the pane from left to right
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(newPane);
            transition.setDuration(Duration.millis(250));  // Set the duration of the transition
            transition.setToX(0);  // Move to the original position (0)
            transition.play();  // Start the transition

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.errorAlert(StudentOtherInfoTableFormController.class, null, loadErrorpane);
        }
    }


    public void loadTable() throws SQLException {
        ArrayList<StudentDto> dtos = studentBO.getAllStudentsWithLearnSubjects();
        ObservableList<StudentTm> studentTms = FXCollections.observableArrayList();

        for (StudentDto dto : dtos) {
            StudentTm studentTm = new StudentTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getParentName(),
                    dto.getPhoneNumber(),
                    dto.getEmail(),
                    dto.getAddress()

            );
            studentTms.add(studentTm);
        }
        tblStudentOtherInfo.setItems(studentTms);

        filter = new FilteredList(studentTms, e -> true);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colParentName.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));

        try {
            loadTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
