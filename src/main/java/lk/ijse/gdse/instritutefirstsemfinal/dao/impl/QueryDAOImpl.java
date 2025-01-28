package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public SubjectCustom getExistsSubjectsAndRelatedGrades(String subjectID) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT s.sub_id, s.sub_name, GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade)" +
                        " AS grade_names, s.description FROM subject AS s LEFT JOIN subject_grade AS" +
                        " sg ON s.sub_id = sg.subject_id LEFT JOIN grade AS g ON sg.grade_id = g.g_id " +
                        "WHERE s.sub_id = ? GROUP BY s.sub_id, s.sub_name, s.description ORDER BY s.sub_id;",
                subjectID
        );
        if (resultSet.next()) {
            String[] gradesArray = resultSet.getString(3) != null ?
                    resultSet.getString(3).split(",")
                    : new String[0];

            SubjectCustom subjectEntity = new SubjectCustom(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    gradesArray,
                    resultSet.getString(4)
            );
            return subjectEntity;

        }
        return null;

    }

    @Override
    public ArrayList<TeacherCustom> getAllTeachersAndRelatedGrades() throws SQLException {
        ArrayList<TeacherCustom> teacherEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(

                "SELECT t.t_id, t.name, t.phone_number, t.email, s.sub_name AS subject, " +
                        "GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grades " +
                        "FROM teacher AS t " +
                        "LEFT JOIN subject AS s ON t.subject_id = s.sub_id " +
                        "LEFT JOIN teacher_grade AS tg ON t.t_id = tg.teacher_id " +
                        "LEFT JOIN grade AS g ON tg.grade_id = g.g_id " +
                        "GROUP BY t.t_id, t.name, t.phone_number, t.email, s.sub_name " +
                        "ORDER BY t.t_id"
        );

        while (resultSet.next()) {

            String subject = resultSet.getString("subject");

            String[] gradeArray = resultSet.getString("grades") != null
                    ? resultSet.getString("grades").split(",")
                    : new String[0];

            TeacherCustom teacherEntity = new TeacherCustom(
                    resultSet.getString("t_id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("email"),
                    subject,
                    gradeArray
            );
            teacherEntities.add(teacherEntity);
        }
        return teacherEntities;
    }

    @Override
    public boolean getExistsTeachersAndRelatedGrades(TeacherCustom teacherCustom) throws SQLException {

        ResultSet resultSet = CrudUtil.execute(
                "SELECT t.name, t.phone_number, t.email, s.sub_name AS subject, " +
                        "GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grades " +
                        "FROM teacher AS t " +
                        "LEFT JOIN subject AS s ON t.subject_id = s.sub_id " +
                        "LEFT JOIN teacher_grade AS tg ON t.t_id = tg.teacher_id " +
                        "LEFT JOIN grade AS g ON tg.grade_id = g.g_id " +
                        "WHERE t.t_id = ? " +
                        "GROUP BY t.t_id, t.name, t.phone_number, t.email, s.sub_name" ,

                teacherCustom.getTeacherId()
        );

        if (resultSet.next()) {
            String currentName = resultSet.getString("name");
            String currentPhoneNumber = resultSet.getString("phone_number");
            String currentEmail = resultSet.getString("email");
            String currentSubject = resultSet.getString("subject");
            String currentGrades = resultSet.getString("grades");


            String gradesString = String.join(",", teacherCustom.getGrades());

            return currentName.equals(teacherCustom.getName()) &&
                    currentPhoneNumber.equals(teacherCustom.getPhoneNumber()) &&
                    currentEmail.equals(teacherCustom.getEmail()) &&
                    currentSubject.equals(teacherCustom.getSubject()) &&
                    (currentGrades != null && currentGrades.equals(gradesString));

        }
        return false;
    }

    @Override
    public List<String> getGradesForSubject(String subjectName) throws SQLException {
        List<String> grades = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "SELECT s.sub_name, g.grade FROM grade g JOIN subject_grade sg" +
                        " ON g.g_id = sg.grade_id JOIN subject s ON sg.subject_id = s.sub_id" +
                        " WHERE s.sub_name = ?" ,
                subjectName
        );

        while (resultSet.next()) {
            grades.add(resultSet.getString(2));
        }
        return grades;
    }


}
