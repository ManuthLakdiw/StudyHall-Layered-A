package lk.ijse.gdse.instritutefirstsemfinal.bo.agreement;

import lk.ijse.gdse.instritutefirstsemfinal.bo.SuperBO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {

    ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException;
    boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException;
    boolean updateUser( UserDto userDto) throws SQLException, ClassNotFoundException;
    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;
    UserDto searchUser(String id) throws SQLException, ClassNotFoundException;
    boolean verifyUser(String username, String password) throws SQLException , ClassNotFoundException;
    List<Object> checkGmailInDB(String mail) throws SQLException , ClassNotFoundException;
    boolean updateUserPassword(String newPassword , String mail) throws SQLException , ClassNotFoundException;

}
