package lk.ijse.gdse.instritutefirstsemfinal.dao.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.dao.CrudDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;

import java.sql.SQLException;
import java.util.List;

public interface SubjectDAO extends CrudDAO<Subject> {

    boolean existsSubjectByName(String subjectName) throws SQLException;
    String getSubjectIDFromName(String subjectName) throws SQLException;
    List<String> getSubjectIDsFromName(List<String> subjectNames) throws SQLException;
    String getSubjectByID(String subjectID) throws SQLException;
}
