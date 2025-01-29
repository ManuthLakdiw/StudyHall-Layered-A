package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.GradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ResultDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.StudentDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Result;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultDAOImpl implements ResultDAO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);
    GradeDAO gradeDAO = (GradeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.GRADE);

    @Override
    public ArrayList<Result> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Result entity) throws SQLException, ClassNotFoundException {
        String studentID = studentDAO.getStudentIDByName(entity.getStudent());
        String grade = gradeDAO.getGradeIdFromName(entity.getGrade());

        return CrudUtil.execute(
                "INSERT INTO result (result_id, exam_id, student_id, marks," +
                        " exam_grade, status, grade) VALUES (?, ?, ?, ?, ?, ?, ?)" ,

                entity.getResultID(),
                entity.getExam(),
                studentID,
                entity.getMarks(),
                entity.getGradeArchived(),
                entity.getStatus(),
                grade
        );
    }

    @Override
    public boolean update(Result entity) throws SQLException, ClassNotFoundException {
        String studentID = studentDAO.getStudentIDByName(entity.getStudent());
        String grade = gradeDAO.getGradeIdFromName(entity.getGrade());

        return CrudUtil.execute(
                "UPDATE result SET exam_id = ?, student_id = ?, marks = ?," +
                        " exam_grade = ?, status = ?, grade = ? WHERE result_id = ?",

                entity.getExam(),
                studentID,
                entity.getMarks(),
                entity.getGradeArchived(),
                entity.getStatus(),
                grade,
                entity.getResultID()
        );


    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute("DELETE FROM result WHERE result_id = ?", id);
    }

    @Override
    public Result exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute(
                "select result_id from result order by result_id desc limit 1"
        );

        if (resultSet.next()) {
            String lastID = resultSet.getString(1);
            String substring = lastID.substring(1);
            int number = Integer.parseInt(substring);
            int newId = ++number;
            return String.format("R%03d", newId);

        }
        return "R001";

    }

    @Override
    public Result search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
