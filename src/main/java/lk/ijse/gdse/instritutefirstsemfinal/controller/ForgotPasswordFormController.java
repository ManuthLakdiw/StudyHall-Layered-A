package lk.ijse.gdse.instritutefirstsemfinal.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class ForgotPasswordFormController {
    private final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    static String gmail;
    static String otp;

//    private final UserModel model = new UserModel();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    private Button btnGetdigit;

    @FXML
    private ImageView forgotImage;

    @FXML
    private TextField txtEmail;

    @FXML
    private Pane forgotPasswordFormPane;


    @FXML
    private void btnGetDigitOnAction(ActionEvent eve) throws SQLException, ClassNotFoundException {
        String email = txtEmail.getText().trim();
        boolean isValidEmail = email.matches(emailRegex);


        List<Object> result = userBO.checkGmailInDB(email);
        boolean isEmailExists = (boolean) result.get(0);
        gmail = (String) result.get(1);
        String userName = (String) result.get(2);

        txtEmail.setStyle("-fx-border-color: #03045E; -fx-border-width: 1px; -fx-border-radius: 5; -fx-background-color: transparent;");

        if (!isValidEmail) {
            RegexUtil.setErrorStyle(true,txtEmail);
            AlertUtil.informationAlert(ForgotPasswordFormController.class,null,true,"You must enter a valid email.");
            txtEmail.clear();
//            RegexUtil.resetStyle(txtEmail);
            txtEmail.requestFocus();
        } else {
            if (isEmailExists) {
                try {
                    Random rand = new Random();
                    int min = 1234;
                    int max = 9999;
                    otp = String.valueOf(rand.nextInt(max - min + 1) + min);


                    String subject = "Password Reset OTP Code!";
                    String body = "Hello, "+userName+"\n\n"
                            + "Please use the following OTP to reset your password:\n\n"
                            + "╔══════════════════════════════╗\n"
                            + "║         " + otp + "          ║\n"
                            + "╚══════════════════════════════╝\n\n"
                            + "If you did not request a password reset, please ignore this email.\n\n"
                            + "Thank You!!!";

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



                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(from));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gmail));
                    message.setSubject(subject);
                    message.setText(body);
                    Transport.send(message);

                    btnGetdigit.setText("Done ✔");


                    Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "OTP code sent successfully!", ButtonType.CLOSE, ButtonType.OK);
                    successAlert.setTitle("Confirmation");
                    successAlert.setHeaderText(null);
                    successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/assets/style/Style.css").toExternalForm());
                    Optional<ButtonType> buttonType = successAlert.showAndWait();

                    if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                        NavigationUtil.loadPane(ForgotPasswordFormController.class,forgotPasswordFormPane,"Forgot Password[Email Verification]","/view/resetPasswordConfirmForm.fxml");
                    }else if (buttonType.isPresent() && buttonType.get() == ButtonType.CLOSE) {
                        btnGetdigit.setText("Get 4-digit code");
                    }

                } catch (MessagingException e) {
                    AlertUtil.informationAlert(ForgotPasswordFormController.class,null,false,"Failed to send OTP code!\nPlease check your Internet connection.");
                }
            } else {
                RegexUtil.setErrorStyle(false,txtEmail);
                AlertUtil.informationAlert(ForgotPasswordFormController.class,null,false,email +  "  is not in the database. Try again!");
            }
        }
    }



    @FXML
    private void txtEmailOnkeyType(KeyEvent keyEvent) {
        String checkTxtEmail = keyEvent.getText();

        if (checkTxtEmail.isEmpty()) {
            RegexUtil.resetStyle(txtEmail);
        }
    }






}



