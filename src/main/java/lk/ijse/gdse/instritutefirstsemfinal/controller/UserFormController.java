package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

//    UserModel userModel = new UserModel();
    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    private UserTableFormController userTableFormController;

    public void setUserTableFormController(UserTableFormController userTableFormController) {
        this.userTableFormController = userTableFormController;
    }


    @FXML
    private Pane AdminPane;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtEmailAddress;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblUserNameStatus;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label llblConfirmPassword;


    ////////////////////////////////////////////////////////////////////////////////////////////////

    String userName;
    String newPassword;
    String confirmPassword;
    String phoneNumber;
    String email;

    private String usernameRegex = "^[a-zA-Z][a-zA-Z0-9_-]{3,8}[a-zA-Z0-9]$";
    private String passwordWeakRegex = "^(?=.{1,})([a-zA-Z]+|[0-9]+|[^a-zA-Z0-9]+)$";
    private String passwordMediumRegex = "^(?=.*[a-zA-Z])(?=.*[0-9]).{4,}$";
    private String passwordStrongRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{6,}$";
    private String phoneNumberRegex = "^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$";
    private String emailRegex = "[\\w]*@*[a-z]*\\.*[\\w]{5,}(\\.)*(com)*(@gmail\\.com)";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshPage();

    }


    //////////////////////////////////// BUTTONS //////////////////////////////////

    @FXML
    private void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!btnSave.isDisable()) {
            UserDto userDto = new UserDto(userName,newPassword,email,phoneNumber);

//            boolean isSaved = userModel.saveUser(userDto);
            boolean isSaved = userBO.saveUser(userDto);

            if (isSaved) {
                AlertUtil.informationAlert(UserFormController.class,null,true,"User Saved Successfully");
                refreshPage();
                userTableFormController.loadUserTable();


            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"User Saved Failed!");
            }
        }else {
            System.out.println("save button is disabled");
        }
    }

    @FXML
    private void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        btnSave.setVisible(true);
        txtUserName.setDisable(false);
        txtUserName.requestFocus();
        lblUserNameStatus.setVisible(true);
        lblUserNameStatus.setStyle("-fx-text-fill: #4a4848;");
        lblUserNameStatus.setText("Username should be Unique");
        lblNewPassword.setText("");
        llblConfirmPassword.setText("");
        lblPhoneNumber.setText("");
        lblEmail.setText("");
        RegexUtil.resetStyle(txtUserName,txtConfirmPassword,txtNewPassword,txtContactNumber,txtEmailAddress);
        refreshPage();
        userTableFormController.loadUserTable();

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        RegexUtil.resetStyle(txtUserName,txtConfirmPassword,txtNewPassword,txtContactNumber,txtEmailAddress);
        btnUpdate.setDisable(false);

        if (!btnUpdate.isDisable()) {
            userName = txtUserName.getText();
            newPassword = txtNewPassword.getText();
            email = txtEmailAddress.getText();
            phoneNumber = txtContactNumber.getText();

            boolean check = true;

            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(txtUserName);
            fields.add(txtNewPassword);
            fields.add(txtEmailAddress);
            fields.add(txtContactNumber);
            fields.add(txtConfirmPassword);

            for (TextField field : fields) {
                if (field.getText().isEmpty()) {
                    AlertUtil.informationAlert(UserFormController.class, null, true, "Fields cannot be empty. Please fill in all fields.");
                    RegexUtil.setErrorStyle(false,field);
                    return;
                }
            }


            if (!phoneNumber.matches(phoneNumberRegex)) {
                RegexUtil.setErrorStyle(false,txtContactNumber);
                AlertUtil.informationAlert(UserFormController.class, null, false, "Phone number format is incorrect. It must start with '0' and be 10 digits.");
                return;
            }


            if (!email.matches(emailRegex)) {
                RegexUtil.setErrorStyle(false,txtEmailAddress);
                AlertUtil.informationAlert(UserFormController.class, null, false, "Email format is incorrect. Please provide a valid email.");
                return;
            }


            if (!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
                RegexUtil.setErrorStyle(false,txtConfirmPassword);
                AlertUtil.informationAlert(UserFormController.class, null, true, "Passwords do not match.");
                return;


            } else if (newPassword.matches(passwordWeakRegex)) {
                AlertUtil.informationAlert(UserFormController.class, null, false, "Password does not meet the required strength. Please enter a valid password.");
                return;
            }


//            UserDto existingUser = userModel.getUserByUserName(userName);
            UserDto existingUser = userBO.searchUser(userName);



            if (existingUser != null) {

                if (newPassword.equals(existingUser.getUsPassword()) &&
                        email.equals(existingUser.getUsEmail()) &&
                        phoneNumber.equals(existingUser.getUsPhone())) {
                    check = false;
                    AlertUtil.informationAlert(UserFormController.class, null, false, "No changes detected. Update is not necessary.");
                } else {
//                    UserDto userDto = new UserDto(userName, newPassword, email, phoneNumber);
//                    boolean isUpdate = userModel.updateUser(userDto);
                    boolean isUpdate = userBO.updateUser(new UserDto(userName, newPassword, email, phoneNumber));



                    if (isUpdate) {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "User : " + userName + " updated successfully");
                        btnUpdate.setDisable(false);
                        btnSave.setVisible(true);
                        txtUserName.setDisable(false);
                        lblUserNameStatus.setVisible(true);
                        lblUserNameStatus.setStyle("-fx-text-fill: #4a4848;");
                        lblUserNameStatus.setText("Username should be Unique");
                    } else {
                        AlertUtil.informationAlert(UserFormController.class, null, true, "User : " + userName + " update failed!");
                    }
                }
            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "User not found!");
            }

            if (check) {
                refreshPage();
                userTableFormController.loadUserTable();
            }
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        RegexUtil.resetStyle(txtUserName,txtConfirmPassword,txtNewPassword,txtContactNumber,txtEmailAddress);
        String userName = txtUserName.getText();
        Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure delete this User?", ButtonType.NO, ButtonType.YES);
        successAlert.setTitle("Confirmation");
        successAlert.setHeaderText(null);
        successAlert.getDialogPane().getStylesheets().add(getClass().getResource("/assets/style/Style.css").toExternalForm());
        Optional<ButtonType> buttonType = successAlert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            btnDelete.setDisable(false);
            btnReset.setDisable(false);
            btnSave.setVisible(true);
            txtUserName.setDisable(false);
