package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.ExamBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ExamDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ExamDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Exam;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExamBOImpl implements ExamBO {

    ExamDAO examDAO = (ExamDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EXAM);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public String generateNewExamID() throws SQLException, ClassNotFoundException {
        return examDAO.generateNewID();
    }

    @Override
    public ArrayList<ExamDto> getAllExamsAndApplicableSubjectNames() throws SQLException {
        ArrayList<ExamDto> examDtos = new ArrayList<>();
        ArrayList<Exam> examEntities = queryDAO.getAllExamsAndApplicableSubjectNames();

        for (Exam exam : examEntities) {
            ExamDto examDto = new ExamDto();

            examDto.setExamId(exam.getExamId());
            examDto.setSubject(exam.getSubject());
            examDto.setExamDate(exam.getExamDate());
            examDto.setExamDescription(exam.getDescription());
            examDto.setExamType(exam.getExamType());
            examDto.setGrade(exam.getGrade());
            examDtos.add(examDto);
        }

        return examDtos;
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
    public boolean deleteExam(String id) throws SQLException, ClassNotFoundException {
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

    @Override
    public ArrayList<ExamDto> ExistExam(String examID) throws SQLException {
        ArrayList<ExamDto> examDtos = new ArrayList<>();
        ArrayList<Exam> examEntities = examDAO.ExistExam(examID);

        for (Exam exam : examEntities) {
            ExamDto examDto = new ExamDto();
            examDto.setExamId(exam.getExamId());
            examDto.setSubject(exam.getSubject());
            examDto.setExamDate(exam.getExamDate());
            examDto.setExamDescription(exam.getDescription());
            examDto.setExamType(exam.getExamType());
            examDto.setGrade(exam.getGrade());
            examDtos.add(examDto);
        }
        return examDtos;

    }

    @Override
    public String[] getExamSubjectsByGrade(String grade) throws SQLException {
        return examDAO.getExamSubjectsByGrade(grade);
    }

    @Override
    public ExamDto getNextUpComingExam() throws SQLException {
        ExamDto examDto = new ExamDto();
        Exam examEntity = queryDAO.getNextUpComingExam();
        System.out.println(queryDAO.getNextUpComingExam());
        examDto.setExamId(examEntity.getExamId());
        examDto.setSubject(examEntity.getSubject());
        examDto.setExamDate(examEntity.getExamDate());
        examDto.setExamDescription(examEntity.getDescription());
        examDto.setExamType(examEntity.getExamType());
        examDto.setGrade(examEntity.getGrade());
        return examDto;
    }


}
