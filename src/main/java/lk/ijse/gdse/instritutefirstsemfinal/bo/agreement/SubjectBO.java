package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SubjectBO extends SuperBO {

    ArrayList<SubjectDto> getAllSubjectsAndRelatedGrades() throws SQLException;

    boolean saveSubject(SubjectDto subjectDto, List<String> gradeIds) throws SQLException;
}
