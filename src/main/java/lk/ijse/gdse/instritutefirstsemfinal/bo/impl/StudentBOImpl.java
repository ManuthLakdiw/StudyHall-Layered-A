package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.StudentBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.StudentDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.impl.StudentDAOImpl;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.StudentCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public ArrayList<StudentDto> getAllStudentsWithLearnSubjects() throws SQLException {

        ArrayList<StudentDto> students = new ArrayList<>();
        ArrayList<StudentCustom> studentCustomEntities = queryDAO.getAllStudentsWithLearnSubjects();

        for (StudentCustom studentCustom : studentCustomEntities) {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(studentCustom.getId());
            studentDto.setBirthday(studentCustom.getBirthday());
            studentDto.setName(studentCustom.getName());
            studentDto.setEmail(studentCustom.getEmail());
            studentDto.setAddress(studentCustom.getAddress());
            studentDto.setSubjects(studentCustom.getSubjects());
            studentDto.setParentName(studentCustom.getParentName());
            studentDto.setGrade(studentCustom.getGrade());
            studentDto.setPhoneNumber(studentCustom.getPhoneNumber());
            studentDto.setAdmissionFee(studentCustom.getAdmissionFee());
            studentDto.setAddedBy(studentCustom.getAddedBy());

            students.add(studentDto);

        }
        return students;
    }
}
