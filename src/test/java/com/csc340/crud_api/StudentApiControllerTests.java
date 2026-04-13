package com.csc340.crud_api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.csc340.crud_api.student.Student;
import com.csc340.crud_api.student.StudentApiController;
import com.csc340.crud_api.student.StudentService;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StudentApiController.class)
public class StudentApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper; // Helper for converting objects to JSON

    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    public void setUp() {
        student1 = new Student(1L, "Alice Smith", "alice@school.edu", "CSC", 3.8, "/avatar.png", "test", "test");
        student2 = new Student("Bob Johnson", "bob@school.edu", "MAT", 3.5);
        student3 = new Student("Charlie Brown", "charlie@school.edu", "CSC", 3.2);
    }

    @Test
    void testCreateStudent() throws Exception {
        when(studentService.createStudent(Mockito.any(Student.class)))
                .thenReturn(student1);
        mockMvc.perform(post("/api/students/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Smith"));

    }

    @Test
    void testDeleteStudent() throws Exception {
        Mockito.doNothing().when(studentService).deleteStudent(1L);
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/api/students/1"))
                .andExpect(status().isNoContent());

    }

    @Test
    void testGetAllStudents() throws Exception {
        given(studentService.getAllStudents())
                .willReturn(java.util.List.of(student1, student2, student3));
        mockMvc.perform(get("/api/students/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("Alice Smith"))
                .andExpect(jsonPath("$[1].name").value("Bob Johnson"))
                .andExpect(jsonPath("$[2].name").value("Charlie Brown"));

    }

    @Test
    void testGetHonorsStudents() throws Exception {
        given(studentService.getHonorsStudents(3.5))
                .willReturn(java.util.List.of(student1, student2));
        mockMvc.perform(get("/api/students/honors/3.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice Smith"))
                .andExpect(jsonPath("$[1].name").value("Bob Johnson"));

    }

    @Test
    void testGetStudentByEmail() throws Exception {
        given(studentService.getStudentByEmail("bob@school.edu"))
                .willReturn(student2);
        // We would need to perform a GET request to the appropriate endpoint and check
        // the response
        mockMvc.perform(get("/api/students/email/bob@school.edu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bob Johnson"));

    }

    @Test
    void testGetStudentById() throws Exception {
        given(studentService.getStudentById(1L))
                .willReturn(student1);
        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Smith"));

    }

    @Test
    void testGetStudentsByMajor() throws Exception {
        given(studentService.getStudentsByMajor("CSC"))
                .willReturn(java.util.List.of(student1, student3));
        mockMvc.perform(get("/api/students/major/CSC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice Smith"))
                .andExpect(jsonPath("$[1].name").value("Charlie Brown"));

    }

    @Test
    void testSearchStudentsByName() throws Exception {
        given(studentService.searchStudentsByName("Smith"))
                .willReturn(java.util.List.of(student1));
        mockMvc.perform(get("/api/students/search?name=Smith"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Alice Smith"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        when(studentService.updateStudent(Mockito.eq(1L), Mockito.any(Student.class)))
                .thenReturn(student1);
        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Smith"));
    }
}
