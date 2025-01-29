package lk.ijse.gdse.instritutefirstsemfinal.dao.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.dao.CrudDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Student;

import java.sql.SQLException;

public interface StudentDAO extends CrudDAO<Student> {


    String getStudentNameByID(String studentId) throws SQLException;

    String getStudentIDByName(String studentName) throws SQLException;

    int getStudentCount() throws SQLException;

}
