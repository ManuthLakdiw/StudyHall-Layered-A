package lk.ijse.gdse.instritutefirstsemfinal.model;

import lk.ijse.gdse.instritutefirstsemfinal.dto.UserDto;
import lk.ijse.gdse.instritutefirstsemfinal.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserModel {


    public boolean verifyUser(String username, String password) {
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM user WHERE user_name=? AND pass_word=?",username,password);

            if (rs.next()) {
                if (rs.getString("user_name").equals(username) && rs.getString("pass_word").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Object> checkGmailInDB(String email) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE email=?", email);
            if (resultSet.next()) {
                String resultEmail = resultSet.getString("email");
                String adminName = resultSet.getString("user_name");
                return Arrays.asList(true, resultEmail, adminName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Arrays.asList(false, null, null);
    }


    public boolean updateUserPassword(String newPassword, String gmail) {
        System.out.println("Attempting to update password for email: " + gmail);
        try {

            Boolean isUpdated = CrudUtil.execute("UPDATE user SET pass_word = ? WHERE email = ?", newPassword, gmail);

            return isUpdated != null && isUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<UserDto> getAllUsers() {
        try {
            ArrayList<UserDto> users = new ArrayList<>();
            ResultSet rst = CrudUtil.execute("select * from user");
            while (rst.next()) {
                UserDto userDto = new UserDto(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4)
                );
                users.add(userDto);
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }


    public boolean saveUser(UserDto userDto) {
        try {
            return CrudUtil.execute(
                    "insert into user values(?,?,?,?)",
                    userDto.getUsName(),
                    userDto.getUsPassword(),
                    userDto.getUsEmail(),
                    userDto.getUsPhone()
            );

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateUser(UserDto userDto) {
        try {
            return CrudUtil.execute("update user set pass_word=?, email=?, phone_number=? where user_name=?",
                    userDto.getUsPassword(),
                    userDto.getUsEmail(),
                    userDto.getUsPhone(),
                    userDto.getUsName()
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public UserDto getUserByUserName(String usName) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT pass_word, email, phone_number FROM user WHERE user_name = ?", usName);

            if (rst.next()) {
                return new UserDto(
                        usName,
                        rst.getString("pass_word"),
                        rst.getString("email"),
                        rst.getString("phone_number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean deleteUser(String userName) {
        try {
            return CrudUtil.execute("delete from user where user_name = ?", userName);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


//    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from customer");
//
//        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
//
//        while (rst.next()) {
//            CustomerDTO customerDTO = new CustomerDTO(
//                    rst.getString(1),  // Customer ID
//                    rst.getString(2),  // Name
//                    rst.getString(3),  // NIC
//                    rst.getString(4),  // Email
//                    rst.getString(5)   // Phone
//            );
//            customerDTOS.add(customerDTO);
//        }
//        return customerDTOS;
//    }
}
