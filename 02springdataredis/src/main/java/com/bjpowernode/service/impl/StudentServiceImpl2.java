package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Student;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  使用注解的方式
 */
//@Service("studentServiceImpl2")
public class StudentServiceImpl2 implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //失效时间是10秒
    @Cacheable(value = "unRealTimeCache",key = "'selectAllStudentsCount'")
    @Override
    public int selectAllStudentsCount() {
        System.out.println("selectAllStudentsCount从数据库中查询");
        return studentDao.selectAllStudentsCount();
    }

    @Cacheable(value = "realTimeCache",key = "'selectStudentsByName_' + #name")
    @Override
    public List<Student> selectStudentsByName(String name) {
        System.out.println("selectStudentsByName从数据库中查询");
        return studentDao.selectStudentsByName(name);
    }

    @CacheEvict(value = "realTimeCache",allEntries = true)
    @Override
    public void insertStudent(Student student) {
        System.out.println("插入数据");
        studentDao.insertStudent(student);
    }
}
