package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.SubjectBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.QueryDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.SubjectGradeDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dbConnection.DBConnection;
import lk.ijse.gdse.instritutefirstsemfinal.dto.SubjectDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.Subject;
import lk.ijse.gdse.instritutefirstsemfinal.entity.SubjectGrade;
import lk.ijse.gdse.instritutefirstsemfinal.entity.custom.SubjectCustom;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectBOImpl implements SubjectBO {

    SubjectDAO subjectDAO = (SubjectDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUBJECT);

    QueryDAO queryDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.QUERY);

    SubjectGradeDAO subjectGradeDAO = (SubjectGradeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUBJECTGRADE);

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

    @Override
    public boolean saveSubject(SubjectDto subjectDto, List<String> gradeIds) throws SQLException {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            Subject subjectEntity = new Subject();
            subjectEntity.setSubID(subjectDto.getSubjectId());
            subjectEntity.setSubjectName(subjectDto.getSubjectName());
            subjectEntity.setDescription(subjectDto.getSubjectDescription());

            if (!subjectDAO.save(subjectEntity)) {
                connection.rollback();
                return false;
            }


            String[] gradeIdsArray = gradeIds.toArray(new String[0]);

            SubjectGrade subjectGrade = new SubjectGrade();
            subjectGrade.setSubjectID(subjectDto.getSubjectId());
            subjectGrade.setGradeID(gradeIdsArray);

            if (!subjectGradeDAO.save(subjectGrade)) {
                connection.rollback();
                return false;
            }

            connection.commit();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException autoCommitException) {
                    autoCommitException.printStackTrace();
                }
            }
        }
    }



}
