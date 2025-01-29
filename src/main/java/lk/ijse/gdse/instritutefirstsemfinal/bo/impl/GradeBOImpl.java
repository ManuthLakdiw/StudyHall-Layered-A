package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.GradeBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.GradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.impl.GradeDAOImpl;
import lk.ijse.gdse.instritutefirstsemfinal.dto.GradeDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Grade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeBOImpl implements GradeBO {

    GradeDAO gradeDAO = (GradeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.GRADE);

    @Override
    public List<String> getGradeIdsFromNames(List<String> gradeNames) throws SQLException {
        return gradeDAO.getGradeIdsFromNames(gradeNames);
    }

    @Override
    public String getGradeIdFromName(String grade) throws SQLException {
        return gradeDAO.getGradeIdFromName(grade);
    }

    @Override
    public ArrayList<GradeDto> getAllGrades() throws SQLException, ClassNotFoundException {
        ArrayList<GradeDto> grades = new ArrayList<>();
        ArrayList<Grade> allGrades = gradeDAO.getAll();
        for (Grade grade : allGrades) {
            GradeDto gradeDto = new GradeDto();
            gradeDto.setGradeId(grade.getGradeId());
            gradeDto.setGradeName(grade.getGradeName());
            grades.add(gradeDto);
        }
        return grades;
    }
}
