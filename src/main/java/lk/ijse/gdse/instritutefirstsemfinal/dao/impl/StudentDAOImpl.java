package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.StudentDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Student;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {



    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "select s_id from student order by s_id desc limit 1"
        );

        if (resultSet.next()) {
            String lastID = resultSet.getString(1);
            String substring = lastID.substring(1);
            int number = Integer.parseInt(substring);
            int newId = ++number;
            return String.format("S%04d", newId);
        }
        return "S0001";
    }

    @Override
    public boolean save(Student entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO student (s_id, birthday, name, admission_fee, parent_name, " +
                        "email, phone_number, address, added_by, grade) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ,

                entity.getId(),
                entity.getBirthday(),
                entity.getName(),
                entity.getAdmissionFee(),
                entity.getParentName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getAddress(),
                entity.getAddedBy(),
                entity.getGrade()
        );
    }

    @Override
    public boolean update(Student entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE student SET birthday = ?, name = ?, admission_fee = ?," +
                " parent_name = ?, email = ?, phone_number = ?, address = ?, " +
                "added_by = ?, grade = ? WHERE s_id = ?" ,

                entity.getBirthday(),
                entity.getName(),
                entity.getAdmissionFee(),
                entity.getParentName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getAddress(),
                entity.getAddedBy(),
                entity.getGrade(),
                entity.getId()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM student WHERE s_id = ?", id);
    }

    @Override
    public String getStudentNameByID(String studentId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT name FROM student WHERE s_id = ?",
                studentId
        );

        if (resultSet.next()) {
            return resultSet.getString("name");
        }

        return "Not Found";
    }

    @Override
    public String getStudentIDByName(String studentName) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT s_id FROM student WHERE name = ?",
                studentName
        );
        if (resultSet.next()) {
            return resultSet.getString("s_id");
        }
        return "Not Found";
    }

    @Override
    public int getStudentCount() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT COUNT(*) FROM student"
        );
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }






    @Override
    public ArrayList<Student> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Student search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Student exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
