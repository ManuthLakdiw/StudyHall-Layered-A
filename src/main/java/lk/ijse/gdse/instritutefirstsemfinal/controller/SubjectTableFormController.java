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
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.dto.tm.SubjectTm;
import lk.ijse.gdse.instritutefirstsemfinal.model.SubjectModel;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SubjectTableFormController implements Initializable {

    SubjectModel model = new SubjectModel();
    SubjectFormController subjectFormController = new SubjectFormController();


    @FXML
    private Pane SubjectPane;

    @FXML
    private Button btnSubject;

    @FXML
    private TableColumn<SubjectTm, String> colDescription;

    @FXML
    private TableColumn<SubjectTm, String> colGrade;

    @FXML
    private TableColumn<SubjectTm, String> colSubID;

    @FXML
    private TableColumn<SubjectTm, String> colSubName;

    @FXML
    private TableView<SubjectTm> tblSubject;

    @FXML
    private TextField txtFindSubject;


    boolean isButtonClicked = false;
    FilteredList filter;

    @FXML
    private void btnSubjectOnAction(ActionEvent event) {
        isButtonClicked = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/subjectForm.fxml"));
            Parent load = loader.load();

            SubjectFormController controller = loader.getController();

            controller.setTblSubjectFormController(this);

            this.subjectFormController = controller;

            Stage stage = new Stage();
            stage.initModality(null);
            stage.setTitle("Subject Form");
            stage.setScene(new Scene(load));

            stage.initModality(null);

            stage.initOwner(btnSubject.getScene().getWindow());

            stage.setResizable(false);


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void tblSubjectOnClicked(MouseEvent event) {
        SubjectTm isSelected = tblSubject.getSelectionModel().getSelectedItem();
        if (isButtonClicked){
            if (isSelected != null) {
                String grades = isSelected.getSubjectGrades();
                String[] gradeArray = new String[0];

                if (grades != null && !grades.isEmpty()) {
                    // කොමාවෙන් වෙන් කර String[] එකක් ලෙස ලබා ගන්න
                    gradeArray = grades.split(", ");
                }

                SubjectDto dto = new SubjectDto(
                        isSelected.getSubjectId(),
                        isSelected.getSubjectName(),
                        gradeArray,
                        isSelected.getSubjectDescription()
                );
                subjectFormController.setUserDto(dto);
                subjectFormController.tableOnClickedButton();

            }
        }if (event.getButton() == MouseButton.SECONDARY) {
            Optional<ButtonType> result = AlertUtil.ConfirmationAlert("Do you want to delete subject : " + isSelected.getSubjectName()+" ["+isSelected.getSubjectId()+"]", ButtonType.YES, ButtonType.NO);
            if (result.isPresent()) { // Check if the user clicked a button
                if (result.get() == ButtonType.YES) {
                    Optional<ButtonType> confirmDelete = AlertUtil.ConfirmationAlert("Are you sure!", ButtonType.YES, ButtonType.NO);
                    if (confirmDelete.isPresent()) {
                        if (confirmDelete.get() == ButtonType.YES) {
                            boolean isDeleted = model.deleteSubject(isSelected.getSubjectId());
                            if (isDeleted) {
                                AlertUtil.informationAlert(UserFormController.class,null,true,"User deleted successfully");
                                loadSubjectTable();
                            }else {
                                AlertUtil.informationAlert(UserFormController.class,null,true,"User could not be deleted successfully");
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void txtFindSubjectOnKeyRelesed(KeyEvent event) {
            txtFindSubject.textProperty().addListener((observable, oldValue, newValue) -> {
                filter.setPredicate((Predicate<? super SubjectTm>) (SubjectTm subjectTm) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Return all subjects if the search text is empty
                    } else {
                        // Perform case-insensitive matching
                        return subjectTm.getSubjectName().toLowerCase().contains(newValue.toLowerCase()) ||
                                subjectTm.getSubjectId().toLowerCase().contains(newValue.toLowerCase()) ||
                                subjectTm.getSubjectDescription().toLowerCase().contains(newValue.toLowerCase());
                    }
                });

                SortedList<SubjectTm> sortedList = new SortedList<>(filter);
                sortedList.comparatorProperty().bind(tblSubject.comparatorProperty());
                tblSubject.setItems(sortedList);
            });
        }


    public void loadSubjectTable() {
        ArrayList<SubjectDto> subjectDtos = model.getAllSubjects();
        ObservableList<SubjectTm> subjectTms = FXCollections.observableArrayList();

        for (SubjectDto subjectDto : subjectDtos) {
            // Convert grades to a comma-separated string
            String grades = subjectDto.getSubjectGrades() != null && subjectDto.getSubjectGrades().length > 0
                    ? String.join(", ", subjectDto.getSubjectGrades())
                    : "N/A";

            SubjectTm subjectTm = new SubjectTm(
                    subjectDto.getSubjectId(),
                    subjectDto.getSubjectName(),
                    grades,
                    subjectDto.getSubjectDescription()
            );
            subjectTms.add(subjectTm);
        }

        tblSubject.setItems(subjectTms);

        filter = new FilteredList(subjectTms, e -> true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSubID.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colSubName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("subjectGrades"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("subjectDescription"));



        loadSubjectTable();
    }
}
