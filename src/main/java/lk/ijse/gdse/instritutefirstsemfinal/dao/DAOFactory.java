package lk.ijse.gdse.instritutefirstsemfinal.dao;

import lk.ijse.gdse.instritutefirstsemfinal.dao.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {}


    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOType {
        USER , QUERY , SUBJECT , SUBJECT_GRADE, GRADE , TEACHER , TEACHER_GRADE , STUDENT
    }

    public SuperDAO getDAO(DAOType daoType) {
        return switch (daoType) {
            case USER -> new UserDAOImpl();
            case QUERY -> new QueryDAOImpl();
            case SUBJECT -> new SubjectDAOImpl();
            case SUBJECT_GRADE -> new SubjectGradeDAOImpl();
            case GRADE -> new GradeDAOImpl();
            case TEACHER -> new TeacherDAOImpl();
            case TEACHER_GRADE -> new TeacherGradeDAOImpl();
            case STUDENT -> new StudentDAOImpl();

            default -> null;
        };

    }

}