//            boolean isDeleted = userModel.deleteUser(userName);
            boolean isDeleted = userBO.deleteUser(userName);


            if (isDeleted) {
                AlertUtil.informationAlert(UserFormController.class,null,true,"User deleted successfully");
                refreshPage();
                userTableFormController.loadUserTable();

            }else {
                AlertUtil.informationAlert(UserFormController.class,null,true,"User could not be deleted!");
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////





    ///////////////

    @FXML
    void txtConfirmPasswordOnKeyPressed(KeyEvent event) {
        if (txtConfirmPassword.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                RegexUtil.resetStyle(txtConfirmPassword);
                txtNewPassword.requestFocus();
                txtNewPassword.positionCaret(txtNewPassword.getText().length());
            }
        }else if (event.getCode() == KeyCode.ENTER) {
            txtContactNumber.requestFocus();
            txtContactNumber.positionCaret(txtContactNumber.getText().length());

        }
    }

    @FXML
    void txtConfirmPasswordOnKeyTyped(KeyEvent event) {
        llblConfirmPassword.setVisible(true);
        confirmPassword = txtConfirmPassword.getText();


        if (txtConfirmPassword.getText().isEmpty() && !txtNewPassword.getText().isEmpty()) {
            isAnyFieldFilled();
            llblConfirmPassword.setText("Confirm your new password ⇪");
            llblConfirmPassword.setTextFill(Color.web("#4a4848"));
            RegexUtil.resetStyle(txtConfirmPassword);
        }

        else if (confirmPassword.equals(newPassword)) {
            RegexUtil.resetStyle(txtConfirmPassword);
            llblConfirmPassword.setText("Password confirmed ✔︎");
            llblConfirmPassword.setTextFill(Color.GREEN);
        }

        else {
            RegexUtil.setErrorStyle(false, txtConfirmPassword);
            llblConfirmPassword.setText("Passwords do not match! ⛔");
            llblConfirmPassword.setTextFill(Color.RED);
        }

        isSaveEnable();

    }





    /////////////////

    @FXML
    void txtContactNumberOnKeyPressed(KeyEvent event) {
        if (txtContactNumber.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                RegexUtil.resetStyle(txtContactNumber);
                txtConfirmPassword.requestFocus();
                txtConfirmPassword.positionCaret(txtConfirmPassword.getText().length());
            }
        }else if (event.getCode() == KeyCode.ENTER) {
            txtEmailAddress.requestFocus();
            txtEmailAddress.positionCaret(txtEmailAddress.getText().length());
        }
    }

    @FXML
    void txtContactNumberOnKeyTyped(KeyEvent event) throws SQLException, ClassNotFoundException {
        lblPhoneNumber.setVisible(true);
        phoneNumber = txtContactNumber.getText();
        btnReset.setDisable(false);
        lblPhoneNumber.setStyle("-fx-text-fill: #4a4848;");
        ArrayList<UserDto> users = userBO.getAllUsers();
        ArrayList<String> userContactNumbers = new ArrayList<>();

        for (UserDto userDto : users) {
            userContactNumbers.add(userDto.getUsPhone());
        }

        if (phoneNumber.isEmpty()) {
            btnReset.setDisable(true);
            lblPhoneNumber.setText("");
            RegexUtil.resetStyle(txtContactNumber);
            isAnyFieldFilled();
            return;
        }

        boolean contactExists = false;
        for (String existingContactNumber : userContactNumbers) {
            if (existingContactNumber.equals(phoneNumber)) {
                contactExists = true;
                break;
            }
        }

        if (contactExists) {
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber already exists!");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        } else if (phoneNumber.matches(phoneNumberRegex)) {
            lblPhoneNumber.setText("");
            RegexUtil.resetStyle(txtContactNumber);
        } else if (!phoneNumber.matches(phoneNumberRegex)){
            lblPhoneNumber.setStyle("-fx-text-fill: red");
            lblPhoneNumber.setText("ContactNumber must be 10 numbers and start with \"0\"");
            RegexUtil.setErrorStyle(false, txtContactNumber);
        }

        isSaveEnable();



    }





    ///////////////////

    @FXML
    void txtEmailAddressOnKeyPressed(KeyEvent event) {
        if (txtEmailAddress.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                RegexUtil.resetStyle(txtEmailAddress);
                txtContactNumber.requestFocus();
                txtContactNumber.positionCaret(txtContactNumber.getText().length());
            }
        }if (event.getCode() == KeyCode.ENTER) {
            txtUserName.requestFocus();
            txtUserName.positionCaret(txtUserName.getText().length());
        }

    }

    @FXML
    private void txtEmailAddressOnKeyTyped(KeyEvent event) throws SQLException, ClassNotFoundException {
        lblEmail.setVisible(true);
        email = txtEmailAddress.getText();
        btnReset.setDisable(false);
        ArrayList<UserDto> users = userBO.getAllUsers();
        ArrayList<String> userEmails = new ArrayList<>();

        for (UserDto userDto : users) {
            userEmails.add(userDto.getUsEmail());
        }


        if (email.isEmpty()) {
            btnReset.setDisable(true);
            lblEmail.setText("");
            RegexUtil.resetStyle(txtEmailAddress);
            isAnyFieldFilled();
            return;
        }

        boolean contactExists = false;
        for (String existingEmailAddress : userEmails) {
            if (existingEmailAddress.equals(email)) {
                contactExists = true;
                break;
            }
        }

        if (contactExists) {
            lblEmail.setStyle("-fx-text-fill: red");
            lblEmail.setText("Email Address already exists!");
            RegexUtil.setErrorStyle(false, txtEmailAddress);
        } else if (email.matches(emailRegex)) {
            lblEmail.setText("");
            RegexUtil.resetStyle(txtEmailAddress);
        } else if (!email.matches(emailRegex)){
            lblEmail.setStyle("-fx-text-fill: red");
            lblEmail.setText("Email must start with letters, numbers, or underscores follow '@' ");
            RegexUtil.setErrorStyle(false, txtEmailAddress);
        }

        isSaveEnable();


    }




    ////////////////

    @FXML
    void txtNewPasswordOnKeyPressed(KeyEvent event) {
        if (txtNewPassword.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                lblNewPassword.setText("");
                llblConfirmPassword.setText("");
                RegexUtil.resetStyle(txtNewPassword,txtConfirmPassword);
                txtUserName.requestFocus();
                txtUserName.positionCaret(txtUserName.getText().length());
            }
        }else if (!txtConfirmPassword.isDisable()) {
            if (event.getCode() == KeyCode.ENTER) {
                txtConfirmPassword.requestFocus();
                txtConfirmPassword.positionCaret(txtConfirmPassword.getText().length());
            }
        }
    }

    @FXML
    private void txtNewPasswordOnKeyTyped(KeyEvent event) {
        lblNewPassword.setVisible(true);
        llblConfirmPassword.setVisible(true);
        newPassword = txtNewPassword.getText();
        btnReset.setDisable(false);

        RegexUtil.resetStyle(txtNewPassword);

        if (newPassword.isEmpty()) {
            btnReset.setDisable(true);
            lblNewPassword.setText(" ");
            llblConfirmPassword.setText(" ");
            txtConfirmPassword.clear();
            txtConfirmPassword.setDisable(true);
            isAnyFieldFilled();
        }

        else if (newPassword.length() > 13) {
            RegexUtil.setErrorStyle(true, txtNewPassword);
            lblNewPassword.setText("Password is too long! > 13");
            lblNewPassword.setTextFill(Color.RED);
            txtConfirmPassword.setDisable(true);
        }

        else if (newPassword.matches(passwordStrongRegex)) {
            lblNewPassword.setText("Strong password ✔︎");
            lblNewPassword.setTextFill(Color.GREEN);
            txtConfirmPassword.setDisable(false);
            llblConfirmPassword.setText("Please confirm your password ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #4a4848");
        }

        else if (newPassword.matches(passwordMediumRegex)) {
            lblNewPassword.setText("Medium password ⚠︎");
            lblNewPassword.setTextFill(Color.BROWN);
            txtConfirmPassword.setDisable(false);
            llblConfirmPassword.setText("Please confirm your password ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #4a4848");
        }

        else if (newPassword.matches(passwordWeakRegex)) {
            lblNewPassword.setText("Weak password ⛔");
            lblNewPassword.setTextFill(Color.RED);
            txtConfirmPassword.setDisable(true);
            llblConfirmPassword.setText("Your new password should be medium or strong for confirmation ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #ff0000");
        }

        else if (!txtConfirmPassword.getText().isEmpty() && !txtNewPassword.getText().isEmpty()) {
//            txtConfirmPassword.clear();
            llblConfirmPassword.setText("Your new password should be medium or strong for confirmation ⇪");
            llblConfirmPassword.setStyle("-fx-text-fill: #ff0000");

            if (newPassword.matches(passwordWeakRegex)) {
                txtConfirmPassword.setDisable(false);
            }
        }
        isSaveEnable();

    }





    //////////////////

    @FXML
    void txtUserNameOnKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            txtNewPassword.requestFocus();
            txtNewPassword.positionCaret(txtNewPassword.getText().length());
        }
    }

    @FXML
    void txtUserNameOnKeyTyped(KeyEvent event) throws SQLException, ClassNotFoundException {
        lblUserNameStatus.setVisible(true);
        lblUserNameStatus.setStyle("-fx-text-fill: #4a4848;");
        btnReset.setDisable(false);
        userName = txtUserName.getText();
        ArrayList<UserDto> users = userBO.getAllUsers();
        ArrayList<String> userNames = new ArrayList<>();

        for (UserDto userDto : users) {
            userNames.add(userDto.getUsName());
        }

        if (userName.isEmpty()) {
            lblUserNameStatus.setStyle("-fx-text-fill: #4a4848");
            lblUserNameStatus.setText("Username should be Unique");
            RegexUtil.resetStyle(txtUserName);
            isAnyFieldFilled();
            return;
        }

        boolean userExists = false;
        for (String existingUserName : userNames) {
            if (existingUserName.equals(userName)) {
                userExists = true;
                break;
            }
        }

        if (userExists) {
            lblUserNameStatus.setStyle("-fx-text-fill: red");
            lblUserNameStatus.setText("Username already exists!");
            RegexUtil.setErrorStyle(false, txtUserName);
        } else if (userName.matches(usernameRegex)) {
            lblUserNameStatus.setStyle("-fx-text-fill: green");
            lblUserNameStatus.setText("Username eligible✔︎");
            RegexUtil.resetStyle(txtUserName);
        } else if (!userName.matches(usernameRegex)){
            lblUserNameStatus.setStyle("-fx-text-fill: red");
            lblUserNameStatus.setText("Username must be between 5 and 10 characters long, starting with a letter and ending with a letter or number.");
            RegexUtil.setErrorStyle(false, txtUserName);
        }

        isSaveEnable();
        isAnyFieldFilled();

    }




    //////////////////////////////////////////////////////////////////////////////////////////

    private void refreshPage()  {
        txtUserName.requestFocus();
        lblUserNameStatus.setVisible(true);
        lblUserNameStatus.setDisable(false);
        txtNewPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);


        txtUserName.setText("");
        txtNewPassword.setText("");
        txtConfirmPassword.setText("");
        txtContactNumber.setText("");
        txtEmailAddress.setText("");

        lblUserNameStatus.setStyle("-fx-text-fill: #4a4848;");
        lblUserNameStatus.setText("Username should be Unique");
        lblNewPassword.setText("");
        llblConfirmPassword.setText("");
        lblPhoneNumber.setText("");
        lblEmail.setText("");

        btnDelete.setDisable(true);
        btnReset.setDisable(true);
        btnSave.setDisable(true);
        btnUpdate.setDisable(true);


    }

    private void isSaveEnable(){
        boolean isUserNameValid = userName != null && !userName.isEmpty() && userName.matches(usernameRegex);
        boolean isPasswordValid = txtConfirmPassword.getText() != null && !txtConfirmPassword.getText().isEmpty()
                && txtConfirmPassword.getText().equals(txtNewPassword.getText())
                && (newPassword != null && (newPassword.matches(passwordStrongRegex) || newPassword.matches(passwordMediumRegex)));
        boolean isContactNumberValid = phoneNumber != null && !phoneNumber.isEmpty() && phoneNumber.matches(phoneNumberRegex);
        boolean isEmailValid = txtEmailAddress.getText() != null && !txtEmailAddress.getText().isEmpty() && txtEmailAddress.getText().matches(emailRegex);

        btnSave.setDisable(!(isUserNameValid && isPasswordValid && isContactNumberValid && isEmailValid));
    }

    private void isAnyFieldFilled() {
        boolean isAnyFieldFilled = (userName != null && !userName.isEmpty())
                || (txtConfirmPassword.getText() != null && !txtConfirmPassword.getText().isEmpty())
                || (newPassword != null && !newPassword.isEmpty())
                || (phoneNumber != null && !phoneNumber.isEmpty())
                || (txtEmailAddress.getText() != null && !txtEmailAddress.getText().isEmpty());

        btnReset.setDisable(!isAnyFieldFilled);
    }

    public void setUserDto(UserDto userDto) {
        if (userDto != null) {
            txtUserName.setText(userDto.getUsName());
            txtNewPassword.setText(userDto.getUsPassword());
            txtConfirmPassword.setText(userDto.getUsPassword());
            txtEmailAddress.setText(userDto.getUsEmail());
            txtContactNumber.setText(userDto.getUsPhone());
        }
    }

    public void tableOnClickeButton() {
        txtUserName.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
        btnSave.setVisible(false);
        btnReset.setDisable(false);
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

        llblConfirmPassword.setVisible(false);
        lblUserNameStatus.setVisible(false);
        lblNewPassword.setVisible(false);
        lblPhoneNumber.setVisible(false);
        lblEmail.setVisible(false);

        RegexUtil.resetStyle(txtUserName, txtConfirmPassword, txtNewPassword, txtContactNumber, txtEmailAddress);
    }


}
