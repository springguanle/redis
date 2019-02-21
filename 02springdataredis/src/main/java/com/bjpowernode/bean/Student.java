package com.bjpowernode.bean;

import lombok.Data;

import java.io.Serializable;

/*
    学生
 */
@Data
public class Student implements Serializable{

    private int id;

    private String name;

    private int age;

    private double score;

    public Student() {
    }

    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

}