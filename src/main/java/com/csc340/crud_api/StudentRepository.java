package com.csc340.crud_api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findByMajor(String major);

  @Query(value = "SELECT s.* FROM students s WHERE s.gpa >= ?1", nativeQuery = true)
  List<Student> findHonorsStudents(double gpa);

  @Query(value = "SELECT s.* FROM students s WHERE s.name like %?1%", nativeQuery = true)
  List<Student> findByName(String name);

  Student findByEmail(String email);

}
