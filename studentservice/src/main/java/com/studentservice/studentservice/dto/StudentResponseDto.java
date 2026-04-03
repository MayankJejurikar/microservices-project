package com.studentservice.studentservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentResponseDto {

    private int stu_Id;
    private String stu_Name;
    private String stu_email;
    private String stu_Mobile_no;
    private String stu_DOB;
    private String stu_Address;
}
