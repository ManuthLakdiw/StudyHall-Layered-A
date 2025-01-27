package lk.ijse.gdse.instritutefirstsemfinal.dao;

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
        USER
    }

    public SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case USER:
                return new UserDAOImpl();

            default:
                return null;
        }

    }

}
