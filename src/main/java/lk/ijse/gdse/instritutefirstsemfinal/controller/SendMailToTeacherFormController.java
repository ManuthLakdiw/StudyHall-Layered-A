package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.TeacherBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.model.TeacherModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lombok.Setter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;


public class SendMailToTeacherFormController implements Initializable {

    private TeacherTableFormController teacherTableFormController;


//    TeacherModel teacherModel = new TeacherModel();

    TeacherBO teacherBO = (TeacherBO) BOFactory.getInstance().getBO(BOFactory.BOType.TEACHER);

    @FXML
    private Button btnSend;

    @FXML
    private Label lblClear;

    @FXML
    private ComboBox<String> cmbTeacherID;

    @FXML
    private JFXTextArea tareaBody;

    @FXML
    private Pane teacherMailPane;

    @FXML
    private Label lblGmail;

    @FXML
    private TextField txtSubject;

    private String teacherEmail;


    @FXML
    void btnSendOnClicked(ActionEvent event) {
        txtSubject.requestFocus();
        String subject = txtSubject.getText();
        String body = tareaBody.getText();
        if (cmbTeacherID.getSelectionModel().getSelectedItem() == null || cmbTeacherID.getSelectionModel().getSelectedItem().equals("")) {
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(teacherEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            AlertUtil.informationAlert(SendMailToTeacherFormController.class,null,true,"Mail sent successfully");
            cmbTeacherID.getSelectionModel().clearSelection();
            lblGmail.setText("");
            txtSubject.setText("");
            tareaBody.clear();
//            btnSend.setDisable();

        }catch (MessagingException e){
            AlertUtil.informationAlert(ForgotPasswordFormController.class,null,false,"Failed to send OTP code!\nPlease check your Internet connection.");
        }



    }


    @FXML
    void lblClearOnClicked(MouseEvent event) {
        tareaBody.clear();
    }

    public void setTableTeacherFormController(TeacherTableFormController teacherTableFormController) {
        this.teacherTableFormController = teacherTableFormController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbTeacherID.requestFocus();
        ArrayList<TeacherDto> teachers = null;
        try {
            teachers = teacherBO.getAllTeachersAndRelatedGrades();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (TeacherDto teacherDto : teachers) {
            cmbTeacherID.getItems().add(teacherDto.getTeacherId());
        }

    }

    public void cmbTeacherIDOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedTeacherId = cmbTeacherID.getValue();


        teacherEmail = teacherBO.getTeacherEmail(selectedTeacherId);
        lblGmail.setText(teacherEmail);
    }
}

