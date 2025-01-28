package lk.ijse.gdse.instritutefirstsemfinal.dao;

import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {

    ArrayList<SubjectCustom> getAllSubjectsAndRelatedGrades() throws SQLException;

    SubjectCustom getExistsSubjectsAndRelatedGrades(String subjectID) throws SQLException;

    ArrayList<TeacherCustom> getAllTeachersAndRelatedGrades() throws SQLException;
}
