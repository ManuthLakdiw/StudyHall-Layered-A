package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.StudentDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.StudentCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {

    ArrayList<StudentDto> getAllStudentsWithLearnSubjects() throws SQLException;
}
