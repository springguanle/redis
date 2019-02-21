package com.bjpowernode.dao;

import com.bjpowernode.bean.Student;

import java.util.List;

public interface StudentDao {

    int selectAllStudentsCount();

    List<Student> selectStudentsByName(String name);

    void insertStudent(Student student);
}