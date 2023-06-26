package com.example.DatabaseConnectivityJpa;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private Boolean isSeniorCitizen;

    private Boolean checkage(){
        return this.age>=60;
    }

    //Converting UserUpdaterequest to User object
    public User toUser(){
        return User.builder().firstName(firstName).age(age).lastName(lastName).email(email).
                isSeniorCitizen(isSeniorCitizen == null ? checkage():isSeniorCitizen).build();
    }
}
