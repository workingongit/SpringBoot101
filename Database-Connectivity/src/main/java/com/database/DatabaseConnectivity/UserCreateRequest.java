package com.database.DatabaseConnectivity;


import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserCreateRequest {

    @Size(max = 20)
    private String firstName;

    @NotBlank // Size not 0 + not null
    @Size(max = 10)
    private String lastName;

    @Min(value = 1)
    @Max(value = 100)
    private Integer age;

    @Email
    private String email;

    private Boolean isSeniorCitizen;

    private Boolean checkage(){
        return this.age>=60;
    }

    //Converting Userrequest to User object
    public User toUser(){
        return User.builder().firstName(firstName).age(age).lastName(lastName).email(email).
                isSeniorCitizen(isSeniorCitizen == null ? checkage():isSeniorCitizen).build();
    }

}
