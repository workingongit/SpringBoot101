package com.database.DatabaseConnectivity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//Before executing any of the function object of UserDAO will be created
@Component
public class UserDAO {

    public static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    //Once the object is created I want to initialize
    private final Connection connection;

    public UserDAO() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "");
        createTable();
    }

    //create table alpha(id int primary key auto_increment,name varchar(30));
    //insert into alpha(name) values ('Raj');
    //select * from alpha;

    //insert into alpha(id,name) values (17,'Raj');
    //select * from alpha;

    //insert into alpha(name) values ('Ram');
    //select * from alpha;

    //insert into alpha(id,name) values (10,'Sid');
    //select * from alpha;

    //insert into alpha(name) values ('Ram');
    //select * from alpha;

    //id as not null does not make any sense because in java there would be a default value associated to int value
    //if auto increment is removed and don't pass any id -> one time it does the insertion after that it throws error
    //if we change the datatype of id in User class then by default value would be null and insertion would fail at first insertion
    //Table should be created at start of SpringBoot application
    //If we run the springboot application again it will give an error table already exists
    //by adding if not exists it would not throw any error
    //Difference between execute and executeQuery
    public void createTable() throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.execute("create table if not exists my_user (id int primary key auto_increment not null, firstName varchar(20), lastName varchar(10), age int, email varchar(50), isSeniorCitizen bool)");

    }

    public void createUser(User user) throws SQLException {
        //With prepared statement dynamic insertion is possible
        //why we are not setting in parameter

        //With all type of program -> first compilation happens then execution
        //Here it will compile once and execution just changing the parameter
        PreparedStatement preparedStatement = this.connection.prepareStatement("insert into my_user (firstName,lastName,age,email,isSeniorCitizen) values (?,?,?,?,?)");
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setBoolean(5, user.getIsSeniorCitizen());
        Boolean result = preparedStatement.execute();

        //You are not getting anything back ie resultset from db server so result will be false
        //Inserting value does not involve resultset
        logger.debug("the result of insert operation is {}", result);

    }

    //We need to create a table before
    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select * from my_user");

        while (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");
            Boolean isSeniorCitizen = resultSet.getBoolean("isSeniorCitizen");
            String email = resultSet.getString("email");

            User user = User.builder().firstName(firstName).lastName(lastName).id(userId).age(age).isSeniorCitizen(isSeniorCitizen).email(email).build();
            userList.add(user);
        }

        return userList;
    }

    public User getUserById(int id) throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select * from my_user where id = " + id);

        //If there have been 30 property code will be very lengthy and error prone
        //ORM framework - Hibernates (Java to DB mapping) JPA implemented by hibernates
        //JPA directly talk to the driver and driver talks to db to get the data
        //JPA is a contract and Hibernate is one of the implementer of JPA. There are any implementer openJPA and other but Hibernate is default one
        //Hiberante ,JPA, JDBC is for relational db not for nosql db
        while (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");
            Boolean isSeniorCitizen = resultSet.getBoolean("isSeniorCitizen");
            String email = resultSet.getString("email");

            User user = User.builder().firstName(firstName).lastName(lastName).id(userId).age(age).isSeniorCitizen(isSeniorCitizen).email(email).build();
            return user;
        }

        return null;
    }

    public User delete(int id) throws SQLException {
        Statement statement = this.connection.createStatement();
        User user = getUserById(id);
        int resultSet = statement.executeUpdate("Delete from my_user where id = " + id);
        logger.debug("Row updated {}", resultSet);
        return user;
    }

    public void updateUser(int id, User user) throws SQLException {
        Statement statement = this.connection.createStatement();
        String sqlquery = "update my_user set ";
        if (user.getAge() != 0) {
            sqlquery += "age = " + user.getAge();
        }
        if (user.getEmail() != null) {
            sqlquery += " ,email = '"+user.getEmail()+"'";
        }
        if (user.getFirstName() != null) {
            sqlquery += " ,firstName = '"+ user.getFirstName() +"'";
        }
        if (user.getLastName() != null) {
            sqlquery += " ,lastName = '"+ user.getLastName()+"'";
        }
        if (user.getIsSeniorCitizen() != null) {
            sqlquery += " ,isSeniorCitizen = "+ user.getIsSeniorCitizen();
        }
        sqlquery += " where id =" + id;
        statement.executeUpdate(sqlquery);
    }
}
