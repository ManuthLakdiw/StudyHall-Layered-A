package lk.ijse.gdse.instritutefirstsemfinal.dao;

import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Student;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.StudentCustom;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QueryDAO extends SuperDAO {

    ArrayList<SubjectCustom> getAllSubjectsAndRelatedGrades() throws SQLException;

    SubjectCustom getExistsSubjectsAndRelatedGrades(String subjectID) throws SQLException;

    ArrayList<TeacherCustom> getAllTeachersAndRelatedGrades() throws SQLException;

    boolean getExistsTeachersAndRelatedGrades(TeacherCustom teacherCustom) throws SQLException;

    List<String> getGradesForSubject(String subjectName) throws SQLException;

    ArrayList<StudentCustom> getAllStudentsWithLearnSubjects() throws SQLException;

    ArrayList<StudentCustom> getStudentAllDetailsByID(String studentId) throws SQLException;

    ArrayList<String> getSubjectsDetailsByGradeID(String gradeId) throws SQLException;

    ArrayList<Exam> getAllExamsAndApplicableSubjectNames() throws SQLException;

    Exam getNextUpCommingExam() throws SQLException;

}
