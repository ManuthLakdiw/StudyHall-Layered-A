package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ResultDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Result;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResultDAOImpl implements ResultDAO {
    @Override
    public ArrayList<Result> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Result entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Result entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Result exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Result search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
