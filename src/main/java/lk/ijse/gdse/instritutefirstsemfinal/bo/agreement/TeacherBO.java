package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TeacherBO extends SuperBO {

    String generateNewID() throws SQLException , ClassNotFoundException;

    ArrayList<TeacherDto> getAllTeachersAndRelatedGrades() throws SQLException;

    boolean saveTeacher(TeacherDto teacherDto, List<String> grades) throws SQLException;

    boolean updateTeacher(TeacherDto teacherDto, List<String> grades) throws SQLException;

    boolean deleteTeacher(String teacherId) throws SQLException , ClassNotFoundException;

    boolean getExistsTeachersAndRelatedGrades(TeacherDto teacherDto) throws SQLException;

    String getTeacherEmail(String selectedTeacherId) throws SQLException;

    int getTeacherCount() throws SQLException;

}
