package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.StudentSubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.StudentSubject;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentSubjectDAOImpl implements StudentSubjectDAO {

    @Override
    public ArrayList<StudentSubject> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(StudentSubject entity) throws SQLException, ClassNotFoundException {

        boolean isSaved = false;

        for (String subjectID : entity.getSubjectID()) {
            boolean result = CrudUtil.execute(
                    "INSERT INTO student_subject (student_id, subject_id) VALUES (?, ?)",
                    entity.getStudentID(),
                    subjectID
            );

            if (result) {
                isSaved = true;
            } else {
                return false;
            }
        }

        return isSaved;
    }

    @Override
    public boolean update(StudentSubject entity) throws SQLException, ClassNotFoundException {
        return false;
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("DELETE FROM student_subject WHERE student_id = ?",
                id

        );
    }

    @Override
    public StudentSubject exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public StudentSubject search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
