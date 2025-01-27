package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectGradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.SubjectGrade;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectGradeDAOImpl implements SubjectGradeDAO {

    @Override
    public ArrayList<SubjectGrade> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(SubjectGrade entity) throws SQLException, ClassNotFoundException {
        boolean isSaved = false;

        for (String gradeID : entity.getGradeID()) {
            boolean result = CrudUtil.execute(
                    "INSERT INTO subject_grade (subject_id, grade_id) VALUES (?, ?)",
                    entity.getSubjectID(),
                    gradeID
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
    public boolean update(SubjectGrade entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public SubjectGrade exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public SubjectGrade search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
