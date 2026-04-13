package com.csc340.crud_api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.csc340.crud_api.student.Student;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentApiControllerIntegrationTests {

  @LocalServerPort
  private int port;

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  void testCreateStudent() {
    Student student = new Student("Test Student", "test@school.edu", "CSC", 3.0);

    String url = "http://localhost:" + port + "/api/students/";
    var response = restTemplate.postForEntity(url, student, Student.class);
    assert (response.getStatusCode().is2xxSuccessful());
    assert (response.getBody() != null);
    assert (response.getBody().getName().equals("Test Student"));
  }

  @Test
  void testGetAllStudents() {
    var response = restTemplate.getForEntity("http://localhost:" + port + "/api/students/", Student[].class);
    assert (response.getStatusCode().is2xxSuccessful());

  }
}
