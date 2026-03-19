package com.csc340.crud_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class StudentRepositoryTests {
  @Autowired
  private StudentRepository studentRepository;
  Student student1;
  Student student2;
  Student student3;

  @BeforeEach
  void setUp() {
    student1 = new Student("Alice Smith", "alice@school.edu", "CSC", 3.8);
    student2 = new Student("Bob Johnson", "bob@school.edu", "MAT", 3.5);
    student3 = new Student("Charlie Brown", "charlie@school.edu", "CSC", 3.2);
  }

  @Test
  void testFindByEmail() {
    studentRepository.save(student1);
    Student student = studentRepository.findByEmail("alice@school.edu");
    assertNotNull(student);
    assertEquals("Alice Smith", student.getName());
  }

  @Test
  void testFindByMajor() {
    studentRepository.save(student1);
    studentRepository.save(student2);
    studentRepository.save(student3);
    var cscStudents = studentRepository.findByMajor("CSC");
    assertEquals(2, cscStudents.size());
  }

  @Test
  void testFindByName() {
    studentRepository.save(student1);
    studentRepository.save(student2);
    studentRepository.save(student3);
    var students = studentRepository.findByName("Smith");
    assertEquals(1, students.size());
    assertEquals("Alice Smith", students.get(0).getName());
  }

  @Test
  void testFindHonorsStudents() {
    studentRepository.save(student1);
    studentRepository.save(student2);
    studentRepository.save(student3);
    var honorsStudents = studentRepository.findHonorsStudents(3.5);
    assertEquals(2, honorsStudents.size());
  }
}
