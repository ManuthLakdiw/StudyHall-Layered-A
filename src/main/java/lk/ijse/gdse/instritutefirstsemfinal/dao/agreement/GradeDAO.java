package lk.ijse.gdse.instritutefirstsemfinal.dao.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.dao.CrudDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.SuperDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Grade;

import java.sql.SQLException;
import java.util.List;

public interface GradeDAO extends CrudDAO<Grade> {

    List<String> getGradeIdsFromNames(List<String> gradeNames) throws SQLException;

    String getGradeIdFromName(String grade) throws SQLException;

    String getGradeNameFromID(String gradeId) throws SQLException;

}
