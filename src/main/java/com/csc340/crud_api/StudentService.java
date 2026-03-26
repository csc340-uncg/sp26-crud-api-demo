package com.csc340.crud_api;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  private static final String UPLOAD_DIR = "src/main/resources/static/profile-pictures/";

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

  public void saveProfilePicture(Student student, MultipartFile profilePicture) {
    if (profilePicture == null || profilePicture.isEmpty()) {
      return;// No picture uploaded, skip saving
    }
    String originalFileName = profilePicture.getOriginalFilename();
    try {
      if (originalFileName != null && originalFileName.contains(".")) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String fileName = String.valueOf(student.getStudentId()) + "." + fileExtension;
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        InputStream inputStream = profilePicture.getInputStream();

        Files.createDirectories(Paths.get(UPLOAD_DIR));// Ensure directory exists
        Files.copy(inputStream, filePath,
            StandardCopyOption.REPLACE_EXISTING);// Save picture file
        student.setProfilePicturePath(fileName);
        studentRepository.save(student);// Update student with picture path
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}