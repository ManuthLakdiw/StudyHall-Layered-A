package lk.ijse.gdse.instritutefirstsemfinal.controller;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.TeacherBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.TeacherTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class TeacherTableFormController implements Initializable {

//    TeacherModel teacherModel = new TeacherModel();
    TeacherFormController formTeacherController = new TeacherFormController();
    SendMailToTeacherFormController sendMailToTeacherFormController = new SendMailToTeacherFormController();

    TeacherBO teacherBO = (TeacherBO) BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);

    @FXML
    private Pane SubjectPane;

    @FXML
    private Button btnSendMail;

    @FXML
    private Button btnTeacher1;

    @FXML
    private TableColumn<TeacherTm, String> colContactNumber;


    @FXML
    private TableColumn<TeacherTm, String> colEmail;

    @FXML
    private TableColumn<TeacherTm, String> colName;

    @FXML
    private TableColumn<TeacherTm, String> colTeacherID;

    @FXML
    private TableColumn<TeacherTm, String> colTeachingSubjects;

    @FXML
    private TableColumn<TeacherTm, String> colTeachingGrades;

    @FXML
    private TableView<TeacherTm> tblTeacher;

    @FXML
    private TextField txtFindTeacher;

    private FilteredList<TeacherTm> filter;  // Declare the filter as a member variable

    boolean isClicked = false;

    @FXML
    void btnSendMailOnAction(ActionEvent event) {
        TeacherTm teacherTm = tblTeacher.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sendMailToTeacherForm.fxml"));
            Parent load = loader.load();

            SendMailToTeacherFormController controller = loader.getController();

            controller.setTableTeacherFormController(this);

            this.sendMailToTeacherFormController = controller;

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
    void btnTeacherOnAction(ActionEvent event) {
        isClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/teacherForm.fxml"));
            Parent load = loader.load();

            TeacherFormController controller = loader.getController();

            controller.setTableTeacherFormController(this);

            this.formTeacherController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Teacher Form");
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
    void tblTeacherOnClicked(MouseEvent event) {
                if (isClicked) {
                    TeacherTm selectedItem =tblTeacher.getSelectionModel().getSelectedItem();
                    String grades = selectedItem.getGrades();
                    String[] gradeArray = new String[0];

                    if (grades != null && !grades.isEmpty()) {
                        gradeArray = grades.split(", ");
                    }

                    TeacherDto dto = new TeacherDto(
                            selectedItem.getTeacherId(),
                            selectedItem.getName(),
                            selectedItem.getPhoneNumber(),
                            selectedItem.getEmail(),
                            selectedItem.getSubjects(),
                            gradeArray

                    );
                    formTeacherController.setDto(dto);
                    formTeacherController.tableOnClickedButton();
                }
    }

    @FXML
    void txtFindTeacherOnKeyRelesed(KeyEvent event) {
        txtFindTeacher.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super TeacherTm>) (TeacherTm teacherTm) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Return all subjects if the search text is empty
                } else {
                    // Perform case-insensitive matching
                    return teacherTm.getTeacherId().toLowerCase().contains(newValue.toLowerCase()) ||
                            teacherTm.getName().toLowerCase().contains(newValue.toLowerCase());

                }
            });

            SortedList<TeacherTm> sortedList = new SortedList<>(filter);
            sortedList.comparatorProperty().bind(tblTeacher.comparatorProperty());
            tblTeacher.setItems(sortedList);
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTeacherID.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTeachingSubjects.setCellValueFactory(new PropertyValueFactory<>("subjects"));
        colTeachingGrades.setCellValueFactory(new PropertyValueFactory<>("grades"));

//        colTeacherID.setCellFactory(column -> {
//            return new TableCell<TeacherTm, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty || item == null) {
//                        setText(null);
//                        setStyle(""); // Reset style
//                    } else {
//                        setText(item);
//                        // Apply bold and larger font size to teacher ID
//                        setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
//                    }
//                }
//            };
//        });

        try {
            loadTeacherTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void loadTeacherTable() throws SQLException {
        ArrayList<TeacherDto> teacherDtos = teacherBO.getAllTeachersAndRelatedGrades();
        ObservableList<TeacherTm> teacherTms = FXCollections.observableArrayList();

        if (teacherDtos != null) {
            for (TeacherDto teacherDto : teacherDtos) {
                String grades = teacherDto.getGrades() != null && teacherDto.getGrades().length > 0
                        ? String.join(", ", teacherDto.getGrades())
                        : "N/A";

                TeacherTm teacherTm = new TeacherTm(
                        teacherDto.getTeacherId(),
                        teacherDto.getName(),
                        teacherDto.getPhoneNumber(),
                        teacherDto.getEmail(),
                        teacherDto.getSubject(),
                        grades
                );

                teacherTms.add(teacherTm);
            }

            tblTeacher.setItems(teacherTms);

            filter = new FilteredList(teacherTms, e -> true);
        }



    }


}
