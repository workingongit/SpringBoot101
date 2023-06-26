package com.example.DatabaseConnectivityJpa;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

//How to map user class to a table . How to tell hibernate to do that using @Entity
//We have to define schema as well. Right now DDL is none so it wont work
//create :- Initialize schema and whatever there before will be deleted .Once again when you stat it will delete first. Not viable in prod
//create-drop :- Initialize schema and delete if after you stop the application
//validate :- No changes to db
//update :- 99.99% of time use this. It creates schema for you and update. No data loss
@Entity // tell hibernate to map object to table in db
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
//Numbers Integer,Double are serializable as it can be converted into bytes
//But user object cannot be converted into bytes by itself
//Why required because in file you need to convert the data into bytes so you need to implement serializable
//Some function requires your object to be serializable
public class User implements Serializable {

    //create table user (id int primary key auto_increment
    //How does hibernates knows what is the primary key using @Id in the object
    /*
    GenerationType.IDENTITY :- Auto increment generated by mysql
    GenerationType.AUTO :- Auto increment generated by hibernates
    If there are 3 tables :- Person , User , Transaction :- If use AUTO then id changes as 1,2,3 respectively
    If use IDENTITY then id changes as 1,1,1 respectively
    Hibernates converts camel case to underscore -> firstName to first_name
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //Auto increment values
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    //Using @Column you can add constraints at column level
    private String email;
    @Column(name = "Senior Citizen")
    private Boolean isSeniorCitizen;
}
