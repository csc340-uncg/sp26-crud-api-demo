package com.csc340.crud_api.student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

//@RestController
@Controller
@RequestMapping("/students")
public class StudentUiController {

  private final StudentService studentService;

  public StudentUiController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping({ "/", "" })
  public String getAllStudents(Model model) {
    // rreturn ResponseEntity.ok(studentService.getAllStudents());
    model.addAttribute("studentsList", studentService.getAllStudents());
    model.addAttribute("title", "All Students");
    return "students-list";// view name
  }

  @GetMapping("/{id}")
  public String getStudentById(@PathVariable Long id, Model model) {
    Student student = studentService.getStudentById(id);
    if (student != null) {
      model.addAttribute("student", student);
      model.addAttribute("title", "Student Details");
    } else {
      model.addAttribute("errorMessage", "Student not found");
      model.addAttribute("title", "Error");
      return "error";
    }
    return "student-details";
  }

  @GetMapping("/image/{id}")
  @ResponseBody
  public ResponseEntity<byte[]> streamImage(@PathVariable Long id) {
    Student student = studentService.getStudentById(id);
    if (student == null || student.getProfilePicture() == null) {
      // return a default image if student or picture is missing
      try {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
            .body(Files.readAllBytes(Paths.get("src/main/resources/static/profile-pictures/avatar.png")));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // Return the bytes with the correct Content-Type header
    return ResponseEntity.ok()
        .contentType(MediaType.IMAGE_JPEG) // Or fetch type dynamically from DB
        .body(student.getProfilePicture());
  }

  @GetMapping("/major/{major}")
  public String getStudentsByMajor(@PathVariable String major, Model model) {
    model.addAttribute("studentsList", studentService.getStudentsByMajor(major));
    model.addAttribute("title", "Students with Major: " + major);
    return "students-list";
  }

  @GetMapping("/search")
  public String searchStudentsByName(@RequestParam String name, Model model) {
    model.addAttribute("studentsList", studentService.searchStudentsByName(name));
    model.addAttribute("title", "Search Results for: " + name);
    return "students-list";
  }

  @GetMapping("/delete/{id}")
  public String deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return "redirect:/students/";
  }

  @GetMapping("/add")
  public String showAddStudentForm(Model model) {
    model.addAttribute("student", new Student());
    model.addAttribute("title", "Add New Student");
    return "student-form";
  }

  @PostMapping("/")
  public String addStudent(Student student, MultipartFile picture) throws IOException {
    if (!picture.isEmpty()) {
      student.setProfilePicture(picture.getBytes());
    }
    Student newStudent = studentService.createStudent(student);
    if (newStudent != null) {
      return "redirect:/students/" + newStudent.getStudentId();
    } else {
      return "redirect:/students/add?error=true";
    }
  }

  @PostMapping("/update/{id}")
  public String updateStudent(@PathVariable Long id, Student updatedStudent, MultipartFile picture) throws IOException {
    Student student = studentService.updateStudent(id, updatedStudent);
    if (!picture.isEmpty()) {
      student.setProfilePicture(picture.getBytes());
    }
    if (student != null) {
      return "redirect:/students/" + student.getStudentId() + "?success=true";
    } else {
      return "redirect:/students/update/" + id + "?error=true";
    }
  }

}
