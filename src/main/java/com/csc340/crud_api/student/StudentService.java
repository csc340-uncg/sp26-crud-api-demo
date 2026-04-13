package com.csc340.crud_api.student;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${upload.dir}")
  private String UPLOAD_DIR;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student createStudent(Student student) {
    student.setPassword(passwordEncoder.encode(student.getPassword()));
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
          student.setRole(updatedStudent.getRole());
          if (updatedStudent.getPassword() != null && !updatedStudent.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(updatedStudent.getPassword()));
          }
          return studentRepository.save(student);
        })
        .orElse(null);
  }

  public void deleteStudent(Long id) {
    Student student = studentRepository.findById(id).orElse(null);
    if (student != null) {
      deleteProfilePicture(student.getProfilePicturePath());
    }
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

  public void saveProfilePicture(Student student, MultipartFile profilePicture) {
    if (profilePicture == null || profilePicture.isEmpty())
      return;

    try {
      // Path: /app/uploads/profile-pictures/
      Path rootLocation = Paths.get(UPLOAD_DIR).resolve("profile-pictures");
      Files.createDirectories(rootLocation);

      String extension = getFileExtension(profilePicture.getOriginalFilename());
      String fileName = student.getStudentId() + "." + extension;

      // Save file: /app/uploads/profile-pictures/101.jpg
      try (InputStream is = profilePicture.getInputStream()) {
        Files.copy(is, rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
      }

      // Store only "101.jpg" in the DB
      student.setProfilePicturePath(fileName);
      studentRepository.save(student);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  public void deleteProfilePicture(String fileName) {
    if (fileName == null || fileName.isEmpty() || fileName.equals("avatar.png")) {
      return; // Don't try to delete the default avatar or empty paths
    }

    try {
      // Path: /app/uploads/profile-pictures/5.jpg
      Path filePath = Paths.get(UPLOAD_DIR)
          .resolve("profile-pictures")
          .resolve(fileName);

      // Delete the file only if it exists
      boolean deleted = Files.deleteIfExists(filePath);

      if (deleted) {
        System.out.println("Successfully deleted: " + fileName);
      }
    } catch (IOException e) {
      System.err.println("Could not delete file: " + fileName + ". Error: " + e.getMessage());
    }
  }

}