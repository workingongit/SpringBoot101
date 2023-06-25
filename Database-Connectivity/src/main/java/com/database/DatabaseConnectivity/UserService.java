package com.database.DatabaseConnectivity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

//Which data to get
//What to do after getting the data
//Do you want to filter out any data
@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    //DAO level is taking user object and service layer is using Usercreaterequest object with less parameter so we need to convert
    public void createUser(UserCreateRequest userCreateRequest) throws SQLException {
        userDAO.createUser(userCreateRequest.toUser());
    }

    public User deleteUser(int userId) throws SQLException {
        return userDAO.delete(userId);
    }

    public List<User> getUsers() throws SQLException {
        return userDAO.getUsers();
    }

    public User getUserById(int userId) throws SQLException {
        return userDAO.getUserById(userId);
    }

    public void updateUser(int userId,UserUpdateRequest userUpdateRequest) throws SQLException {
        userDAO.updateUser(userId,userUpdateRequest.toUser());
    }
}
