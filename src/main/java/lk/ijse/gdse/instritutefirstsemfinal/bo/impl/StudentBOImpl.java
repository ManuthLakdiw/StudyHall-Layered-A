package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.StudentBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.StudentDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.StudentSubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.impl.StudentDAOImpl;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Student;
import lk.ijse.gdse.instritutefirstsemfinal.entity.StudentSubject;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.StudentCustom;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    StudentSubjectDAO studentSubjectDAO = (StudentSubjectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.STUDENT_SUBJECT);

    @Override
    public String generateNewStudentID() throws SQLException, ClassNotFoundException {
        return studentDAO.generateNewID();
    }

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

    @Override
    public boolean saveStudent(StudentDto studentDto, List<String> subjectIds) throws SQLException {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            Student studentEntity = new Student();
            studentEntity.setId(studentDto.getId());
            studentEntity.setBirthday(studentDto.getBirthday());
            studentEntity.setName(studentDto.getName());
            studentEntity.setEmail(studentDto.getEmail());
            studentEntity.setAddress(studentDto.getAddress());
            studentEntity.setAdmissionFee(studentDto.getAdmissionFee());
            studentEntity.setPhoneNumber(studentDto.getPhoneNumber());
            studentEntity.setParentName(studentDto.getParentName());
            studentEntity.setGrade(studentDto.getGrade());
            studentEntity.setAddedBy(studentDto.getAddedBy());

            if (!studentDAO.save(studentEntity)) {
                connection.rollback();
                return false;
            }

            String [] subjectID = subjectIds.toArray(new String[0]);

            StudentSubject studentSubject = new StudentSubject();
            studentSubject.setStudentID(studentDto.getId());
            studentSubject.setSubjectID(subjectID);

            if (!studentSubjectDAO.save(studentSubject)) {
                connection.rollback();
                return false;
            }


            connection.commit();
            return true;


        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            connection.rollback();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public boolean UpdateStudent(StudentDto studentDto, List<String> subjectIds) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Student studentEntity = new Student();
            studentEntity.setId(studentDto.getId());
            studentEntity.setBirthday(studentDto.getBirthday());
            studentEntity.setName(studentDto.getName());
            studentEntity.setEmail(studentDto.getEmail());
            studentEntity.setAddress(studentDto.getAddress());
            studentEntity.setAdmissionFee(studentDto.getAdmissionFee());
            studentEntity.setPhoneNumber(studentDto.getPhoneNumber());
            studentEntity.setParentName(studentDto.getParentName());
            studentEntity.setGrade(studentDto.getGrade());
            studentEntity.setAddedBy(studentDto.getAddedBy());

            if (!studentDAO.update(studentEntity)){
                connection.rollback();
                return false;
            }
            System.out.println("Student Table updated");

            if (!studentSubjectDAO.delete(studentDto.getId())){
                connection.rollback();
                return false;
            }
            System.out.println("studentSubject table updated");

            String [] subjectID = subjectIds.toArray(new String[0]);

            StudentSubject studentSubject = new StudentSubject();
            studentSubject.setStudentID(studentDto.getId());
            studentSubject.setSubjectID(subjectID);

            if (!studentSubjectDAO.save(studentSubject)){
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }

    @Override
    public boolean deleteStudent(String studentId) throws SQLException, ClassNotFoundException {
        return studentDAO.delete(studentId);
    }

    @Override
    public ArrayList<StudentDto> getStudentAllDetailsByID(String studentId) throws SQLException, ClassNotFoundException {
        ArrayList<StudentDto> studentDtos = new ArrayList<>();
        ArrayList<StudentCustom> studentCustoms = queryDAO.getStudentAllDetailsByID(studentId);

        for (StudentCustom studentCustom : studentCustoms) {
            StudentDto studentDto = new StudentDto();

            studentDto.setId(studentCustom.getId());
            studentDto.setBirthday(studentCustom.getBirthday());
            studentDto.setName(studentCustom.getName());
            studentDto.setEmail(studentCustom.getEmail());
            studentDto.setAddress(studentCustom.getAddress());
            studentDto.setAdmissionFee(studentCustom.getAdmissionFee());
            studentDto.setPhoneNumber(studentCustom.getPhoneNumber());
            studentDto.setParentName(studentCustom.getParentName());
            studentDto.setGrade(studentCustom.getGrade());
            studentDto.setAddedBy(studentCustom.getAddedBy());
            studentDto.setSubjects(studentCustom.getSubjects());
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }
}
