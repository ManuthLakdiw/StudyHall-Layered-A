package lk.ijse.gdse.instritutefirstsemfinal.dao;

import lk.ijse.gdse.instritutefirstsemfinal.dao.impl.QueryDAOImpl;
import lk.ijse.gdse.instritutefirstsemfinal.dao.impl.UserDAOImpl;

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
        USER , QUERY
    }

    public SuperDAO getDAO(DAOType daoType) {
        return switch (daoType) {
            case USER -> new UserDAOImpl();
            case QUERY -> new QueryDAOImpl();

            default -> null;
        };

    }

}
