package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Teacher;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherDAOImpl implements TeacherDAO {

    @Override
    public ArrayList<Teacher> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Teacher entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Teacher entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
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
