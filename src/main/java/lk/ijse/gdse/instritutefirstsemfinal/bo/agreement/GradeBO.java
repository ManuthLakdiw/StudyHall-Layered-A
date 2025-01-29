package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Grade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface GradeBO extends SuperBO {

    List<String> getGradeIdsFromNames(List<String> gradeNames) throws SQLException;
    String getGradeIdFromName(String grade) throws SQLException;
    ArrayList<GradeDto> getAllGrades() throws SQLException, ClassNotFoundException;
}
