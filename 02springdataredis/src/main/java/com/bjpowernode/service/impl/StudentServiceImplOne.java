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
 * @author zhangguanle
 * @create 2019-02-21 16:55
 */
@Service("studentServiceImplOne")
public class StudentServiceImplOne implements StudentService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StudentDao studentDao;

    @Override
    public int selectAllStudentsCount() {
        //定义一个key,保证唯一性，给key命名
        String key="selectAllStudentsCount";
        ValueOperations<String,Object> ops = redisTemplate.opsForValue();
        //根据key去缓存中拿数据
        Integer count= (Integer) ops.get(key);
        if(count==null){
            System.out.println("selectAllStudentsCount 从数据库中查询");
            count=studentDao.selectAllStudentsCount();
            //将查询结果放入到缓冲中,并设置有效时长为20s
            ops.set(key,count,20, TimeUnit.SECONDS);
        }
        return count;
    }

    @Override
    public List<Student> selectStudentsByName(String name) {
        String key="selectStudentsByName_"+name;
        BoundValueOperations<String,Object> bovs = redisTemplate.boundValueOps(key);
        List<Student> student = (List<Student>) bovs.get();
        if(student==null){
            System.out.println("selectStudentsByName 从数据库中查询");
            student=studentDao.selectStudentsByName(name);
            bovs.set(student);

        }
        return student;
    }

    @Override
    public void insertStudent(Student student) {
        //插入前将相关的缓冲清空
        Set<String> keys=redisTemplate.keys("selectStudentsByName_*");
        redisTemplate.delete(keys);
        studentDao.insertStudent(student);
    }
}
