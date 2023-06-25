package com.database.DatabaseConnectivity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private Boolean isSeniorCitizen;
}
