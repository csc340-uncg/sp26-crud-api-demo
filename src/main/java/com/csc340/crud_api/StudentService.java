package com.csc340.crud_api;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student createStudent(Student student) {
    return studentRepository.save(student);
  }

  public Student getStudentById(Long id) {
    return studentRepository.findById(id).orElse(null);
  }

  public Student updateStudent(Long id, Student updatedStudent) {
    return studentRepository.findById(id)
        .map(student -> {
          student.setName(updatedStudent.getName());
          student.setEmail(updatedStudent.getEmail());
          student.setMajor(updatedStudent.getMajor());
          student.setGpa(updatedStudent.getGpa());
          return studentRepository.save(student);
        })
        .orElse(null);
  }

  public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
  }

  public List<Student> getStudentsByMajor(String major) {
    return studentRepository.findByMajor(major);
  }

  public List<Student> getHonorsStudents(double gpa) {
    return studentRepository.findHonorsStudents(gpa);
  }

  public List<Student> searchStudentsByName(String name) {
    return studentRepository.findByName(name);
  }

  public Student getStudentByEmail(String email) {
    return studentRepository.findByEmail(email);
  }

}