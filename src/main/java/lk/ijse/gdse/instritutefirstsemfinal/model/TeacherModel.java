package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TeacherModel {
    SubjectModel subjectModel = new SubjectModel();
    GradeModel gradeModel = new GradeModel();


    public String getNextTeacherID() {
        try {
            ResultSet resultSet = CrudUtil.execute("select t_id from teacher order by t_id desc limit 1");
            if (resultSet.next()) {
                String lastId = resultSet.getString(1);
                String substring = lastId.substring(1);
                int number = Integer.parseInt(substring);
                int newId = ++number;
                return String.format("T%03d", newId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "T001";
    }

    
    public ArrayList<TeacherDto> getAllTeachers() {
        try {
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

            ArrayList<TeacherDto> teachers = new ArrayList<>();
            while (resultSet.next()) {
                String subject = resultSet.getString("subject");

                String[] gradeArray = resultSet.getString("grades") != null
                        ? resultSet.getString("grades").split(",")
                        : new String[0];

                TeacherDto teacherDto = new TeacherDto(
                        resultSet.getString("t_id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("email"),
                        subject,
                        gradeArray
                );
                teachers.add(teacherDto);
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean saveTeacher(TeacherDto teacherDto, List<String> grades) {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String teacherSQL = "INSERT INTO teacher (t_id, name, phone_number, email, subject_id) VALUES (?, ?, ?, ?, ?)";
            String subjectId = subjectModel.getSubjectIdFromName(teacherDto.getSubject());


            if (subjectId == null) {
                connection.rollback();
                return false;
            }

            boolean teacherInserted = CrudUtil.execute(
                    teacherSQL,
                    teacherDto.getTeacherId(),
                    teacherDto.getName(),
                    teacherDto.getPhoneNumber(),
                    teacherDto.getEmail(),
                    subjectId
            );

            if (!teacherInserted) {
                connection.rollback();
                return false;
            }

            for (String gradeName : grades) {
                String gradeId = gradeModel.getGradeIdFromName(gradeName);

                if (gradeId != null) {
                    String teacherGradeSQL = "INSERT INTO teacher_grade (teacher_id, grade_id) VALUES (?, ?)";
                    boolean gradeInserted = CrudUtil.execute(teacherGradeSQL, teacherDto.getTeacherId(), gradeId);

                    if (!gradeInserted) {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public boolean updateTeacher(TeacherDto teacherDto, List<String> grades) {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start a transaction

            String subjectId = subjectModel.getSubjectIdFromName(teacherDto.getSubject());

            if (subjectId == null) {
                connection.rollback();
                return false;
            }

            String checkTeacherSQL = "SELECT t_id FROM teacher WHERE t_id = ?";
            ResultSet resultSet = CrudUtil.execute(checkTeacherSQL, teacherDto.getTeacherId());
            if (!resultSet.next()) {
                connection.rollback();
                return false;
            }

            String updateTeacherSQL = "UPDATE teacher SET name = ?, phone_number = ?, email = ?, subject_id = ? WHERE t_id = ?";
            boolean teacherUpdated = CrudUtil.execute(
                    updateTeacherSQL,
                    teacherDto.getName(),
                    teacherDto.getPhoneNumber(),
                    teacherDto.getEmail(),
                    subjectId,
                    teacherDto.getTeacherId()
            );

            if (!teacherUpdated) {
                connection.rollback();
                return false;
            }

            String deleteTeacherGradesSQL = "DELETE FROM teacher_grade WHERE teacher_id = ?";
            CrudUtil.execute(deleteTeacherGradesSQL, teacherDto.getTeacherId());

            for (String gradeName : grades) {
                String gradeId = gradeModel.getGradeIdFromName(gradeName);

                if (gradeId != null) {
                    String insertTeacherGradeSQL = "INSERT INTO teacher_grade (teacher_id, grade_id) VALUES (?, ?)";
                    boolean gradeInserted = CrudUtil.execute(insertTeacherGradeSQL, teacherDto.getTeacherId(), gradeId);

                    if (!gradeInserted) {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public boolean isValuesUnchanged(String teacherId, String name, String phoneNumber, String email, String subject, List<String> grades) {
        try {
            String query = "SELECT t.name, t.phone_number, t.email, s.sub_name AS subject, " +
                    "GROUP_CONCAT(DISTINCT g.grade ORDER BY g.grade) AS grades " +
                    "FROM teacher AS t " +
                    "LEFT JOIN subject AS s ON t.subject_id = s.sub_id " +
                    "LEFT JOIN teacher_grade AS tg ON t.t_id = tg.teacher_id " +
                    "LEFT JOIN grade AS g ON tg.grade_id = g.g_id " +
                    "WHERE t.t_id = ? " +
                    "GROUP BY t.t_id, t.name, t.phone_number, t.email, s.sub_name";

            ResultSet resultSet = CrudUtil.execute(query, teacherId);

            if (resultSet.next()) {
                // Fetch current data
                String currentName = resultSet.getString("name");
                String currentPhoneNumber = resultSet.getString("phone_number");
                String currentEmail = resultSet.getString("email");
                String currentSubject = resultSet.getString("subject");
                String currentGrades = resultSet.getString("grades");

                String gradesString = String.join(",", grades);

                System.out.println("DB Name: " + currentName + " | Input Name: " + name);
                System.out.println("DB Phone: " + currentPhoneNumber + " | Input Phone: " + phoneNumber);
                System.out.println("DB Grades: " + currentGrades + " | Input Grades: " + gradesString);

                return currentName.equals(name) &&
                        currentPhoneNumber.equals(phoneNumber) &&
                        currentEmail.equals(email) &&
                        currentSubject.equals(subject) &&
                        (currentGrades != null && currentGrades.equals(gradesString));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean deleteTeacherById(String teacherId) {
        Connection connection = null;
        boolean isTeacherGradesDeleted = false;
        boolean isTeacherDeleted = false;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            String deleteTeacherGradesQuery = "DELETE FROM teacher_grade WHERE teacher_id = ?";
            isTeacherGradesDeleted = CrudUtil.execute(deleteTeacherGradesQuery, teacherId);

            if (!isTeacherGradesDeleted) {
                connection.rollback();
                return false;
            }


            String deleteTeacherQuery = "DELETE FROM teacher WHERE t_id = ?";
            isTeacherDeleted = CrudUtil.execute(deleteTeacherQuery, teacherId);


            if (!isTeacherDeleted) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getEmailByTeacherID(String selectedTeacherId) {
        try {
            ResultSet resultSet = CrudUtil.execute("select email from teacher where t_id = ?", selectedTeacherId);
            if (resultSet.next()) {

                return resultSet.getString("email");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return "Not in database";
    }

    
    public int getTeacherCount() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM teacher");

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}