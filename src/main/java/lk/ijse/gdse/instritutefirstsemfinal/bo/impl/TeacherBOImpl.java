package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.TeacherBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherGradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.SubjectGrade;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Teacher;
import lk.ijse.gdse.instritutefirstsemfinal.entity.TeacherGrade;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherBOImpl implements TeacherBO {

    TeacherDAO teacherDAO = (TeacherDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TEACHER);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    TeacherGradeDAO teacherGradeDAO = (TeacherGradeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TEACHER_GRADE);

    SubjectDAO subjectDAO = (SubjectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUBJECT);

    @Override
    public ArrayList<TeacherDto> getAllTeachersAndRelatedGrades() throws SQLException {
        ArrayList<TeacherCustom> teacherEntities = queryDAO.getAllTeachersAndRelatedGrades();
        ArrayList<TeacherDto> teacherDtos = new ArrayList<>();
        for (TeacherCustom teacherCustom : teacherEntities) {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setTeacherId(teacherCustom.getTeacherId());
            teacherDto.setName(teacherCustom.getName());
            teacherDto.setPhoneNumber(teacherCustom.getPhoneNumber());
            teacherDto.setEmail(teacherCustom.getEmail());
            teacherDto.setSubject(teacherCustom.getSubject());
            teacherDto.setGrades(teacherCustom.getGrades());
            teacherDtos.add(teacherDto);
        }
        return teacherDtos;
    }

    @Override
    public boolean saveTeacher(TeacherDto teacherDto, List<String> grades) throws SQLException {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Teacher teacherEntity = new Teacher();
            teacherEntity.setTeacherId(teacherDto.getTeacherId());
            teacherEntity.setTeacherName(teacherDto.getName());
            teacherEntity.setPhoneNumber(teacherDto.getPhoneNumber());
            teacherEntity.setEmail(teacherDto.getEmail());
            teacherEntity.setSubjectID(subjectDAO.getSubjectIDFromName(teacherDto.getSubject()));

            if (!teacherDAO.save(teacherEntity)){
                System.out.println("teacher table doesnt save");
                connection.rollback();
                return false;
            }
            System.out.println("teacher table saved");

            String[] gradeIdsArray = grades.toArray(new String[0]);
            TeacherGrade teacherGrade = new TeacherGrade();
            teacherGrade.setTeacherID(teacherDto.getTeacherId());
            teacherGrade.setGrade(gradeIdsArray);

            if (!teacherGradeDAO.save(teacherGrade)){
                System.out.println("teacher grade table doesnt save");
                connection.rollback();
                return false;

            }
            System.out.println("teacher grade table saved");
            connection.commit();
            return true;


        }catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        }finally {
            connection.setAutoCommit(true);

        }
    }

    @Override
    public boolean updateTeacher(TeacherDto teacherDto, List<String> grades) throws SQLException {
        return false;
    }


}
