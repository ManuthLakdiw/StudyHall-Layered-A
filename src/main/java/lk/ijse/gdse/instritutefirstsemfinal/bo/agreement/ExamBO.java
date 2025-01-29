package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ExamBO extends SuperBO {

    String generateNewExamID() throws SQLException, ClassNotFoundException;

    ArrayList<ExamDto> getAllExamsAndApplicableSubjectNames() throws SQLException;

    boolean saveExam(ExamDto examDto) throws SQLException, ClassNotFoundException;

    boolean updateExam(ExamDto examDto) throws SQLException, ClassNotFoundException;

    boolean deleteExam(String id) throws SQLException, ClassNotFoundException;

    String[] getExamIDsUsingSubject(String subject) throws SQLException;

    int getExamCount() throws SQLException;

    ArrayList<ExamDto> ExistExam(String examID) throws SQLException;

    String[] getExamSubjectsByGrade(String grade) throws SQLException;

    ExamDto getNextUpComingExam() throws SQLException;



}
