package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.StudentCustom;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    @Override
    public ArrayList<StudentCustom> getAllStudentsWithLearnSubjects() throws SQLException {
        ArrayList<StudentCustom> studentEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute(
                "SELECT s.s_id AS 'Student ID', " +
                        "s.name AS 'Student Name', " +
                        "s.birthday AS 'Date Of Birth', " +
                        "s.admission_fee AS 'Admission Fee', " +
                        "s.parent_name AS 'Parent Name', " +
                        "s.email AS 'Email', " +
                        "s.phone_number AS 'Phone Number', " +
                        "s.address AS 'Address', " +
                        "g.grade AS 'Grade', " +
                        "GROUP_CONCAT(sb.sub_name SEPARATOR ', ') AS 'Subjects', " +
                        "s.added_by AS 'Added By' " +
                        "FROM student s " +
                        "LEFT JOIN grade g ON g.g_id = s.grade " +
                        "LEFT JOIN student_subject ss ON ss.student_id = s.s_id " +
                        "LEFT JOIN subject sb ON sb.sub_id = ss.subject_id " +
                        "GROUP BY s.s_id;"
        );
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            LocalDate birthday = resultSet.getDate(3) != null
                    ? resultSet.getDate("Date Of Birth").toLocalDate()
                    : null;
            double admissionFee = resultSet.getDouble(4);
            String parentName = resultSet.getString(5);
            String email = resultSet.getString(6);
            String phoneNumber = resultSet.getString(7);
            String address = resultSet.getString(8);
            String grade = resultSet.getString(9);
            String addedBy = resultSet.getString(11);

            String subjectsString = resultSet.getString(10);
            String[] subjects = subjectsString != null ? subjectsString.split(", ") : new String[0];

            StudentCustom studentCustomEntity = new StudentCustom(
                    id,
                    birthday,
                    name,
                    admissionFee,
                    parentName,
                    email,
                    phoneNumber,
                    address,
                    addedBy,
                    grade,
                    subjects
            );

            studentEntities.add(studentCustomEntity);
        }
        return studentEntities;

    }

    @Override
    public ArrayList<StudentCustom> getStudentAllDetailsByID(String studentId) throws SQLException {
        ArrayList<StudentCustom> studentCustoms = new ArrayList<>();

            ResultSet resultSet = CrudUtil.execute(
                    "SELECT s.s_id AS 'Student ID', " +
                            "s.name AS 'Student Name', " +
                            "s.birthday AS 'Date Of Birth', " +
                            "s.admission_fee AS 'Admission Fee', " +
                            "s.parent_name AS 'Parent Name', " +
                            "s.email AS 'Email', " +
                            "s.phone_number AS 'Phone Number', " +
                            "s.address AS 'Address', " +
                            "g.grade AS 'Grade', " +
                            "GROUP_CONCAT(sb.sub_name SEPARATOR ', ') AS 'Subjects', " +
                            "s.added_by AS 'Added By' " +
                            "FROM student s " +
                            "LEFT JOIN grade g ON g.g_id = s.grade " +
                            "LEFT JOIN student_subject ss ON ss.student_id = s.s_id " +
                            "LEFT JOIN subject sb ON sb.sub_id = ss.subject_id " +
                            "WHERE s.s_id = ? " +
                            "GROUP BY s.s_id;", studentId
            );


            while (resultSet.next()) {

                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                LocalDate birthday = resultSet.getDate(3) != null
                        ? resultSet.getDate("Date Of Birth").toLocalDate()
                        : null;
                double admissionFee = resultSet.getDouble(4);
                String parentName = resultSet.getString(5);
                String email = resultSet.getString(6);
                String phoneNumber = resultSet.getString(7);
                String address = resultSet.getString(8);
                String grade = resultSet.getString(9);
                String addedBy = resultSet.getString(11);


                String subjectsString = resultSet.getString(10);
                String[] subjects = subjectsString != null ? subjectsString.split(", ") : new String[0];


                StudentCustom studentCustomEntity = new StudentCustom(
                        id,
                        birthday,
                        name,
                        admissionFee,
                        parentName,
                        email,
                        phoneNumber,
                        address,
                        addedBy,
                        grade,
                        subjects
                );


                studentCustoms.add(studentCustomEntity);
            }
            return studentCustoms;
    }

    @Override
    public ArrayList<String> getSubjectsDetailsByGradeID(String gradeId) throws SQLException {
        ArrayList<String> subjects = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "SELECT subject.sub_name FROM subject JOIN" +
                        " subject_grade ON subject.sub_id = subject_grade.subject_id" +
                        " WHERE subject_grade.grade_id = ?" ,

                gradeId

        );

        while (resultSet.next()) {
            subjects.add(resultSet.getString("sub_name"));
        }

        return subjects;

    }

    @Override
    public ArrayList<Exam> getAllExamsAndApplicableSubjects() throws SQLException {
        ArrayList<Exam> examEntities = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(
                "SELECT e.exam_id, g.grade, s.sub_name AS subject, e.date, e.exam_type," +
                        " e.description FROM exam AS e LEFT JOIN " +
                        "subject AS s ON e.subject_id = s.sub_id LEFT JOIN grade" +
                        " AS g ON e.grade = g.g_id"
        );
        while (resultSet.next()) {
            Exam examEntity = new Exam();

            examEntity.setExamId(resultSet.getString(1));
            examEntity.setGrade(resultSet.getString(2));
            examEntity.setSubject(resultSet.getString(3));
            examEntity.setExamDate(resultSet.getDate(4).toLocalDate());
            examEntity.setExamType(resultSet.getString(5));
            examEntity.setDescription(resultSet.getString(6));
            examEntities.add(examEntity);
        }
        return examEntities;
    }

    @Override
    public Exam getNextUpCommingExam() throws SQLException {

        ResultSet resultSet = CrudUtil.execute("""
        SELECT e.exam_id, g.grade AS grade_name, s.sub_name AS subject_name, e.exam_type, e.date AS exam_date, DATEDIFF(e.date, CURDATE()) AS days_until_exam 
        FROM exam e 
        JOIN subject s ON e.subject_id = s.sub_id 
        JOIN grade g ON e.grade = g.g_id 
        WHERE e.date > CURDATE() 
        ORDER BY e.date ASC LIMIT 1;
        """);

        if (resultSet.next()) {
            return new Exam(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(5).toLocalDate(),
                    resultSet.getString(4),
                    resultSet.getString(6)
            );
        }
        return null;
    }


}
