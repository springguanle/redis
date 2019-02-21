package com.bjpowernode.test;

import com.bjpowernode.bean.Student;
import com.bjpowernode.service.StudentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSpringDataRedis {

    private StudentService studentService;

    @Before
    public void before() {
        String[] conf = {"spring-mybatis.xml","spring-tx.xml","spring-redis.xml"};

        ApplicationContext context = new ClassPathXmlApplicationContext(conf);
        studentService = (StudentService)context.getBean("studentServiceImpl4");
    }

    @Test
    public void test01() {
        studentService.insertStudent(new Student("赵学有",16,98.8));
    }

    @Test
    public void test02() {
        int count = studentService.selectAllStudentsCount();
        System.out.println(count);
    }

    @Test
    public void test03(){
        List<Student> list = studentService.selectStudentsByName("学");
        list.forEach((student -> {
            System.out.println(student);
        }));
    }

    /*
        模拟高并发的场景
     */
    @Test
    public void test04() throws InterruptedException {
        //创建线程池，提供20个线程
        ExecutorService es = Executors.newFixedThreadPool(20);

        //模拟1000个并发
        for (int i = 0; i < 1000; i++) {
            es.submit(()->{
                studentService.selectStudentsByName("学");
            });

            Thread.sleep(1);
        }
    }
}
