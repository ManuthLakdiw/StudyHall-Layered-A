package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.GradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherGradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.TeacherGrade;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherGradeDAOImpl implements TeacherGradeDAO {

    GradeDAO gradeDAO = (GradeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.GRADE);

    @Override
    public ArrayList<TeacherGrade> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(TeacherGrade entity) throws SQLException, ClassNotFoundException {

        boolean isSaved = false;

        for (String grade : entity.getGrade()) {
            String gradeID = gradeDAO.getGradeIdFromName(grade);
            boolean result = CrudUtil.execute(
                    "INSERT INTO teacher_grade (teacher_id, grade_id) VALUES (?, ?)",
                    entity.getTeacherID(),
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
    public boolean update(TeacherGrade entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public TeacherGrade exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public TeacherGrade search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
