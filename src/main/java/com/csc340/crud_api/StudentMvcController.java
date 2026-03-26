package com.csc340.crud_api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RestController
@Controller
@RequestMapping("/students")
public class StudentMvcController {

  private final StudentService studentService;

  public StudentMvcController(StudentService studentService) {
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
    }
    return "student-details";
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

}
