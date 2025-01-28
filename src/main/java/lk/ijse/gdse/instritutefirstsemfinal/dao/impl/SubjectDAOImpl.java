package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl implements SubjectDAO {

//    SubjectDAO subjectDAO = (SubjectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUBJECT);

    @Override
    public ArrayList<Subject> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Subject entity) throws SQLException, ClassNotFoundException {

         return CrudUtil.execute(
                "INSERT INTO subject (sub_id, sub_name, description) VALUES (?, ?, ?)",
                entity.getSubID(),
                entity.getSubjectName(),
                entity.getDescription()

        );
    }

    @Override
    public boolean update(Subject entity) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "UPDATE subject SET sub_name = ?, description = ? WHERE sub_id = ?",
                entity.getSubjectName(),
                entity.getDescription(),
                entity.getSubID()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "DELETE FROM subject WHERE sub_id = ?",
                id

        );
    }

    @Override
    public Subject exist(String id) throws SQLException, ClassNotFoundException { //getSubjectNameFromId
        ResultSet rs = CrudUtil.execute("SELECT * FROM subject WHERE subject_id = ?",
                id
        );
        if (rs.next()) {
            Subject subject = new Subject(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
            );
            return subject;
        }
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute(
                "select sub_id from subject order by sub_id desc limit 1"
        );

        if (resultSet.next()) {

            String lastID = resultSet.getString(1);
            String substring = lastID.substring(3);
            int number = Integer.parseInt(substring);
            int newId = ++number;
            return String.format("SUB%03d", newId);
        }
        return "SUB001";

    }

    @Override
    public Subject search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean existsSubjectByName(String subjectName) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "select sub_name from subject where sub_name = ?",
                subjectName
        );
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public String getSubjectIDFromName(String subjectName) throws SQLException {

        return CrudUtil.execute(
                "SELECT sub_id FROM subject WHERE sub_name = ?",
                subjectName
        );
    }

    @Override
    public List<String> getSubjectIDsFromName(List<String> subjectNames) throws SQLException {
        return List.of();
    }

//    @Override
//    public List<String> getSubjectIDsFromName(List<String> subjectNames) throws SQLException {
//
//        List<String> subjectIds = new ArrayList<>();
//
//        for (String subjectName : subjectNames) {
//            String subjectId = subjectDAO.getSubjectIDFromName(subjectName);
//            if (subjectId != null) {
//                subjectIds.add(subjectId);
//            }
//
//        }
//        return subjectIds;
//
//    }

}
