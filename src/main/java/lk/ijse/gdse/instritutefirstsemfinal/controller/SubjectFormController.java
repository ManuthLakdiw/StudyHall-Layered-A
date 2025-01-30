package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.bo.BOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.GradeBO;
import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.SubjectBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import lk.ijse.gdse.instritutefirstsemfinal.util.RegexUtil;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class SubjectFormController implements Initializable {

//    SubjectModel subjectModel = new SubjectModel();
//    GradeModel gradeModel = new GradeModel();
    private SubjectTableFormController tableSubjectFormController;

    SubjectBO subjectBO = (SubjectBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUBJECT);
    GradeBO gradeBO = (GradeBO) BOFactory.getInstance().getBO(BOFactory.BOType.GRADE);

    public void setTblSubjectFormController(SubjectTableFormController tableSubjectFormController) {
        this.tableSubjectFormController = tableSubjectFormController;
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
    private CheckComboBox<String> checkComboBoxGrade;

    @FXML
    private Label lblSubID;

    @FXML
    private Label lblSubNameRegex;

    @FXML
    private Pane subjectPane;

    @FXML
    private JFXTextArea tareaDescription;

    @FXML
    private TextField txtSubName;


    ////////////////////////////////////////////////////////////////////////

    String subjectId;
    String subjectName;
    String subjectDescription;

    String subjectRegex = "^[A-Za-z][A-Za-z0-9 .,_]*$";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<GradeDto> grades = null;
        try {
            grades = gradeBO.getAllGrades();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String>  items = new ArrayList<>();

        for (GradeDto grade : grades) {
            items.add(grade.getGradeName());
        }
        checkComboBoxGrade.getItems().addAll(items);

        checkComboBoxGrade.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
            isSaveEnable();
        });
        checkComboBoxGrade.getCheckModel().getCheckedItems().addListener((ListChangeListener<? super String>) change -> {
            // Enable the reset button if any item is selected, disable if no items are selected
            btnReset.setDisable(checkComboBoxGrade.getCheckModel().getCheckedItems().isEmpty());
        });


        try {
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }





    //////////////////////////////////// BUTTONS //////////////////////////////////

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        RegexUtil.resetStyle(txtSubName);
        Optional<ButtonType> buttonType = AlertUtil.ConfirmationAlert("Are you sure you want to delete this Subject?", ButtonType.NO, ButtonType.YES);
        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
//            boolean isDeleted = subjectModel.deleteSubject(lblSubID.getText());
            boolean isDeleted = subjectBO.deleteSubject(lblSubID.getText());

            if (isDeleted) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject deleted successfully.");
                refreshPage();
                tableSubjectFormController.loadSubjectTable();
            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject could not be deleted!");
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<String> selectedItems = checkComboBoxGrade.getCheckModel().getCheckedItems();

        subjectId = lblSubID.getText();
        subjectName = txtSubName.getText();
        subjectDescription = tareaDescription.getText();

//        boolean isExitedSubName = subjectModel.checkExitingSubject(subjectName);

        boolean isExitedSubName = subjectBO.existsSubjectByName(subjectName);

        if (isExitedSubName) {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Subject name already exists!");
            return;
        }

        if (subjectDescription == null || subjectDescription.isEmpty()) {
            subjectDescription = "Not specified Description";
        }

        if (subjectId == null || subjectId.isEmpty() || subjectName == null || subjectName.isEmpty()) {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Subject ID or Subject Name cannot be empty.");
            return;
        }

        if (selectedItems.isEmpty()) {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Please select at least one grade.");
            return;
        }

        if (!btnSave.isDisable()) {
//            List<String> gradeIds = subjectModel.getGradeIdsFromNames(new ArrayList<>(selectedItems));
            List<String> gradeIds = gradeBO.getGradeIdsFromNames(new ArrayList<>(selectedItems));


            if (gradeIds.isEmpty()) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Invalid grade selection.");
                return;
            }

            SubjectDto subjectDto = new SubjectDto(subjectId, subjectName, subjectDescription);

//            boolean isSaved = subjectModel.saveSubjectWithGrades(subjectDto, gradeIds);
            boolean isSaved = subjectBO.saveSubject(subjectDto, gradeIds);

            if (isSaved) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject Saved Successfully");
                refreshPage();
                tableSubjectFormController.loadSubjectTable();
            } else {
                AlertUtil.errorAlert(UserFormController.class, null,  "Subject Save Failed");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        RegexUtil.resetStyle(txtSubName);
        btnUpdate.setDisable(false);
        subjectName = txtSubName.getText();
        subjectDescription = tareaDescription.getText();
        subjectId = lblSubID.getText();



        if (!btnUpdate.isDisable()) {
            subjectId = lblSubID.getText();
            subjectName = txtSubName.getText();
            subjectDescription = tareaDescription.getText();

            if (subjectDescription == null || subjectDescription.isEmpty()) {
                subjectDescription = "Not specified Description";
            }

            // Subject name validation
            if (txtSubName.getText().isEmpty()) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject Name cannot be empty");
                RegexUtil.setErrorStyle(true, txtSubName);
                return;
            }

            if (!subjectName.matches(subjectRegex)) {
                RegexUtil.setErrorStyle(true, txtSubName);
                AlertUtil.informationAlert(UserFormController.class, null, false, "Invalid Subject Name");
                return;
            }

            if (checkComboBoxGrade.getCheckModel().getCheckedItems().isEmpty()) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Grade cannot be empty");
                return;
            }

            ObservableList<String> selectedItems = checkComboBoxGrade.getCheckModel().getCheckedItems();
