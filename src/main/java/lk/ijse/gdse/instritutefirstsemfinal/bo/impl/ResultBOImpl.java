package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.ResultBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ResultDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.ResultCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResultBOImpl implements ResultBO {

    ResultDAO resultDAO = (ResultDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESULT);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

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
}
