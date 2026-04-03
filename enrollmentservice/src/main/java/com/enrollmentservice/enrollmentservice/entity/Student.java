package com.enrollmentservice.enrollmentservice.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private int stu_Id;
    private String stu_Name;
    private String stu_email;
    private Long stu_Mobile_no;
    private String stu_DOB;
    private String stu_Address;
}
