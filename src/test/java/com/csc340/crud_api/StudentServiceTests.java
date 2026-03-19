package com.csc340.crud_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

  @Mock
  private StudentRepository studentRepository;

  @InjectMocks
  private StudentService studentService;

  private Student student1;
  private Student student2;
  private Student student3;

  @BeforeEach
  public void setUp() {
    student1 = new Student("Alice Smith", "alice@school.edu", "CSC", 3.8);
    student2 = new Student("Bob Johnson", "bob@school.edu", "MAT", 3.5);
    student3 = new Student("Charlie Brown", "charlie@school.edu", "CSC", 3.2);
  }

  @Test
  public void testCreateStudent() {
    given(studentRepository.save(student1)).willReturn(student1);
    Student created = studentService.createStudent(student1);
    assertNotNull(created);
    assertEquals("Alice Smith", created.getName());
  }

  @Test
  public void testDeleteStudent() {
    willDoNothing().given(studentRepository).deleteById(1L);
    studentService.deleteStudent(1L);
    verify(studentRepository).deleteById(1L);
  }

  @Test
  public void testGetAllStudents() {
    given(studentRepository.findAll()).willReturn(java.util.List.of(student1, student2, student3));
    var students = studentService.getAllStudents();
    assertEquals(3, students.size());
  }

  @Test
  public void testGetHonorsStudents() {
    given(studentRepository.findHonorsStudents(3.5)).willReturn(java.util.List.of(student1, student2));
    var honorsStudents = studentService.getHonorsStudents(3.5);
    assertEquals(2, honorsStudents.size());

  }

  @Test
  public void testGetStudentByEmail() {
    given(studentRepository.findByEmail("bob@school.edu")).willReturn(student2);
    Student student = studentService.getStudentByEmail("bob@school.edu");
    assertNotNull(student);

  }

  @Test
  public void testGetStudentById() {
    given(studentRepository.findById(1L)).willReturn(java.util.Optional.of(student1));
    Student student = studentService.getStudentById(1L);
    assertNotNull(student);
    assertEquals("Alice Smith", student.getName());

  }

  @Test
  public void testGetStudentsByMajor() {
    given(studentRepository.findByMajor("CSC")).willReturn(java.util.List.of(student1, student3));
    var cscStudents = studentService.getStudentsByMajor("CSC");
    assertEquals(2, cscStudents.size());

  }

  @Test
  public void testSearchStudentsByName() {
    given(studentRepository.findByName("Smith")).willReturn(java.util.List.of(student1));
    var students = studentService.searchStudentsByName("Smith");
    assertEquals(1, students.size());
    assertEquals("Alice Smith", students.get(0).getName());

  }

  @Test
  public void testUpdateStudent() {
    given(studentRepository.findById(1L)).willReturn(java.util.Optional.of(student1));
    given(studentRepository.save(student1)).willReturn(student1);
    Student updated = studentService.updateStudent(1L, student1);
    assertNotNull(updated);
    assertEquals("Alice Smith", updated.getName());

  }
}
