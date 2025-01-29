package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ExamDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamDAOImpl implements ExamDAO {

    @Override
    public ArrayList<Exam> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean save(Exam entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "insert into exam values(?,?,?,?,?,?)",
                entity.getExamId(),
                entity.getSubject(),
                entity.getExamType(),
                entity.getExamDate(),
                entity.getDescription(),
                entity.getGrade()

        );
    }

    @Override
    public boolean update(Exam entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE exam SET subject_id = ?, exam_type = ?, date = ?, " +
                        "description = ?, grade = ? WHERE exam_id = ?" ,

                entity.getSubject(),
                entity.getExamType(),
                entity.getExamDate(),
                entity.getDescription(),
                entity.getGrade(),
                entity.getExamId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "delete from exam where exam_id = ?" ,
                id
        );
    }

    @Override
    public Exam exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "select exam_id from exam order by exam_id desc limit 1"
        );

        if (resultSet.next()) {
            String lastID = resultSet.getString(1);
            String substring = lastID.substring(1);
            int number = Integer.parseInt(substring);
            int newId = ++number;
            return String.format("E%03d", newId);

        }
        return "E001";
    }


    @Override
    public Exam search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String[] getExamIDsUsingSubject(String subject) throws SQLException {
        ArrayList<String> examIDs = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "select exam_id from exam where subject_id = ? ",
                subject
        );

        while (resultSet.next()) {
            examIDs.add(resultSet.getString(1));
        }

        return examIDs.toArray(new String[examIDs.size()]);

    }

    @Override
    public int getExamCount() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT COUNT(*) FROM exam"
        );

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public ArrayList<Exam> ExistExam(String examID) throws SQLException {
        ArrayList<Exam> exams = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "select * from exam where exam_id = ?",
                examID
        );

        if (resultSet.next()) {
            Exam exam = new Exam(
                    resultSet.getString(1),
                    resultSet.getString(6),
                    resultSet.getString(2),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getString(3),
                    resultSet.getString(5)
            );
            exams.add(exam);
        }
        return exams;

    }

    @Override
    public String[] getExamSubjectsByGrade(String grade) throws SQLException {
        ArrayList<String> subjectsList = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "SELECT subject_id FROM exam WHERE grade =?",
                grade
        );

        while (resultSet.next()) {
            subjectsList.add(resultSet.getString("subject_id"));
        }
        return subjectsList.toArray(new String[0]);
    }
}
