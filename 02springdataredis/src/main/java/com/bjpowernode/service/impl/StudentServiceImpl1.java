package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Student;
import com.bjpowernode.dao.StudentDao;
import com.bjpowernode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  不使用注解的方式
 */
//@Service("studentServiceImpl1")
public class StudentServiceImpl1 implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public int selectAllStudentsCount() {
        //给key命名
        String key = "selectAllStudentsCount";

        //获取redis操作对象
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();

        //根据key去缓存中查询
        Integer count = (Integer)ops.get(key);

        //如果缓存中的数据是null，则再去数据库中查询
        if (count == null){
            System.out.println("selectAllStudentsCount从数据库中查询");
            count = studentDao.selectAllStudentsCount();
            //将查询结果放入到redis中,设置有效时长为20秒
            ops.set(key,count,20, TimeUnit.SECONDS);
        }

        return count;
    }

    @Override
    public List<Student> selectStudentsByName(String name) {
        String key = "selectStudentsByName_" + name;
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps(key);
        //从缓存中查询数据
        List<Student> students = (List<Student>)ops.get();

        //如果students是null，则再从数据库中查询，再将结果放入到redis中
        if (students == null) {
            System.out.println("selectStudentsByName从数据库中查询");
            //从数据库中查询
            students = studentDao.selectStudentsByName(name);

            ops.set(students);
        }

        return students;
    }

    @Override
    public void insertStudent(Student student) {
        //要清空先关缓存
        Set<String> keys = redisTemplate.keys("selectStudentsByName_*");

        //从redis中清空相关的key
        redisTemplate.delete(keys);

        studentDao.insertStudent(student);
    }
}
