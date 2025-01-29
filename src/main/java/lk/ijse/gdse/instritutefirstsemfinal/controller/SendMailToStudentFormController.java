package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.StudentBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.StudentModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class SendMailToStudentFormController implements Initializable {

//    StudentModel studentModel = new StudentModel();

    private StudentOtherInfoTableFormController studentOtherInfoTableFormController;

    StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOType.STUDENT);


    @FXML
    private Button btnSend;

    @FXML
    private ComboBox<String> cmbStudentID;

    @FXML
    private Label lblClear;

    @FXML
    private Label lblGmail;

    @FXML
    private JFXTextArea tareaBody;

    @FXML
    private Pane teacherMailPane;

    @FXML
    private TextField txtSubject;

    private String studentEmail;
    @FXML
    void btnSendOnClicked(ActionEvent event) {
        txtSubject.requestFocus();
        String subject = txtSubject.getText();
        String body = tareaBody.getText();
        if (cmbStudentID.getSelectionModel().getSelectedItem() == null || cmbStudentID.getSelectionModel().getSelectedItem().equals("")) {
            AlertUtil.informationAlert(SendMailToTeacherFormController.class,null,true,"Please choose an ID");
            return;
        }
        if (subject.isEmpty()){
            AlertUtil.informationAlert(SendMailToTeacherFormController.class,null,true,"Subject cannot be empty");
            return;
        }
        String from = "manuthlakdiv2006@gmail.com";
        String host = "smtp.gmail.com";
        String username = "manuthlakdiv2006@gmail.com";
        String password = "aefq cods wrot ktgp";



        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(studentEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            AlertUtil.informationAlert(SendMailToTeacherFormController.class,null,true,"Mail sent successfully");
            System.out.println(studentEmail);
            cmbStudentID.getSelectionModel().clearSelection();
            lblGmail.setText("");
            txtSubject.setText("");
            tareaBody.clear();
//            btnSend.setDisable();

        }catch (MessagingException e){
            AlertUtil.informationAlert(ForgotPasswordFormController.class,null,false,"Failed to send OTP code!\nPlease check your Internet connection.");
        }

    }

    @FXML
    void cmbStudentIDOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String studentID = cmbStudentID.getSelectionModel().getSelectedItem();

        ArrayList<StudentDto> students = studentBO.getStudentAllDetailsByID(studentID);

        for (StudentDto studentDto : students) {
            studentEmail = studentDto.getEmail();
            lblGmail.setText(studentEmail);
        }

    }

    @FXML
    void lblClearOnClicked(MouseEvent event) {
        tareaBody.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbStudentID.requestFocus();
        ArrayList<StudentDto> students = null;
        try {
            students = studentBO.getAllStudentsWithLearnSubjects();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (StudentDto student : students) {
            cmbStudentID.getItems().add(student.getId());
        }

    }

    public void setTableStudentController(StudentOtherInfoTableFormController studentOtherInfoTableFormController) {
        this.studentOtherInfoTableFormController = studentOtherInfoTableFormController;

    }
}
