package lk.ijse.gdse.instritutefirstsemfinal.dao.impl;

import lk.ijse.gdse.instritutefirstsemfinal.dao.agreement.UserDAO;
import lk.ijse.gdse.instritutefirstsemfinal.entity.User;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAOImpl implements UserDAO {


    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user");
        while (resultSet.next()) {
            User user = new User();
            user.setUserName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
            user.setEmail(resultSet.getString(3));
            user.setPhoneNumber(resultSet.getString(4));
            users.add(user);
        }
        return users;
    }


    @Override
    public boolean save(User entity) throws SQLException, ClassNotFoundException {

         return CrudUtil.execute(
                "insert into user values (?,?,?,?)",
                entity.getUserName(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getPhoneNumber()
        );
    }


    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "update user set pass_word=?, email=?, phone_number=? where user_name=?",
                entity.getPassword(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getUserName()
        );
    }


    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return CrudUtil.execute(
                "delete from user where user_name = ? " ,
                id
        );
    }


    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "select * from user where user_name = ?",
                id
        );
        if (resultSet.next()) {
            User user = new User();
            user.setUserName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
            user.setEmail(resultSet.getString(3));
            user.setPhoneNumber(resultSet.getString(4));
            return user;
        }
        return null;
    }


    @Override
    public boolean verifyUser(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM user WHERE user_name=? AND pass_word=?",
                username,
                password
        );

        if (resultSet.next()) {
            if (resultSet.getString("user_name").equals(username) && resultSet.getString("pass_word").equals(password)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Object> checkGmailInDB(String mail) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM user WHERE email=?",
                mail
        );

        if (resultSet.next()) {
            String resultEmail = resultSet.getString("email");
            String adminName = resultSet.getString("user_name");
            return Arrays.asList(true, resultEmail, adminName);
        }

        return Arrays.asList(false, null, null);
    }


    @Override
    public boolean updateUserPassword(String newPassword, String mail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE user SET pass_word = ? WHERE email = ?" ,
                newPassword ,
                mail
        );

    }







    @Override
    public User exist(String id) throws SQLException, ClassNotFoundException {
        return null;

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return "";
    }











}
