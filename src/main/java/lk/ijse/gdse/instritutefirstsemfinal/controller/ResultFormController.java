package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.GradeModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.ResultModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ResultFormController implements Initializable {

    ExamModel examModel = new ExamModel();
    ResultModel resultModel = new ResultModel();
    SubjectModel subjectModel = new SubjectModel();
    GradeModel gradeModel = new GradeModel();

    String marksRegex = "^(100|[1-9]?[0-9])$";

    String resultId;
    int marks;
    String grade;
    String subject;
    String student;
    String examId;
    String gradeArchived;
    String status;


    private ResultTableFormController resultTableFormController;
    public void setResultTableFormController(ResultTableFormController resultTableFormController) {
        this.resultTableFormController = resultTableFormController;
    }

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;


    @FXML
    private ComboBox<String> cmbGrade;



    @FXML
    private ComboBox<String> cmbSubject;

    @FXML
    private JFXRadioButton radioBtnNotPArticipant;

    @FXML
    private ComboBox<String> cmbExamID;


    @FXML
    private Label lblResultID;

    @FXML
    private Label lblExamIdDesc;

    @FXML
    private ComboBox<String> cmbStudent;

    @FXML
    private Pane resultPane;

    @FXML
    private TextField txtMarks;

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        resultId = lblResultID.getText();

        Optional<ButtonType> buttonType = AlertUtil.ConfirmationAlert("Are you sure?,This action will permanently delete the selected result.",ButtonType.NO,ButtonType.YES);

        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = resultModel.deleteResult(resultId);

            if (isDeleted) {
                AlertUtil.informationAlert(this.getClass(), null, true, "Result deleted successfully.");
                lblResultID.setText(resultModel.getNextResultID());
                refreshPage();
                resultTableFormController.loadTable();
            } else {
                AlertUtil.informationAlert(this.getClass(), null, true, "Result deletion failed.");
            }
        }
    }


    @FXML
    void btnResetOnAction(ActionEvent event) {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        resultId = lblResultID.getText();
        if (!txtMarks.getText().isEmpty()) {
            marks = Integer.parseInt(txtMarks.getText());
        }
        grade = cmbGrade.getValue();
        subject = cmbSubject.getValue();
        student = cmbStudent.getValue();
        examId = cmbExamID.getValue();

        gradeArchived = (marks >= 75) ? "A" : (marks >= 65) ? "B" : (marks >= 50) ? "C" : (marks >= 35) ? "S" : "W";

        status = (radioBtnNotPArticipant.isSelected()) ? "Absent" : (marks >= 35) ? "Pass" : "Fail";
        if (status.equals("Absent")) {
            gradeArchived = "F";
        }

        ResultDto dto = new ResultDto(
                resultId,
                grade,
                subject,
                examId,
                student,
                marks,
                gradeArchived,
                status
        );

        boolean isSaved = resultModel.saveResult(dto);

        if (isSaved) {
            AlertUtil.informationAlert(this.getClass(),null,false,"Result saved Successfully");
            lblResultID.setText(resultModel.getNextResultID());
            radioBtnNotPArticipant.setSelected(false);
            txtMarks.setText("");
            cmbStudent.getSelectionModel().clearSelection();
            cmbStudent.show();
            txtMarks.setDisable(false);
            isSaveEnable();
            resultTableFormController.loadTable();

        }else {
            AlertUtil.informationAlert(this.getClass(),null,true,"Result Save Failed");

        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        resultId = lblResultID.getText();
        if (!txtMarks.getText().isEmpty()) {
            marks = Integer.parseInt(txtMarks.getText());
        }
        grade = cmbGrade.getValue();
        subject = cmbSubject.getValue();
        student = cmbStudent.getValue();
        examId = cmbExamID.getValue();

        gradeArchived = (marks >= 75) ? "A" : (marks >= 65) ? "B" : (marks >= 50) ? "C" : (marks >= 35) ? "S" : "W";

        status = (radioBtnNotPArticipant.isSelected()) ? "Absent" : (marks >= 35) ? "Pass" : "Fail";
        if (status.equals("Absent")) {
            gradeArchived = "F";
        }

        ArrayList<ResultDto> resultDtos = resultModel.checkExitingResult(lblResultID.getText());
        ResultDto dto = new ResultDto(
                resultId,
                grade,
                subject,
                examId,
                student,
                marks,
                gradeArchived,
                status
        );

        boolean isUpdated = resultModel.updateResult(dto);

        if (isUpdated) {
            AlertUtil.informationAlert(this.getClass(), null, true, "Result updated successfully");
            refreshPage();
            resultTableFormController.loadTable();

        } else {
            AlertUtil.informationAlert(this.getClass(), null, true, "Result update failed");

        }
    }


    @FXML
    void radioBtnNotPArticipantOnAction(ActionEvent event) {

        if (radioBtnNotPArticipant.isSelected()) {
            txtMarks.setDisable(true);
            RegexUtil.resetStyle(txtMarks);
            txtMarks.clear();

        } else {
            txtMarks.setDisable(false);
            txtMarks.clear();
        }
        isSaveEnable();
    }


    @FXML
    void cmbGradeOnKeyPresssed(KeyEvent event) {

    }



    @FXML
    void cmbSubjectTypeOnKeyPressed(KeyEvent event) {

    }

    @FXML
    private void cmbGradeOnAction(ActionEvent event) {
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();

        cmbSubject.getItems().clear();
        cmbExamID.getItems().clear();
        cmbStudent.getItems().clear();
        lblExamIdDesc.setText("");

        if (selectedGrade != null) {
            String gradeId = gradeModel.getGradeIdFromName(selectedGrade);

            String[] subjectIDs = resultModel.getExamSubjectsByGrade(gradeId);
            Set<String> subjectNamesSet = new HashSet<>();

            for (String subjectID : subjectIDs) {
                String subjectName = subjectModel.getSubjectNameFromId(subjectID);
                if (subjectName != null) {
                    subjectNamesSet.add(subjectName);
                }
            }

            cmbSubject.getItems().addAll(subjectNamesSet);

            if (!subjectNamesSet.isEmpty()) {
                String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();
                if (selectedSubject != null) {
                    String subjectId = subjectModel.getSubjectIdFromName(selectedSubject);
                    ArrayList<String> studentNames = resultModel.getStudentsByGradeAndSubject(gradeId, subjectId);
                    if (studentNames != null && !studentNames.isEmpty()) {
                        cmbStudent.getItems().addAll(studentNames);
                    }
                }
            }
        }
        isSaveEnable();
    }


    @FXML
    void cmbSubjectOnAction(ActionEvent event) {
        String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();

        cmbStudent.getItems().clear();
        cmbExamID.getItems().clear();
        lblExamIdDesc.setText("");

        if (selectedSubject != null && selectedGrade != null) {
            String subjectId = subjectModel.getSubjectIdFromName(selectedSubject);
            String gradeId = gradeModel.getGradeIdFromName(selectedGrade);

            ArrayList<String> studentNames = resultModel.getStudentsByGradeAndSubject(gradeId, subjectId);
            if (studentNames != null && !studentNames.isEmpty()) {
                cmbStudent.getItems().addAll(studentNames);
            }

            String[] examIDs = examModel.getExamIDsfromSubject(subjectId);
            if (examIDs != null && examIDs.length > 0) {
                cmbExamID.getItems().addAll(examIDs);
            } else {
                cmbExamID.setPromptText("No exams available");
            }
        } else {
            System.out.println("Selected Subject or Grade is null");
        }
        isSaveEnable();
    }


    public void cmbExamIDOnAction(ActionEvent actionEvent) {
        String selectedExamID = cmbExamID.getSelectionModel().getSelectedItem();

        if (selectedExamID != null) {
            ArrayList<ExamDto> examDtos = examModel.getAllExams();

            for (ExamDto examDto : examDtos) {
                if (examDto.getExamId().equals(selectedExamID)) {
                    lblExamIdDesc.setText(examDto.getExamDate() + "    " + examDto.getExamDescription());
                    break;
                }
            }
        }
        isSaveEnable();
    }


    public void cmbExamIDOnKeyPressed(KeyEvent keyEvent) {

    }


    @FXML
    void txtMarksOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtMarksOnKeyTyped(KeyEvent event) {
        String marksCheck = txtMarks.getText();


        if (txtMarks.getText().isEmpty()) {
            RegexUtil.resetStyle(txtMarks);
        }else {
            if (txtMarks.getText().matches(marksRegex)) {
                marks = Integer.parseInt(marksCheck);
                RegexUtil.resetStyle(txtMarks);
            }else {
                RegexUtil.setErrorStyle(false,txtMarks);

            }
        }
        isSaveEnable();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ExamDto> examDtos = examModel.getAllExams();
        HashSet<String> uniqueGrades = new HashSet<>();

        for (ExamDto examDto : examDtos) {
            uniqueGrades.add(examDto.getGrade());
        }
        cmbGrade.getItems().addAll(uniqueGrades);

        refreshPage();
    }


    public void cmbStudentOnAction(ActionEvent actionEvent) {
        student = cmbStudent.getSelectionModel().getSelectedItem();
        isSaveEnable();
    }

    public void cmbStudentOnActionOnKeyPressed(KeyEvent keyEvent) {
    }




    public void refreshPage(){
        btnSave.setVisible(true);
        cmbGrade.getSelectionModel().clearSelection();
        String resultId = resultModel.getNextResultID();
        lblResultID.setText(resultId);

        RegexUtil.resetStyle(txtMarks);

        cmbStudent.getItems().clear();
        cmbExamID.getItems().clear();
        cmbSubject.getItems().clear();
        cmbExamID.getItems().clear();

        txtMarks.clear();
        radioBtnNotPArticipant.setSelected(false);
        txtMarks.setDisable(false);
        txtMarks.setText("");


        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnReset.setDisable(true);

    }


    public void isSaveEnable() {
        boolean checkID = lblResultID != null && lblResultID.getText().isEmpty();
        boolean checkGrade = cmbGrade.getValue() == null;
        boolean checkSubject = cmbSubject.getValue() == null;
        boolean checkExamID = cmbExamID.getValue() == null;
        boolean checkStudent = cmbStudent.getValue() == null;

        boolean checkMarks = true;
        if (!txtMarks.isDisable()) {
            checkMarks = txtMarks.getText().matches(marksRegex);
        }

        boolean isFormValid = !checkID && !checkGrade && !checkSubject && !checkExamID && !checkStudent && checkMarks;

        btnSave.setDisable(!isFormValid);

        boolean isResetEnabled = !(lblResultID.getText().isEmpty() && cmbGrade.getValue() == null && cmbSubject.getValue() == null && cmbExamID.getValue() == null && cmbStudent.getValue() == null && txtMarks.getText().isEmpty());
        btnReset.setDisable(!isResetEnabled);
    }


    public void setDto(ResultDto dto) {
        lblResultID.setText(dto.getResultID());
        cmbGrade.setValue(dto.getGrade());
        cmbSubject.setValue(dto.getSubject());
        cmbStudent.setValue(dto.getStudent());
        cmbExamID.setValue(dto.getExam());
        if (dto.getStatus().equalsIgnoreCase("Absent")){
            radioBtnNotPArticipant.setSelected(true);
            txtMarks.setDisable(true);
        }else {
            radioBtnNotPArticipant.setSelected(false);
            txtMarks.setDisable(false);
        }
        txtMarks.setText(String.valueOf(dto.getMarks()));
    }

    public void setButtons() {
        btnSave.setVisible(false);
        btnUpdate.setDisable(false);
        btnReset.setDisable(false);
        btnDelete.setDisable(false);
    }
}
