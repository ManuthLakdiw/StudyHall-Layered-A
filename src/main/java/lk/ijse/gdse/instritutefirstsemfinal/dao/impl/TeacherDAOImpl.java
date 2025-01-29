package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Teacher;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDAOImpl implements TeacherDAO {

    @Override
    public ArrayList<Teacher> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Teacher entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO teacher (t_id, name, phone_number, email, subject_id) VALUES (?, ?, ?, ?, ?)",
                entity.getTeacherId(),
                entity.getTeacherName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getSubjectID()
        );
    }

    @Override
    public boolean update(Teacher entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE teacher SET name = ?, phone_number = ?, email = ?, subject_id = ? WHERE t_id = ?",
                entity.getTeacherName(),
                entity.getPhoneNumber(),
                entity.getEmail(),
                entity.getSubjectID(),
                entity.getTeacherId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM teacher WHERE t_id = ?",
                id
        );
    }

    @Override
    public Teacher exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "select t_id from teacher order by t_id desc limit 1"
        );

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String substring = lastId.substring(1);
            int number = Integer.parseInt(substring);
            int newId = ++number;
            return String.format("T%03d", newId);

        }

        return "T001";

    }

    @Override
    public Teacher search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public String getTeacherEmail(String selectedTeacherId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select email from teacher where t_id = ?" ,
                selectedTeacherId
        );

        if (resultSet.next()) {
            return resultSet.getString("email");
        }
        return "Not found";
    }

    @Override
    public int getTeacherCount() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT COUNT(*) FROM teacher"
        );

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;

    }
}
