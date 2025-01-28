package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.TeacherBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.TeacherDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.TeacherDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.TeacherCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherBOImpl implements TeacherBO {

//    TeacherDAO teacherDAO = (TeacherDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TEACHER);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

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
}
