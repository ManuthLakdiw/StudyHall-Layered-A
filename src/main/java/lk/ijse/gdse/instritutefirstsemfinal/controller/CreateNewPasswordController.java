package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.NavigationUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateNewPasswordController implements Initializable {

//    private final UserModel user = new UserModel();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    private String newPassword = "";
    private String confirmPassword = "";

    @FXML
    private Button btnResetPassword;

    @FXML
    private Pane createNewPasswordFormPane;

    @FXML
    private Label lblPasswordStatus;

    @FXML
    private Label lblpasswordConfirm;

    @FXML
    private PasswordField txtConfirmHidePassWord;

    @FXML
    private PasswordField txtNewHidePassWord;

    private String passwordWeakRegex = "^(?=.{1,})([a-zA-Z]+|[0-9]+|[^a-zA-Z0-9]+)$";
    private String passwordMediumRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$";
    private String passwordStrongRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$";

    @FXML
    void btnResetPasswordOnClicked(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (newPassword.isEmpty()) {
            AlertUtil.informationAlert(CreateNewPasswordController.class, null, true, "Please enter your new password.");
            return;
        } else if (confirmPassword.isEmpty()) {
            AlertUtil.informationAlert(CreateNewPasswordController.class, null, true, "Please confirm your new password.");
            return;
        } else if (newPassword.equals(confirmPassword)) {
//            boolean isUpdate = user.updateUserPassword(newPassword, ForgotPasswordFormController.gmail);
            boolean isUpdate = userBO.updateUserPassword(newPassword, ForgotPasswordFormController.gmail);

            if (isUpdate) {
                NavigationUtil.loadPane(CreateNewPasswordController.class, createNewPasswordFormPane, "Forgot Password[Reset Success!!!]", "/view/pwResetSuccessForm.fxml");
            }
        } else if (newPassword.length() > 13) {
            AlertUtil.informationAlert(CreateNewPasswordController.class, null, true, "OOPS! Your new password is too long!");
        }
    }

    @FXML
    void txtConfirmHidePassWordOnKeyType(KeyEvent event) {
        confirmPassword = txtConfirmHidePassWord.getText();


        if (txtConfirmHidePassWord.getText().isEmpty() && !txtNewHidePassWord.getText().isEmpty()) {
            lblpasswordConfirm.setText("Confirm your new password ⇪");
            lblpasswordConfirm.setTextFill(Color.web("#03045e"));
            RegexUtil.resetStyle(txtConfirmHidePassWord);
        }

        else if (confirmPassword.equals(newPassword)) {
            RegexUtil.resetStyle(txtConfirmHidePassWord);
            lblpasswordConfirm.setText("Password confirmed ✔︎");
            lblpasswordConfirm.setTextFill(Color.GREEN);
        }

        else {
            RegexUtil.setErrorStyle(false, txtConfirmHidePassWord);
            lblpasswordConfirm.setText("Passwords do not match! ⛔");
            lblpasswordConfirm.setTextFill(Color.RED);
        }
    }

    @FXML
    void txtNewHidePassWordOnKeyType(KeyEvent event) {
        newPassword = txtNewHidePassWord.getText();

        RegexUtil.resetStyle(txtNewHidePassWord);

        if (newPassword.isEmpty()) {
            lblPasswordStatus.setText(" ");
            lblpasswordConfirm.setText(" ");
            txtConfirmHidePassWord.clear();
            txtConfirmHidePassWord.setDisable(true);
        }

        else if (newPassword.length() > 13) {
            RegexUtil.setErrorStyle(false, txtNewHidePassWord);
            lblPasswordStatus.setText("Password is too long! Max length is 13 characters.");
            lblPasswordStatus.setTextFill(Color.RED);
            txtConfirmHidePassWord.setDisable(true);
        }

        else if (newPassword.matches(passwordStrongRegex)) {
            lblPasswordStatus.setText("Strong password ✔︎");
            lblPasswordStatus.setTextFill(Color.GREEN);
            txtConfirmHidePassWord.setDisable(false);
            lblpasswordConfirm.setText("Please confirm your password ⇪");
            lblpasswordConfirm.setStyle("-fx-text-fill: #03045e");
        }

        else if (newPassword.matches(passwordMediumRegex)) {
            lblPasswordStatus.setText("Medium password ⚠︎");
            lblPasswordStatus.setTextFill(Color.BROWN);
            txtConfirmHidePassWord.setDisable(false);
            lblpasswordConfirm.setText("Please confirm your password ⇪");
            lblpasswordConfirm.setStyle("-fx-text-fill: #03045e");
        }

        else if (newPassword.matches(passwordWeakRegex)) {
            lblPasswordStatus.setText("Weak password ⛔");
            lblPasswordStatus.setTextFill(Color.RED);
            txtConfirmHidePassWord.setDisable(true);
            lblpasswordConfirm.setText("Your new password should be medium or strong for confirmation ⇪");
            lblpasswordConfirm.setStyle("-fx-text-fill: #ff0000");
        }

        else if (!txtConfirmHidePassWord.getText().isEmpty() && !txtNewHidePassWord.getText().isEmpty()) {
            lblpasswordConfirm.setText("Your new password should be medium or strong for confirmation ⇪");
            lblpasswordConfirm.setStyle("-fx-text-fill: #ff0000");


            if (newPassword.matches(passwordWeakRegex)) {
                txtConfirmHidePassWord.setDisable(false);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNewHidePassWord.requestFocus();
        txtConfirmHidePassWord.setDisable(true);
    }
}
