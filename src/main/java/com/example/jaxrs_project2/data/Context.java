package com.example.jaxrs_project2.data;


import com.example.jaxrs_project2.entities.Student;

import java.util.ArrayList;
import java.util.List;

public class Context {
    public static List<Student> students;
    public static List<String> blackList;

    static {
        students = new ArrayList<>();
        blackList = new ArrayList<>();
    }
}
