package com.csc340.crud_api;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
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

  public Student() {
  }

  public Student(String name, String email, String major, double gpa) {
    this.name = name;
    this.email = email;
    this.major = major;
    this.gpa = gpa;
  }

  public Student(Long studentId, String name, String email, String major, double gpa) {
    this.studentId = studentId;
    this.name = name;
    this.email = email;
    this.major = major;
    this.gpa = gpa;
  }

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public double getGpa() {
    return gpa;
  }

  public void setGpa(double gpa) {
    this.gpa = gpa;
  }

}
