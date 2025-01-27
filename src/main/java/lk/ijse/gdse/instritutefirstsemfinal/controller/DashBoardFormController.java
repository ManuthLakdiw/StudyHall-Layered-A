package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.ExamModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardFormController implements Initializable {

    StudentModel studentModel = new StudentModel();
    TeacherModel teacherModel = new TeacherModel();
    ExamModel examModel = new ExamModel();

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

        lblExam.setText(String.valueOf(examModel.getExamCount()));
        lblStudent.setText(String.valueOf(studentModel.getStudentCount()));
        lblTeacher.setText(String.valueOf(teacherModel.getTeacherCount()));
        txtNote.setText(note);

        ExamDto nextExam = examModel.getNextExam();
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
