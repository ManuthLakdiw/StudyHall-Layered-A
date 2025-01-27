package lk.ijse.gdse.instritutefirstsemfinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.UserTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.UserModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UserTableFormController implements Initializable {

    UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);



    @FXML
    private Pane AdminPane;

    @FXML
    private Button btnAddUser;

    @FXML
    private TableColumn<UserTm, String> colContactNumber;

    @FXML
    private TableColumn<UserTm, String> colEmailAddress;

    @FXML
    private TableColumn<UserTm, String> colPassword;

    @FXML
    private TableColumn<UserTm, String> colUserName;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private TextField txtFindUser;


/////////////////////////////////////////////////////////////////////////////////////////


//    UserModel userModel = new UserModel();
    UserFormController userFormController = new UserFormController();
    boolean isClicked = false;
    FilteredList filter;


    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        isClicked = true;
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/userForm.fxml"));
                Parent load = loader.load();

                UserFormController controller = loader.getController();

                controller.setUserTableFormController(this);

                this.userFormController = controller;

                Stage stage = new Stage();
                stage.initModality(null);
                stage.setTitle("User Form");
                stage.setScene(new Scene(load));

                stage.initModality(null);

                stage.initOwner(btnAddUser.getScene().getWindow());

                stage.setResizable(false);


                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void tblUserOnClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        UserTm selectedItem = tblUser.getSelectionModel().getSelectedItem();
        if (isClicked){
            if (selectedItem != null) {
                UserDto userDto = new UserDto(
                        selectedItem.getUsName(),
                        selectedItem.getUsPassword(),
                        selectedItem.getUsPhone(),
                        selectedItem.getUsEmail()
                );
                userFormController.setUserDto(userDto);
                userFormController.tableOnClickeButton();
            }
        }
        if (event.getButton() == MouseButton.SECONDARY) {
            Optional<ButtonType> result = AlertUtil.ConfirmationAlert("Do you want to delete user : " + selectedItem.getUsName(), ButtonType.YES, ButtonType.NO);
            if (result.isPresent()) { // Check if the user clicked a button
                if (result.get() == ButtonType.YES) {
                    Optional<ButtonType> confirmDelete = AlertUtil.ConfirmationAlert("Are you sure!", ButtonType.YES, ButtonType.NO);
                    if (confirmDelete.isPresent()) {
                        if (confirmDelete.get() == ButtonType.YES) {
//                            boolean isDeleted = userModel.deleteUser(selectedItem.getUsName());
                            boolean isDeleted =  userBO.deleteUser(selectedItem.getUsName());
                            if (isDeleted) {
                                AlertUtil.informationAlert(UserFormController.class,null,true,"User deleted successfully");
                                loadUserTable();
                            }else {
                                AlertUtil.informationAlert(UserFormController.class,null,true,"User could not be deleted successfully");
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserName.setCellValueFactory(new PropertyValueFactory<>("usName"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("usEmail"));
        colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("usPhone"));

        try {
            loadUserTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    

    public void loadUserTable() throws SQLException, ClassNotFoundException {
        ArrayList<UserDto> userDtos = userBO.getAllUsers();
        ObservableList<UserTm> userTms = FXCollections.observableArrayList();


        for (UserDto userDto : userDtos) {
            UserTm userTm = new UserTm(
                    userDto.getUsName(),
                    userDto.getUsPassword(),
                    userDto.getUsPhone(),
                    userDto.getUsEmail()
            );
            userTms.add(userTm);
        }
        tblUser.setItems(userTms);

        filter = new FilteredList(userTms,e -> true);
    }


    public void txtFindUserOnKeyRelesed(KeyEvent keyEvent) {
        txtFindUser.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super UserTm>) (UserTm userTm)->{

                if (newValue.isEmpty() || newValue== null){
                    return true;
                }else if (userTm.getUsName().contains(newValue) ){
                    return true;
                }

                return false;
            });

            SortedList sortedList = new SortedList(filter);
            sortedList.comparatorProperty().bind(tblUser.comparatorProperty());
            tblUser.setItems(sortedList);



        });
    }


}

