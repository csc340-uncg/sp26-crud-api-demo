package com.csc340.crud_api.student;

import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/students")
public class StudentApiController {

  private final StudentService studentService;

  public StudentApiController(StudentService studentService) {
    this.studentService = studentService;
  }

  /**
   * Endpoint to retrieve all students.
   *
   * @return ResponseEntity containing a collection of all students.
   */
  @GetMapping("/")
  public ResponseEntity<Collection<Student>> getAllStudents() {
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  /**
   * Endpoint to retrieve a student by their ID.
   *
   * @param id The ID of the student to retrieve.
   * @return ResponseEntity containing the student if found, or a 404 Not Found
   *         status if not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    Student student = studentService.getStudentById(id);
    if (student != null) {
      return ResponseEntity.ok(student);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to create a new student.
   *
   * @param student The student object to create, provided in the request body.
   * @return ResponseEntity containing the created student if successful, or a 404
   *         Not Found status if creation fails.
   */
  @PostMapping("/")
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    Student createdStudent = studentService.createStudent(student);
    if (createdStudent != null) {
      return ResponseEntity.ok(createdStudent);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to retrieve students by their major.
   *
   * @param major The major to filter students by, provided as a path variable.
   * @return ResponseEntity containing a collection of students with the specified
   *         major.
   */
  @GetMapping("/major/{major}")
  public ResponseEntity<Collection<Student>> getStudentsByMajor(@PathVariable String major) {
    return ResponseEntity.ok(studentService.getStudentsByMajor(major));
  }

  /**
   * Endpoint to retrieve honors students based on a minimum GPA.
   *
   * @param gpa The minimum GPA to filter honors students by, provided as a path
   *            variable.
   * @return ResponseEntity containing a collection of honors students with a GPA
   *         greater than or equal to the specified value.
   */
  @GetMapping("/honors/{gpa}")
  public ResponseEntity<Collection<Student>> getHonorsStudents(@PathVariable double gpa) {
    return ResponseEntity.ok(studentService.getHonorsStudents(gpa));
  }

  /**
   * Endpoint to search for students by name. If the name parameter is provided,
   * it will return students whose names contain the specified value. If the name
   * parameter is not provided, it will return all students.
   *
   * @param name The name to search for, provided as a request parameter. This
   *             parameter is optional.
   * @return ResponseEntity containing a collection of students that match the
   *         search criteria, or all students if no name is provided.
   */
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

  /**
   * Endpoint to retrieve a student by their email address.
   *
   * @param email The email address of the student to retrieve, provided as a
   *              request parameter.
   * @return ResponseEntity containing the student if found, or a 404 Not Found
   *         status if not found.
   */
  @GetMapping("/email/{email}")
  public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
    Student student = studentService.getStudentByEmail(email);
    if (student != null) {
      return ResponseEntity.ok(student);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to update an existing student by their ID.
   *
   * @param id             The ID of the student to update, provided as a path
   *                       variable.
   * @param updatedStudent The updated student object, provided in the request
   *                       body.
   * @return ResponseEntity containing the updated student if successful, or a 404
   *         Not Found status if the student to update is not found.
   */
  @PutMapping("/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
    Student student = studentService.updateStudent(id, updatedStudent);
    if (student != null) {
      return ResponseEntity.ok(student);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Endpoint to delete a student by their ID.
   *
   * @param id The ID of the student to delete, provided as a path variable.
   * @return ResponseEntity with no content if deletion is successful, or a 404
   *         Not Found status if the student to delete is not found.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.noContent().build();
  }

}
