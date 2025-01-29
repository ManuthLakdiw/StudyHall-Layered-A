package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.GradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Grade;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GradeDAOImpl implements GradeDAO {

    @Override
    public ArrayList<Grade> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Grade> gradeEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM grade");
        while (resultSet.next()) {
            Grade grade = new Grade();
            grade.setGradeId(resultSet.getString(1));
            grade.setGradeName(resultSet.getString(2));
            gradeEntities.add(grade);
        }
        return gradeEntities;

    }

    @Override
    public boolean save(Grade entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Grade entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Grade exist(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Grade search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public List<String> getGradeIdsFromNames(List<String> gradeNames) throws SQLException {
        List<String> gradeIds = new ArrayList<>();

        String query = "SELECT g_id FROM grade WHERE grade IN (" +
                String.join(",", Collections.nCopies(gradeNames.size(), "?")) + ")";

        ResultSet resultSet = CrudUtil.execute(query, gradeNames.toArray());

        while (resultSet.next()) {
            gradeIds.add(resultSet.getString("g_id"));
        }
        return gradeIds;
    }

    @Override
    public String getGradeIdFromName(String grade) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT g_id FROM grade WHERE grade = ?",
                grade
        );
        if (resultSet.next()) {
            return resultSet.getString("g_id");
        }
        return null;
    }

    @Override
    public String getGradeNameFromID(String gradeId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT grade FROM grade WHERE g_id = ?" ,
                gradeId
        );
        if (resultSet.next()) {
            return resultSet.getString("grade");
        }
        return gradeId;
    }


}
