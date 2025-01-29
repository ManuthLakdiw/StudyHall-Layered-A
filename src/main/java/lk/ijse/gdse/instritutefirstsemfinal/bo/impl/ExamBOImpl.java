package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.ExamBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ExamDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;

import java.sql.SQLException;

public class ExamBOImpl implements ExamBO {

    ExamDAO examDAO = (ExamDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EXAM);

    @Override
    public String generateNewExamID() throws SQLException, ClassNotFoundException {
        return examDAO.generateNewID();
    }

    @Override
    public boolean saveExam(ExamDto examDto) throws SQLException, ClassNotFoundException {
        Exam exam = new Exam();
        exam.setExamId(examDto.getExamId());
        exam.setSubject(examDto.getSubject());
        exam.setExamType(examDto.getExamType());
        exam.setExamDate(examDto.getExamDate());
        exam.setDescription(examDto.getExamDescription());
        exam.setGrade(examDto.getGrade());

        return examDAO.save(exam);
    }

    @Override
    public boolean updateExam(ExamDto examDto) throws SQLException, ClassNotFoundException {
        Exam exam = new Exam();

        exam.setExamId(examDto.getExamId());
        exam.setSubject(examDto.getSubject());
        exam.setExamType(examDto.getExamType());
        exam.setExamDate(examDto.getExamDate());
        exam.setDescription(examDto.getExamDescription());
        exam.setGrade(examDto.getGrade());

        return examDAO.update(exam);

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return examDAO.delete(id);
    }

    @Override
    public String[] getExamIDsUsingSubject(String subject) throws SQLException {
        return examDAO.getExamIDsUsingSubject(subject);
    }

    @Override
    public int getExamCount() throws SQLException {
        return examDAO.getExamCount();
    }


}
