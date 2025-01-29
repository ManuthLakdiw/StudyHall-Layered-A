package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.StudentCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StudentBO extends SuperBO {


    String generateNewStudentID() throws SQLException, ClassNotFoundException;

    ArrayList<StudentDto> getAllStudentsWithLearnSubjects() throws SQLException;

    boolean saveStudent(StudentDto studentDto, List<String> subjectIds) throws SQLException;

    boolean UpdateStudent(StudentDto studentDto, List<String> subjectIds) throws SQLException, ClassNotFoundException;

    boolean deleteStudent(String studentId) throws SQLException, ClassNotFoundException;

    ArrayList<StudentDto> getStudentAllDetailsByID(String studentId) throws SQLException, ClassNotFoundException;
}
