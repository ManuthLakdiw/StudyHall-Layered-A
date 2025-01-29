package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ResultDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Result;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.ResultCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResultBO extends SuperBO {


    String generateNewResultID() throws SQLException, ClassNotFoundException;

    ArrayList<ResultDto> getAllResultsWithSubjects() throws SQLException;

    boolean saveResult(ResultDto resultDto) throws SQLException, ClassNotFoundException;

    boolean updateResult(ResultDto resultDto) throws SQLException, ClassNotFoundException;

    boolean deleteResult(String id) throws SQLException, ClassNotFoundException;

    ArrayList<ResultDto> checkExitingResult(String resultId) throws SQLException;
}
