package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SubjectBO extends SuperBO {

    ArrayList<SubjectDto> getAllSubjectsAndRelatedGrades() throws SQLException;
    String generateNewSubjectID() throws SQLException , ClassNotFoundException;
    boolean saveSubject(SubjectDto subjectDto, List<String> gradeIds) throws SQLException;
    boolean updateSubject(SubjectDto subjectDto , List<String> gradeIds) throws SQLException, ClassNotFoundException;
    boolean deleteSubject(String subjectId) throws SQLException, ClassNotFoundException;
    boolean existsSubjectByName(String subjectName) throws SQLException;
    SubjectDto getExistsSubjectsAndRelatedGrades(String subjectID) throws SQLException;
    List<String> getGradesForSubject(String subjectName) throws SQLException;
    ArrayList<String> getSubjectsDetailsByGradeID(String gradeId) throws SQLException;
    String getSubjectIDFromName(String subjectName) throws SQLException;
    List<String> getSubjectIDsFromName(List<String> subjectNames) throws SQLException;


}
