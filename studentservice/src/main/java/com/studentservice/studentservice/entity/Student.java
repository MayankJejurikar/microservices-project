package com.studentservice.studentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "stu_id")
    private int stuId;

    @Column(name = "stu_Name")
    private String stuName;

    @Column(name = "stu_email")
    private String stuEmail;

    @Column(name = "stu_Mobile_no")
    private String stuMobileNo;

    @Column(name = "stu_DOB")
    private String stuDOB;

    @Column(name = "stu_Address")
    private String stuAddress;
}