package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;

import java.sql.SQLException;

public interface ExamBO extends SuperBO {

    String generateNewExamID() throws SQLException, ClassNotFoundException;

    boolean saveExam(ExamDto examDto) throws SQLException, ClassNotFoundException;

    boolean updateExam(ExamDto examDto) throws SQLException, ClassNotFoundException;

    boolean delete(String id) throws SQLException, ClassNotFoundException;

    String[] getExamIDsUsingSubject(String subject) throws SQLException;

    int getExamCount() throws SQLException;




}
