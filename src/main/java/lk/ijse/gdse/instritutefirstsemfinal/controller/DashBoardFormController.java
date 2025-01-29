package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.ExamBO;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.StudentBO;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.TeacherBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

//    StudentModel studentModel = new StudentModel();
//    TeacherModel teacherModel = new TeacherModel();
//    ExamModel examModel = new ExamModel();

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);

    TeacherBO teacherBO = (TeacherBO) BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);

    ExamBO examBO = (ExamBO) BOFactory.getInstance().getBO(BOFactory.BOType.EXAM);


    @FXML
    private HBox hBox2;

    @FXML
    private Label lblExam;

    @FXML
    private Label lblStudent;

    @FXML
    private Label lblTeacher;

    @FXML
    private JFXTextArea tareaNote;

    @FXML
    private Label lblNextExam;

    @FXML
    private TextArea txtNote;

    String note;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            lblExam.setText(String.valueOf(examBO.getExamCount()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            lblStudent.setText(String.valueOf(studentBO.getStudentCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            lblTeacher.setText(String.valueOf(teacherBO.getTeacherCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtNote.setText(note);

        ExamDto nextExam = null;
        try {
            nextExam = examBO.getNextUpComingExam();
            if (nextExam == null) {
                lblNextExam.setText("No upcoming exams.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (nextExam != null) {
            lblNextExam.setText(formatExamDetails(nextExam));
        } else {
            lblNextExam.setText("No upcoming exams.");
        }

    }

//    public void tAreaNoteKeyTyped(KeyEvent keyEvent) {
//        note = tareaNote.getText();
//    }

    private String formatExamDetails(ExamDto exam) {

        if (exam == null) {
            lblNextExam.setText("No upcoming exams.");
        }
        return String.format(
                " %s : %s : %s : %s : %s",
                exam.getExamId(),
                exam.getGrade(),
                exam.getSubject(),
                exam.getExamDate(),
                exam.getExamType()
        );
    }

    public void txtNoteOnKeyTyped(KeyEvent keyEvent) {
        note = txtNote.getText();
    }
}
