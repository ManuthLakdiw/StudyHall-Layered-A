package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectDAOImpl implements SubjectDAO {

    @Override
    public ArrayList<Subject> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Subject entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Subject entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Subject exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Subject search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
