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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.StudentTm;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.SubjectTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class StudentTableFormController implements Initializable {
    StudentFormController studentFormController = new StudentFormController();
    StudentModel studentModel = new StudentModel();

    @FXML
    private Pane StudentPane;

    @FXML
    private Button btnAction;

    @FXML
    private Button btnOtherInfo;

    @FXML
    private TableColumn<StudentTm, String> colAddBy;

    @FXML
    private TableColumn<StudentTm, Double> colAdmissionFee;

    @FXML
    private TableColumn<StudentTm, LocalDate> colDOB;

    @FXML
    private TableColumn<StudentTm, String> colGrade;

    @FXML
    private TableColumn<StudentTm, String> colStudentID;

    @FXML
    private TableColumn<StudentTm, String> colStudentName;

    @FXML
    private TableColumn<StudentTm, String> colSubject;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtFindStudent;

    private String currentLoadedFXML = "";
    boolean isClicked = false;

    FilteredList filter;

    @FXML
    void btnActionOnClicked(ActionEvent event) {
        isClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/studentForm.fxml"));
            Parent load = loader.load();

            StudentFormController controller = loader.getController();

            controller.setStudentTableFormController(this);

            this.studentFormController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Student Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnAction.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnOtherInfoOnAction(ActionEvent event) {
        navigateTo("/view/studentOtherInfoTableForm.fxml","student - Other Details");

    }

    @FXML
    void tblStudentOnMouseClicked(MouseEvent event) {
        StudentTm studentTm = tblStudent.getSelectionModel().getSelectedItem();
        if (isClicked){
            if (studentTm != null) {
                ArrayList<StudentDto> studentDtos = studentModel.getStudentsById(studentTm.getId());

                StudentDto dto = null;
                for (StudentDto studentDto : studentDtos) {
                    dto = new StudentDto(
                            studentDto.getId(),
                            studentDto.getBirthday(),
                            studentDto.getName(),
                            studentDto.getAdmissionFee(),
                            studentDto.getParentName(),
                            studentDto.getEmail(),
                            studentDto.getPhoneNumber(),
                            studentDto.getAddress(),
                            studentDto.getAddedBy(),
                            studentDto.getGrade(),
                            studentDto.getSubjects()
                    );
                }


                if (dto != null) {
                    studentFormController.setStudentDto(dto);
                    studentFormController.tableClickButton();
                }
            }
        }
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
                            studentTm.getGrade().toLowerCase().contains(newValue.toLowerCase());
                }
            });

            SortedList<StudentTm> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(tblStudent.comparatorProperty());
            tblStudent.setItems(sortedList);
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
            newPane.setTranslateX(-StudentPane.getWidth());  // Start from the left side of the pane (off-screen)

            // Bind the width and height of the loaded pane to the contentMainPane
            newPane.prefWidthProperty().bind(StudentPane.widthProperty());
            newPane.prefHeightProperty().bind(StudentPane.heightProperty());

            // Clear existing children in the contentMainPane and add the new pane
            StudentPane.getChildren().clear();
            StudentPane.getChildren().add(newPane);

            // Create a TranslateTransition to move the pane from left to right
            TranslateTransition transition = new TranslateTransition();
            transition.setNode(newPane);
            transition.setDuration(Duration.millis(250));  // Set the duration of the transition
            transition.setToX(0);  // Move to the original position (0)
            transition.play();  // Start the transition

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.errorAlert(StudentTableFormController.class, null, loadErrorpane);
        }
    }


    public void loadTable() {
        ArrayList<StudentDto> studentDtos = studentModel.getAllStudents();
        ObservableList<StudentTm> studentTmList = FXCollections.observableArrayList();

        for (StudentDto studentDto : studentDtos) {
            String subjectsString = String.join(", ", studentDto.getSubjects());

            StudentTm studentTm = new StudentTm(
                    studentDto.getId(),
                    studentDto.getName(),
                    studentDto.getBirthday(),
                    studentDto.getAdmissionFee(),
                    studentDto.getGrade(),
                    subjectsString,
                    studentDto.getAddedBy()
            );


            studentTmList.add(studentTm);
        }


        tblStudent.setItems(studentTmList);

        filter = new FilteredList(studentTmList, e -> true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAddBy.setCellValueFactory(new PropertyValueFactory<>("addedBy"));
        colAdmissionFee.setCellValueFactory(new PropertyValueFactory<>("admissionFee"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjects"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        loadTable();
    }
}
