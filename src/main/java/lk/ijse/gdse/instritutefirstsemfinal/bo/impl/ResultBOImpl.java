package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.ResultBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ResultDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Result;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.ResultCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResultBOImpl implements ResultBO {

    ResultDAO resultDAO = (ResultDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESULT);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public String generateNewResultID() throws SQLException, ClassNotFoundException {
        return resultDAO.generateNewID();
    }

    @Override
    public ArrayList<ResultDto> getAllResultsWithSubjects() throws SQLException {
        ArrayList<ResultDto> resultDtos = new ArrayList<>();
        ArrayList<ResultCustom> resultEntity = queryDAO.getAllResultsWithSubjects();
        for (ResultCustom resultCustom : resultEntity) {
            ResultDto resultDto = new ResultDto();

            resultDto.setSubject(resultCustom.getSubject());
            resultDto.setResultID(resultCustom.getResultID());
            resultDto.setGrade(resultCustom.getGrade());
            resultDto.setMarks(resultCustom.getMarks());
            resultDto.setGradeArchieved(resultCustom.getGradeArchived());
            resultDto.setStatus(resultCustom.getStatus());
            resultDto.setExam(resultCustom.getExam());
            resultDto.setStudent(resultCustom.getStudent());
            resultDtos.add(resultDto);
        }
        return resultDtos;
    }

    @Override
    public boolean saveResult(ResultDto resultDto) throws SQLException, ClassNotFoundException {
        Result resultEntity = new Result();

        resultEntity.setResultID(resultDto.getResultID());
        resultEntity.setMarks(resultDto.getMarks());
        resultEntity.setGrade(resultDto.getGrade());
        resultEntity.setExam(resultDto.getExam());
        resultEntity.setStudent(resultDto.getStudent());
        resultEntity.setStatus(resultDto.getStatus());
        resultEntity.setGradeArchived(resultDto.getGradeArchieved());

        return resultDAO.save(resultEntity);

    }

    @Override
    public boolean updateResult(ResultDto resultDto) throws SQLException, ClassNotFoundException {
        Result resultEntity = new Result();

        resultEntity.setResultID(resultDto.getResultID());
        resultEntity.setMarks(resultDto.getMarks());
        resultEntity.setGrade(resultDto.getGrade());
        resultEntity.setExam(resultDto.getExam());
        resultEntity.setStudent(resultDto.getStudent());
        resultEntity.setStatus(resultDto.getStatus());
        resultEntity.setGradeArchived(resultDto.getGradeArchieved());

        return resultDAO.update(resultEntity);
    }

    @Override
    public boolean deleteResult(String id) throws SQLException, ClassNotFoundException {

        return resultDAO.delete(id);
    }

    @Override
    public ArrayList<ResultDto> checkExitingResult(String resultId) throws SQLException {
        ArrayList<ResultDto> resultDtos = new ArrayList<>();
        ArrayList<ResultCustom> resultEntity = queryDAO.checkExitingResult(resultId);
        for (ResultCustom resultCustom : resultEntity) {
            ResultDto resultDto = new ResultDto();

            resultDto.setResultID(resultCustom.getResultID());
            resultDto.setMarks(resultCustom.getMarks());
            resultDto.setGrade(resultCustom.getGrade());
            resultDto.setExam(resultCustom.getExam());
            resultDto.setStudent(resultCustom.getStudent());
            resultDto.setStatus(resultCustom.getStatus());
            resultDto.setGradeArchieved(resultCustom.getGradeArchived());
            resultDto.setSubject(resultCustom.getSubject());

            resultDtos.add(resultDto);
        }
        return resultDtos;
    }


}
