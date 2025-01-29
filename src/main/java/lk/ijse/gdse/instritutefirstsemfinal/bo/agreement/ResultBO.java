package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.ResultDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.ResultDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.ResultCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResultBO extends SuperBO {

    ArrayList<ResultDto> getAllResultsWithSubjects() throws SQLException;
}
