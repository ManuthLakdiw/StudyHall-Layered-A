package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class ResultModel {

    SubjectModel subjectModel = new SubjectModel();
    StudentModel studentModel = new StudentModel();
    GradeModel gradeModel = new GradeModel();


    public String getNextResultID(){
        try {
            ResultSet resultSet = CrudUtil.execute("select result_id from result order by result_id desc limit 1");
            if (resultSet.next()) {
                String lastID = resultSet.getString(1);
                String substring = lastID.substring(1);
                int number = Integer.parseInt(substring);
                int newId = ++number;
                return String.format("R%03d", newId);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "R001";


    }

    public ArrayList<ResultDto> getAllResults() {
        ArrayList<ResultDto> results = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT r.result_id,r.grade , e.subject_id, r.exam_id, r.student_id, r.marks, r.exam_grade, r.status FROM result r LEFT JOIN exam e ON r.exam_id = e.exam_id");
            while (resultSet.next()) {
                String gradeName =  gradeModel.getGradeNameFromID(resultSet.getString("grade"));
                String studentName = studentModel.getOneStudentById(resultSet.getString("student_id"));
                String subjectName = subjectModel.getSubjectNameFromId(resultSet.getString("subject_id"));
                ResultDto resultDto = new ResultDto(
                        resultSet.getString(1),
                        gradeName,
                        subjectName,
                        resultSet.getString(4),
                        studentName,
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)

                );
                results.add(resultDto);
            }
            return results;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] getExamSubjectsByGrade(String grade) {
        ArrayList<String> subjectsList = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT subject_id FROM exam WHERE grade =?", grade);
            while (resultSet.next()) {
                subjectsList.add(resultSet.getString("subject_id"));
            }
            return subjectsList.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[0];
    }

    public ArrayList<String> getStudentsByGradeAndSubject(String gradeId, String subjectId) {
        ArrayList<String> studentNames = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT s.name FROM student s " +
                            "JOIN student_subject ss ON s.s_id = ss.student_id " +
                            "WHERE s.grade = ? AND ss.subject_id = ?",
                    gradeId, subjectId
            );

            while (resultSet.next()) {
                studentNames.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentNames;
    }


    public boolean saveResult(ResultDto resultDto) {
        String studentID = studentModel.getStudentIDFromName(resultDto.getStudent());
        String grade = gradeModel.getGradeIdFromName(resultDto.getGrade());
        try {
            return CrudUtil.execute("INSERT INTO result (result_id, exam_id, student_id, marks, exam_grade, status, grade) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    resultDto.getResultID(),
                    resultDto.getExam(),
                    studentID,
                    resultDto.getMarks(),
                    resultDto.getGradeArchieved(),
                    resultDto.getStatus(),
                    grade
            );


        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateResult(ResultDto resultDto) {
        String studentID = studentModel.getStudentIDFromName(resultDto.getStudent());
        String grade = gradeModel.getGradeIdFromName(resultDto.getGrade());
        try {
            return CrudUtil.execute("UPDATE result SET exam_id = ?, student_id = ?, marks = ?, exam_grade = ?, status = ?, grade = ? WHERE result_id = ?",
                    resultDto.getExam(),
                    studentID,
                    resultDto.getMarks(),
                    resultDto.getGradeArchieved(),
                    resultDto.getStatus(),
                    grade,
                    resultDto.getResultID()
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteResult(String resultId) {
        try {
            return CrudUtil.execute("DELETE FROM result WHERE result_id = ?", resultId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<ResultDto> checkExitingResult(String resultId) {
        ArrayList<ResultDto> results = new ArrayList<>();

        try {
            // SQL query execute කිරීම
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT r.result_id, r.grade, e.subject_id, r.exam_id, r.student_id, r.marks, r.exam_grade, r.status " +
                            "FROM result r " +
                            "LEFT JOIN exam e ON r.exam_id = e.exam_id " +
                            "WHERE r.result_id = ?",
                    resultId // Placeholder එකට value pass කිරීම
            );

            while (resultSet.next()) {
                String subject = subjectModel.getSubjectNameFromId(resultSet.getString("subject_id"));
                String studentName = studentModel.getOneStudentById(resultSet.getString("student_id"));
                String gradeName = gradeModel.getGradeNameFromID(resultSet.getString("grade"));

                ResultDto resultDto = new ResultDto(
                        resultSet.getString(1),
                        gradeName,
                        subject,
                        resultSet.getString(4),
                        studentName,
                        resultSet.getInt(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                );

                results.add(resultDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

}
