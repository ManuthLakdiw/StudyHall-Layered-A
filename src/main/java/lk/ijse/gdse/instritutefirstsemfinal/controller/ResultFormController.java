package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.*;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ResultFormController implements Initializable {

//    ExamModel examModel = new ExamModel();
//    ResultModel resultModel = new ResultModel();
//    SubjectModel subjectModel = new SubjectModel();
//    GradeModel gradeModel = new GradeModel();

    ResultBO resultBO = (ResultBO) BOFactory.getInstance().getBO(BOFactory.BOType.RESULT);

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    ExamBO examBO = (ExamBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXAM);

    SubjectBO subjectBO = (SubjectBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUBJECT);

    GradeBO gradeBO = (GradeBO) BOFactory.getInstance().getBO(BOFactory.BOType.GRADE);

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
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        resultId = lblResultID.getText();

        Optional<ButtonType> buttonType = AlertUtil.ConfirmationAlert("Are you sure?,This action will permanently delete the selected result.",ButtonType.NO,ButtonType.YES);

        if (buttonType.get() == ButtonType.YES) {
//            boolean isDeleted = resultModel.deleteResult(resultId);
            boolean isDeleted = resultBO.deleteResult(resultId);

            if (isDeleted) {
                AlertUtil.informationAlert(this.getClass(), null, true, "Result deleted successfully.");
                lblResultID.setText(resultBO.generateNewResultID());
                refreshPage();
                resultTableFormController.loadTable();
            } else {
                AlertUtil.informationAlert(this.getClass(), null, true, "Result deletion failed.");
            }
        }
    }


    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
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

        boolean isSaved = resultBO.saveResult(dto);

        if (isSaved) {
            AlertUtil.informationAlert(this.getClass(),null,false,"Result saved Successfully");
            lblResultID.setText(resultBO.generateNewResultID());
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
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
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

        ArrayList<ResultDto> resultDtos = resultBO.checkExitingResult(lblResultID.getText());


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

        for (ResultDto resultDto : resultDtos) {

            boolean isSameGrade = resultDto.getGrade().equals(cmbGrade.getValue());
            boolean isSameSubject = resultDto.getSubject().equals(cmbSubject.getValue());
            boolean isSameExamID = resultDto.getExam().equals(cmbExamID.getValue());
            boolean isSameStudent= resultDto.getStudent().equals(cmbStudent.getValue());
            boolean isSameMarks = resultDto.getMarks() == marks;
            boolean isSameGradeArchived = resultDto.getGradeArchieved().equals(gradeArchived);
            boolean isSameStatus = resultDto.getStatus().equals(status);

            if (isSameGrade && isSameSubject && isSameExamID && isSameStudent && isSameMarks && isSameGradeArchived && isSameStatus) {
                AlertUtil.informationAlert(this.getClass(), null, true, "No changes detected. Update is not necessary.");
                return;
            }
        }



        boolean isUpdated = resultBO.updateResult(dto);

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
    private void cmbGradeOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();

        cmbSubject.getItems().clear();
        cmbExamID.getItems().clear();
        cmbStudent.getItems().clear();
        lblExamIdDesc.setText("");

        if (selectedGrade != null) {
            String gradeId = gradeBO.getGradeIdFromName(selectedGrade);

            String[] subjectIDs = examBO.getExamSubjectsByGrade(gradeId);
            Set<String> subjectNamesSet = new HashSet<>();

            for (String subjectID : subjectIDs) {
                String subjectName = subjectBO.getSubjectNameFromID(subjectID);
                if (subjectName != null) {
                    subjectNamesSet.add(subjectName);
                }
            }

            cmbSubject.getItems().addAll(subjectNamesSet);

            if (!subjectNamesSet.isEmpty()) {
                String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();
                if (selectedSubject != null) {
                    String subjectId = subjectBO.getSubjectIDFromName(selectedSubject);
                    ArrayList<String> studentNames = studentBO.getStudentsByGradeAndSubject(gradeId, subjectId);
                    if (studentNames != null && !studentNames.isEmpty()) {
                        cmbStudent.getItems().addAll(studentNames);
                    }
                }
            }
        }
        isSaveEnable();
    }


    @FXML
    void cmbSubjectOnAction(ActionEvent event) throws SQLException {
        String selectedSubject = cmbSubject.getSelectionModel().getSelectedItem();
        String selectedGrade = cmbGrade.getSelectionModel().getSelectedItem();

        cmbStudent.getItems().clear();
        cmbExamID.getItems().clear();
        lblExamIdDesc.setText("");

        if (selectedSubject != null && selectedGrade != null) {
            String subjectId = subjectBO.getSubjectIDFromName(selectedSubject);
            String gradeId = gradeBO.getGradeIdFromName(selectedGrade);

            ArrayList<String> studentNames = studentBO.getStudentsByGradeAndSubject(gradeId, subjectId);
            if (studentNames != null && !studentNames.isEmpty()) {
                cmbStudent.getItems().addAll(studentNames);
            }

            String[] examIDs = examBO.getExamIDsUsingSubject(subjectId);
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


    public void cmbExamIDOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedExamID = cmbExamID.getSelectionModel().getSelectedItem();

        if (selectedExamID != null) {
            ArrayList<ExamDto> examDtos = examBO.getAllExamsAndApplicableSubjectNames();

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
        ArrayList<ExamDto> examDtos = null;
        try {
            examDtos = examBO.getAllExamsAndApplicableSubjectNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HashSet<String> uniqueGrades = new HashSet<>();

        for (ExamDto examDto : examDtos) {
            uniqueGrades.add(examDto.getGrade());
        }
        cmbGrade.getItems().addAll(uniqueGrades);

        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void cmbStudentOnAction(ActionEvent actionEvent) {
        student = cmbStudent.getSelectionModel().getSelectedItem();
        isSaveEnable();
    }

    public void cmbStudentOnActionOnKeyPressed(KeyEvent keyEvent) {
    }




    public void refreshPage() throws SQLException, ClassNotFoundException {
        btnSave.setVisible(true);
        cmbGrade.getSelectionModel().clearSelection();
        String resultId = resultBO.generateNewResultID();
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
