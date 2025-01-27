package lk.ijse.gdse.instritutefirstsemfinal.dao.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.dao.CrudDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO extends CrudDAO<User> {

    boolean verifyUser(String username, String password) throws SQLException , ClassNotFoundException;

    List<Object> checkGmailInDB(String mail) throws SQLException , ClassNotFoundException;

    boolean updateUserPassword(String newPassword , String mail) throws SQLException , ClassNotFoundException;

}
