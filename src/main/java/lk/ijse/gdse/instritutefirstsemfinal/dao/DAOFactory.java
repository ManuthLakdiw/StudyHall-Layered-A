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
        USER , QUERY , SUBJECT , SUBJECTGRADE ,GRADE , TEACHER
    }

    public SuperDAO getDAO(DAOType daoType) {
        return switch (daoType) {
            case USER -> new UserDAOImpl();
            case QUERY -> new QueryDAOImpl();
            case SUBJECT -> new SubjectDAOImpl();
            case SUBJECTGRADE -> new SubjectGradeDAOImpl();
            case GRADE -> new GradeDAOImpl();
            case TEACHER -> new TeacherDAOImpl();

            default -> null;
        };

    }

}
