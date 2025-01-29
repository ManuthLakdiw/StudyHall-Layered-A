package lk.ijse.gdse.instritutefirstsemfinal.dao.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.dao.CrudDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;

import java.sql.SQLException;

public interface ExamDAO  extends CrudDAO<Exam> {

    String[] getExamIDsUsingSubject(String subject) throws SQLException;

    int getExamCount() throws SQLException;


}
