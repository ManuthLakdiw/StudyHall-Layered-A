package lk.ijse.gdse.instritutefirstsemfinal.bo.impl;

import lk.ijse.gdse.instritutefirstsemfinal.bo.agreement.UserBO;
import lk.ijse.gdse.instritutefirstsemfinal.dao.DAOFactory;
import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.UserDAO;
import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);


    @Override
    public ArrayList<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> userEntities =  userDAO.getAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : userEntities) {
            UserDto userDto = new UserDto();
            userDto.setUsName(user.getUserName());
            userDto.setUsPassword(user.getPassword());
            userDto.setUsEmail(user.getEmail());
            userDto.setUsPhone(user.getPhoneNumber());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(userDto.getUsName(), userDto.getUsPassword(), userDto.getUsEmail(), userDto.getUsPhone()));
    }

    @Override
    public boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(userDto.getUsName(), userDto.getUsPassword(), userDto.getUsEmail(), userDto.getUsPhone()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public UserDto searchUser(String id) throws SQLException, ClassNotFoundException {
        User userEntities = userDAO.search(id);
        return new UserDto(
                userEntities.getUserName(),
                userEntities.getPassword(),
                userEntities.getEmail(),
                userEntities.getPhoneNumber()
        );
    }

    @Override
    public boolean verifyUser(String username, String password) throws SQLException, ClassNotFoundException {
        return userDAO.verifyUser(username, password);
    }

    @Override
    public List<Object> checkGmailInDB(String mail) throws SQLException, ClassNotFoundException {
        return userDAO.checkGmailInDB(mail);
    }

    @Override
    public boolean updateUserPassword(String newPassword, String mail) throws SQLException, ClassNotFoundException {
        return userDAO.updateUserPassword(newPassword, mail);
    }

}
