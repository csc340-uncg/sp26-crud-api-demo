package com.csc340.crud_api.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long studentId;

  @Column(nullable = false)
  public String name;

  @Column(nullable = false, unique = true)
  private String email;

  private String major;
  private double gpa;
  private String profilePicturePath;
  @Column(nullable = false)
  private String password;
  private String role;

  public Student(String name, String email, String major, double gpa) {
    this.name = name;
    this.email = email;
    this.major = major;
    this.gpa = gpa;
  }

}