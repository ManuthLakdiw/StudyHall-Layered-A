package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Teacher;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

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
        return "";
    }

    @Override
    public Teacher search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
