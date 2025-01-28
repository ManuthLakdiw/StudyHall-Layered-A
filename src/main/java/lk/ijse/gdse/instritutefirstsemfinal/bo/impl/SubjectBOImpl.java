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

    SubjectGradeDAO subjectGradeDAO = (SubjectGradeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUBJECT_GRADE);

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
    public String generateNewSubjectID() throws SQLException, ClassNotFoundException {
        return subjectDAO.generateNewID();
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

    @Override
    public boolean updateSubject(SubjectDto subjectDto, List<String> gradeIds) throws SQLException, ClassNotFoundException {
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);


            String[] gradeIdsArray = gradeIds.toArray(new String[0]);
            SubjectGrade subjectGrade = new SubjectGrade();
            subjectGrade.setSubjectID(subjectDto.getSubjectId());
            subjectGrade.setGradeID(gradeIdsArray);


            Subject subjectEntity = new Subject();

            subjectEntity.setSubjectName(subjectDto.getSubjectName());
            subjectEntity.setDescription(subjectDto.getSubjectDescription());
            subjectEntity.setSubID(subjectDto.getSubjectId());

                if (!subjectDAO.update(subjectEntity)) {
                    connection.rollback();
                    return false;
                }


                if (!subjectGradeDAO.delete(subjectDto.getSubjectId())) {
                    connection.rollback();
                    return false;
                }

                if (!subjectGradeDAO.save(subjectGrade)) {
                    connection.rollback();
                    return false;
                }

                connection.commit();
                return true;

        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                connection.rollback();
            }
            return false;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }

    @Override
    public boolean deleteSubject(String subjectId) throws SQLException, ClassNotFoundException {
        return subjectDAO.delete(subjectId);
    }

    @Override
    public boolean existsSubjectByName(String subjectName) throws SQLException {
        return subjectDAO.existsSubjectByName(subjectName);
    }

    @Override
    public SubjectDto getExistsSubjectsAndRelatedGrades(String subjectID) throws SQLException {
        SubjectCustom subjectCustom =  queryDAO.getExistsSubjectsAndRelatedGrades(subjectID);
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setSubjectId(subjectCustom.getSubjectId());
        subjectDto.setSubjectName(subjectCustom.getSubjectName());
        subjectDto.setSubjectGrades(subjectCustom.getSubjectGrades());
        subjectDto.setSubjectDescription(subjectCustom.getSubjectDescription());
        return subjectDto;

    }

    @Override
    public List<String> getGradesForSubject(String subjectName) throws SQLException {
        return queryDAO.getGradesForSubject(subjectName);
    }


}
