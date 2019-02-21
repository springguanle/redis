package com.bjpowernode.service;

import com.bjpowernode.bean.Student;

import java.util.List;

public interface StudentService {

    int selectAllStudentsCount();

    List<Student> selectStudentsByName(String name);

    void insertStudent(Student student);
}
