package com.csc340.crud_api;

import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/students")
public class StudentApiController {

  private final StudentService studentService;

  public StudentApiController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/")
  public ResponseEntity<Collection<Student>> getAllStudents() {
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    Student student = studentService.getStudentById(id);
    if (student != null) {
      return ResponseEntity.ok(student);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/")
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    System.out.println("Received student: " + student);
    Student createdStudent = studentService.createStudent(student);
    if (createdStudent != null) {
      return ResponseEntity.ok(createdStudent);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/major/{major}")
  public ResponseEntity<Collection<Student>> getStudentsByMajor(@PathVariable String major) {
    return ResponseEntity.ok(studentService.getStudentsByMajor(major));
  }

  @GetMapping("/honors/{gpa}")
  public ResponseEntity<Collection<Student>> getHonorsStudents(@PathVariable double gpa) {
    return ResponseEntity.ok(studentService.getHonorsStudents(gpa));
  }

  @GetMapping("/search")
  public ResponseEntity<Collection<Student>> searchStudentsByName(@RequestParam(required = false) String name) {
    List<Student> students;
    if (name != null) {
      students = studentService.searchStudentsByName(name);
    } else {
      students = studentService.getAllStudents();
    }
    return ResponseEntity.ok(students);
  }

}
