package lk.ijse.gdse.instritutefirstsemfinal.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.util.AlertUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportFormController {

    @FXML
    private Pane ReportPane;

    @FXML
    private Button btnGenarateAllStudentReport;

    @FXML
    void btnGenarateAllStudentReportOnAction(ActionEvent event) {

        try {
            InputStream is = getClass().getResourceAsStream("/assets/report/StudentsDEtailsReport.jrxml");

            if (is == null) {
                throw new FileNotFoundException("JRXML file not found.");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(is);

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            JasperViewer.viewReport(jasperPrint, false);

        }catch (JRException e){
            AlertUtil.errorAlert(this.getClass(),null,"Fail to Load Report");

        }catch (SQLException e){
            AlertUtil.errorAlert(this.getClass(),null,"DbError");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        }

    }

}
