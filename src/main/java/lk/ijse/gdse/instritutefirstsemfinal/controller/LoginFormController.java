package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
//    UserModel userModel = new UserModel();

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    private FontIcon hideEye;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Pane contentPane;

    @FXML
    private Pane loginPageBackgroundPane;

    @FXML
    private FontIcon openEye;

    @FXML
    private PasswordField txtHidePassWord;

    @FXML
    private TextField txtShowPassWord;

    @FXML
    private TextField txtUserName;

    static String uName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
    }



    @FXML
    private void closeEyeOnClickedAction(MouseEvent event) {
        String currentStyle = txtHidePassWord.getStyle();


        txtShowPassWord.setVisible(true);
        openEye.setVisible(true);
        hideEye.setVisible(false);
        txtHidePassWord.setVisible(false);


        txtShowPassWord.setText(txtHidePassWord.getText());
        txtShowPassWord.setStyle(currentStyle);
        txtShowPassWord.requestFocus();
        txtShowPassWord.positionCaret(txtShowPassWord.getText().length());

        txtShowPassWord.setStyle(currentStyle);
    }






    @FXML
    private void openEyeOnClickedAction(MouseEvent event) {

        String currentStyle = txtShowPassWord.getStyle();


        txtShowPassWord.setVisible(false);
        openEye.setVisible(false);
        hideEye.setVisible(true);
        txtHidePassWord.setVisible(true);


        txtHidePassWord.setText(txtShowPassWord.getText());
        txtHidePassWord.setStyle(currentStyle);
        txtHidePassWord.requestFocus();
        txtHidePassWord.positionCaret(txtHidePassWord.getText().length());

        txtHidePassWord.setStyle(currentStyle);
    }




    @FXML
    private void txtUserNameOnKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (txtHidePassWord.isVisible()) {
                txtHidePassWord.requestFocus();
            }else {
                txtShowPassWord.requestFocus();
            }
        }
    }



    @FXML
    private void forgotPassWordOnClicked(MouseEvent mouseEvent) {
                NavigationUtil.loadPane(LoginFormController.class,contentPane,"Forgot Password[Send Email]","/view/forgotPasswordForm.fxml");
    }



    @FXML
    private void passwordFieldOnKeyPressed(KeyEvent keyEvent) {
        if (txtHidePassWord.getText().isEmpty()) {
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                txtUserName.requestFocus();
            }
        }
    }



    @FXML
    private void passwordVisibleFieldOnAction(KeyEvent keyEvent) {
        if(txtShowPassWord.getText().isEmpty()){
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                txtUserName.requestFocus();
            }
        }
    }



    @FXML
    private void btnLoginClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        uName = txtUserName.getText();
        String pWord = txtHidePassWord.isVisible() ? txtHidePassWord.getText() : txtShowPassWord.getText();



        RegexUtil.resetStyle(txtHidePassWord, txtShowPassWord,txtHidePassWord);
//        resetStyle(txtShowPassWord,txtHidePassWord,txtUserName);

        if(uName.isEmpty() || pWord.isEmpty()){
            if(uName.isEmpty() && pWord.isEmpty()){
                /////////////////////////////////////////////////////////// meka ain karanna nacigate wena eka
//                NavigationUtil.loadPane(LoginFormController.class,contentPane," ", "/view/mainLayoutForm.fxml");

                txtUserName.requestFocus();
                RegexUtil.setErrorStyle(true,txtShowPassWord,txtHidePassWord,txtUserName);

                AlertUtil.informationAlert(LoginFormController.class,null,true,"Please fill all the fields");

            }else if (uName.isEmpty()) {
                txtUserName.requestFocus();
                RegexUtil.setErrorStyle(true,txtUserName);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"Please fill the username field.");
            }else {
                txtHidePassWord.requestFocus();
                RegexUtil.setErrorStyle(true,txtHidePassWord,txtShowPassWord);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"Please fill the password field.");
            }
        } else {
//            boolean logging = userModel.verifyUser(uName, pWord);
            boolean logging = userBO.verifyUser(uName,pWord);
            if (!logging) {
                RegexUtil.setErrorStyle(true,txtUserName,txtHidePassWord,txtShowPassWord);
                AlertUtil.informationAlert(LoginFormController.class,null,true,"username and password doesn't match.");
            } else {
                NavigationUtil.loadPane(LoginFormController.class,contentPane," ", "/view/mainLayoutForm.fxml");
            }
        }
    }



    @FXML
    private void txtUserNameOnkeyType(KeyEvent keyEvent) {
        String checkisEmpty = txtUserName.getText();
        if (!checkisEmpty.isEmpty()) {
            RegexUtil.resetStyle(txtUserName);
//            resetStyle(txtUserName);
        }
    }



    @FXML
    private void txtShowPassWordOnKeyType(KeyEvent keyEvent) {
        txtHidePassWord.setText(txtShowPassWord.getText());
        String checkisEmpty = txtShowPassWord.getText();
        if(!checkisEmpty.isEmpty()){
            RegexUtil.resetStyle(txtShowPassWord,txtHidePassWord);
//            resetStyle(txtShowPassWord,txtHidePassWord);
        }
    }



    @FXML
    private void txtHidePassWordOnKeyType(KeyEvent keyEvent) {
        txtShowPassWord.setText(txtHidePassWord.getText());
        String checkisEmpty = txtHidePassWord.getText();
        if(!checkisEmpty.isEmpty()){
            RegexUtil.resetStyle(txtShowPassWord,txtHidePassWord);
//            resetStyle(txtHidePassWord,txtShowPassWord);
        }
    }

}
