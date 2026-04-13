package com.csc340.crud_api.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.csc340.crud_api.student.Student;
import com.csc340.crud_api.student.StudentRepository;

@Service
public class CustomStudentDetailsService implements UserDetailsService {

  @Autowired
  private StudentRepository repo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Student student = repo.findByEmail(email);

    if (student == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
    ArrayList<SimpleGrantedAuthority> authList = new ArrayList<>();
    authList.add(new SimpleGrantedAuthority(student.getRole()));
    return new org.springframework.security.core.userdetails.User(
        student.getEmail(), student.getPassword(), authList);
  }

}
