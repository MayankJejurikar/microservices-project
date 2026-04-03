package com.studentservice.studentservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentRequestDto {

    @NotEmpty
    @Size(min = 4, message = "Please enter your Name with minimum 4 character!")
    private String stu_Name;

    @Email(message = "Please enter valid Email Address!!")
    private String stu_email;

    @Pattern(regexp = "^[0-9]{10}$", message = "You entered Invalid Mobile Number!!.Please enter 10 digit Mobile number!")
    private String stu_Mobile_no;

    @NotEmpty(message = "Please enter your valid DOB in DD/MM/YYYY!!!")
    private String stu_DOB;

    @NotEmpty(message = "Please enter your Address!!!")
    private String stu_Address;
}
