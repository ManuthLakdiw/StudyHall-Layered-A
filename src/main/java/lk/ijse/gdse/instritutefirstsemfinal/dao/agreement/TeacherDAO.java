package lk.ijse.gdse.instritutefirstsemfinal.dao.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.dao.CrudDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Teacher;
import lk.ijse.gdse.instritutefirstsemfinal.entity.TeacherGrade;

import java.sql.SQLException;

public interface TeacherDAO extends CrudDAO<Teacher> {

    String getTeacherEmail(String selectedTeacherId) throws SQLException;

}
