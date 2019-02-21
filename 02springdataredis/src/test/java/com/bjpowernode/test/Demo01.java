package com.bjpowernode.test;

import com.bjpowernode.bean.Student;
import com.bjpowernode.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author zhangguanle
 * @create 2019-02-21 16:57
 */
public class Demo01 {

    private StudentService studentService;
    @Before
    public void before(){
        String [] conf={"spring-mybatis.xml","spring-tx.xml","spring-redis.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(conf);
        studentService=(StudentService) context.getBean("studentServiceImplOne");
    }

    @Test
    public void test01(){
        studentService.insertStudent(new Student("lisisi",16,98.8));
    }

    @Test
    public void test02(){
        int count = studentService.selectAllStudentsCount();
        System.out.println(count);
    }

    @Test
    public void test03(){
        List<Student> list= studentService.selectStudentsByName("l");
        list.forEach((student->{
            System.out.println(student);
        }));
    }

}
