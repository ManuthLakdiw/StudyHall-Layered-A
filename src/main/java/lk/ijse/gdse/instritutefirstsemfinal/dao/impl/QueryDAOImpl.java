package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<SubjectCustom> getAllSubjectsAndRelatedGrades() throws SQLException {
        ArrayList<SubjectCustom> subjectEntities = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "SELECT s.sub_id, s.sub_name, GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade)" +
                        " AS grade_names, s.description FROM subject AS s LEFT JOIN subject_grade AS" +
                        " sg ON s.sub_id = sg.subject_id LEFT JOIN grade AS g ON sg.grade_id = g.g_id" +
                        " GROUP BY s.sub_id, s.sub_name, s.description ORDER BY s.sub_id"
        );

        while (resultSet.next()) {
            String[] gradesArray = resultSet.getString(3) != null
                    ? resultSet.getString(3).split(",")
                    : new String[0];

            SubjectCustom subjectEntity = new SubjectCustom(
            resultSet.getString(1),
            resultSet.getString(2),
                    gradesArray,
            resultSet.getString(4)
            );

            subjectEntities.add(subjectEntity);

        }
        return subjectEntities;
    }
}
