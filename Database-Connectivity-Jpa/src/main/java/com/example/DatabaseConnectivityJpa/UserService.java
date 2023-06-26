package com.example.DatabaseConnectivityJpa;


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
    public void createUser(UserCreateRequest userCreateRequest) {
        //save -> insert
        userDAO.save(userCreateRequest.toUser());
    }

    public User deleteUser(int userId)  {
       //User user = userDAO.getById(userId); User$HibernateProxy not returning user object
        User user = userDAO.findById(userId).orElse(null); //User@1506 coming as user object here
        userDAO.deleteById(userId);
        return user;
    }

    public List<User> getUsers()  {
        return userDAO.findAll();
    }

    public User getUserById(int userId)  {
         return userDAO.findById(userId).orElse(null);
    }

//    public void updateUser(int userId,UserUpdateRequest userUpdateRequest) throws SQLException {
//        userDAO.(userId,userUpdateRequest.toUser());
//    }
}
