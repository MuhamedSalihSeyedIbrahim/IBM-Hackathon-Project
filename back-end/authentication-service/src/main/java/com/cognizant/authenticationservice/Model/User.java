package com.cognizant.authenticationservice.Model;

import java.util.ArrayList;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class User extends RootModel {

    @NotNull(message = "First Name is mandatory")
    @Size(min = 3, max = 30, message = "First Name should be atleast 3 characters")
    private String firstName;

    @NotNull(message = "Last Name is mandatory")
    @Size(min = 3, max = 30, message = " Last Name should be atleast 3 characters")
    private String lastName;

    @NotNull(message = "Age is mandatory")
    @Min(value = 0)
    private int age;

    @NotNull(message = "Email is mandatory")
    @Email(message = "Follow email format")
    private String emailId;

    @NotNull(message = "Password is mandatory")
    private String password;

    @NotNull(message = "Mobile Number is mandatory")
    private String mobileNumber;

    @NotNull(message = "Gender is mandatory")
    private String gender;

    @NotNull(message = "User Type is mandatory")
    private String userType;

    @NotNull(message = "Status is mandatory")
    private String status;

    @NotNull(message = "Coordinates is mandatory")
    private Coordinates coordinate;
    private String pincode;
    private ArrayList<String> mappedId;

    public User(String _rev, String _id, String docType,
            @NotNull(message = "First Name is mandatory") @Size(min = 3, max = 30, message = "First Name should be atleast 3 characters") String firstName,
            @NotNull(message = "Last Name is mandatory") @Size(min = 3, max = 30, message = " Last Name should be atleast 3 characters") String lastName,
            @NotNull(message = "Age is mandatory") @Min(0) int age,
            @NotNull(message = "Email is mandatory") @Email(message = "Follow email format") String emailId,
            @NotNull(message = "Password is mandatory") String password,
            @NotNull(message = "Mobile Number is mandatory") String mobileNumber,
            @NotNull(message = "Gender is mandatory") String gender,
            @NotNull(message = "User Type is mandatory") String userType,
            @NotNull(message = "Status is mandatory") String status,
            @NotNull(message = "Coordinates is mandatory") Coordinates coordinate, String pincode,
            ArrayList<String> mappedId) {
        super(_rev, _id, "user");
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.emailId = emailId;
        this.password = password;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.userType = userType;
        this.status = status;
        this.coordinate = coordinate;
        this.pincode = pincode;
        this.mappedId = mappedId;
    }

}