package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.SubjectBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectBOImpl implements SubjectBO {

    SubjectDAO subjectDAO = (SubjectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUBJECT);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public ArrayList<SubjectDto> getAllSubjectsAndRelatedGrades() throws SQLException {
        ArrayList<SubjectDto> subjects = new ArrayList<>();
        ArrayList<SubjectCustom> subjectCustom =  queryDAO.getAllSubjectsAndRelatedGrades();
        for (SubjectCustom subject : subjectCustom) {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setSubjectId(subject.getSubjectId());
            subjectDto.setSubjectName(subject.getSubjectName());
            subjectDto.setSubjectGrades(subject.getSubjectGrades());
            subjectDto.setSubjectDescription(subject.getSubjectDescription());
            subjects.add(subjectDto);

        }
        return subjects;
    }
}