//            List<String> gradeIds = subjectModel.getGradeIdsFromNames(new ArrayList<>(selectedItems));
            List<String> gradeIds = gradeBO.getGradeIdsFromNames(new ArrayList<>(selectedItems));

            if (isValuesUnchanged(subjectId, subjectName, subjectDescription, selectedItems)) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "No changes detected. Update is not necessary.");
                return;
            }

            // Create the DTO with updated data
            SubjectDto updatedSubjectDto = new SubjectDto(subjectId, subjectName, subjectDescription);

            // Pass only the grade IDs to update the subject
//             boolean isUpdated = subjectModel.updateSubjectWithGrades(updatedSubjectDto, new ArrayList<>(gradeIds));
             boolean isUpdated = subjectBO.updateSubject(updatedSubjectDto,new ArrayList<>(gradeIds));
            System.out.println(isUpdated);

            if (isUpdated) {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject updated successfully");
                refreshPage();
                tableSubjectFormController.loadSubjectTable();
            } else {
                AlertUtil.informationAlert(UserFormController.class, null, true, "Subject update failed!");
            }
        } else {
            AlertUtil.informationAlert(UserFormController.class, null, true, "Please fill in the details correctly!");
        }
    }


    ////////////////////////////////////////////////////////////////////////





    /////////////////////////////
    @FXML
    void tareaDescriptionOnKeyPressed(KeyEvent event) {
        if (tareaDescription.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT) {
                txtSubName.requestFocus();
                txtSubName.positionCaret(txtSubName.getText().length());
            }
        }else{
            if (event.getCode() == KeyCode.ENTER) {
                checkComboBoxGrade.show();
            }
        }
    }

    @FXML
    void tareaDescriptionOnKeyTyped(KeyEvent event) {
        subjectDescription = tareaDescription.getText();
        btnReset.setDisable(false);

        if (subjectDescription.isEmpty()) {
            btnReset.setDisable(true);
            isResetEnable();
        }
        isSaveEnable();

    }



    /////////////////////////////
    @FXML
    void txtSubNameOnKeyTyped(KeyEvent event) {
        subjectName = txtSubName.getText();
        btnReset.setDisable(false);
        RegexUtil.resetStyle(txtSubName);

        if (subjectName.isEmpty()) {
            btnReset.setDisable(true);
            RegexUtil.resetStyle(txtSubName);
            lblSubNameRegex.setText("");
            isResetEnable();
        }else {
            btnReset.setDisable(false);
            if (!subjectName.matches(subjectRegex)) {
                lblSubNameRegex.setStyle("-fx-text-fill: red");
                RegexUtil.setErrorStyle(false,txtSubName);
                lblSubNameRegex.setText("Start with a letter, use only letters, numbers, spaces");
            }else {
                lblSubNameRegex.setText(" ");
                RegexUtil.resetStyle(txtSubName);
            }
        }
        isSaveEnable();

    }

    @FXML
    void txtSubNameOnKeyPressed(KeyEvent event) {
        if (txtSubName.getText().isEmpty()) {
            if (event.getCode() == KeyCode.LEFT){
                checkComboBoxGrade.show();
            }

        }else{
            if (event.getCode() == KeyCode.ENTER){
                tareaDescription.requestFocus();
                tareaDescription.positionCaret(tareaDescription.getText().length());
            }
        }
    }




    /////////////////////////////

    public void refreshPage() throws SQLException, ClassNotFoundException {
//        String nextSubjectID = subjectModel.getNextSubjectID();
        String nextSubjectID = subjectBO.generateNewSubjectID();
        lblSubID.setText(nextSubjectID);
        txtSubName.requestFocus();
        RegexUtil.resetStyle(txtSubName);
        btnDelete.setDisable(true);
        btnReset.setDisable(true);
        btnSave.setDisable(true);
        btnSave.setVisible(true);
        btnUpdate.setDisable(true);
        txtSubName.setText("");
        tareaDescription.setText("");
        checkComboBoxGrade.getCheckModel().clearChecks();

    }

    public void isSaveEnable() {
        // Validate the subject name
        boolean isCheckName = txtSubName != null
                && !txtSubName.getText().isEmpty()
                && txtSubName.getText().matches(subjectRegex);

        // Validate the CheckComboBox (at least one grade is selected)
        boolean isCheckGrade = checkComboBoxGrade != null
                && !checkComboBoxGrade.getCheckModel().getCheckedItems().isEmpty();

        // Enable the Save button only if both conditions are true
        btnSave.setDisable(!(isCheckName && isCheckGrade));
    }

    public void isResetEnable() {
        boolean isCheckName = txtSubName != null && !txtSubName.getText().isEmpty();
        boolean isCheckDescription = tareaDescription != null && !tareaDescription.getText().isEmpty();

        boolean isCheckComboBox = checkComboBoxGrade != null
                && !checkComboBoxGrade.getCheckModel().getCheckedItems().isEmpty();

        // Enable the reset button if any of the conditions are met
        btnReset.setDisable(!(isCheckName || isCheckDescription || isCheckComboBox));
    }

    public void setUserDto(SubjectDto dto) {
        lblSubID.setText(dto.getSubjectId());
        txtSubName.setText(dto.getSubjectName());
        tareaDescription.setText(dto.getSubjectDescription());

        checkComboBoxGrade.getCheckModel().clearChecks();

        String[] subjectGrades = dto.getSubjectGrades();

        if (subjectGrades != null) {
            for (String grade : subjectGrades) {
                if (checkComboBoxGrade.getItems().contains(grade)) {
                    checkComboBoxGrade.getCheckModel().check(grade);
                }
            }
        }
    }

    public void tableOnClickedButton(){
        btnSave.setVisible(false);
        btnReset.setDisable(false);
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        RegexUtil.resetStyle(txtSubName);
        lblSubNameRegex.setText("");
    }



    /////////////////////////////

    private boolean isValuesUnchanged(String subjectId, String subjectName, String subjectDescription, List<String> gradeIds) throws SQLException {
        // Fetch current subject
//        SubjectDto currentSubject = subjectModel.searchExitingSubjectBySubjectID(subjectId);
        SubjectDto currentSubject = subjectBO.getExistsSubjectsAndRelatedGrades(subjectId);


        if (currentSubject == null) {
            return false;
        }

        boolean isNameUnchanged = subjectName.equals(currentSubject.getSubjectName());
        boolean isDescriptionUnchanged = subjectDescription.equals(currentSubject.getSubjectDescription());

        // Compare the grades as sets to ignore order and duplicates
        boolean isGradeUnchanged = new HashSet<>(gradeIds).equals(new HashSet<>(currentSubject.getGradeIds()));

        // Return true if no values are changed, false if any value is different
        return isNameUnchanged && isDescriptionUnchanged && isGradeUnchanged;
    }

}
