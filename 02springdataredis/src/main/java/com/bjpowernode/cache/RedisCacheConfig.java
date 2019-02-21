package com.bjpowernode.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
   自定义key的生成器
 */
public class RedisCacheConfig extends CachingConfigurerSupport{
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                //编写key的生成规则
                //获取注解所标注的类的类名
                String className = target.getClass().getName();
                //获取注解所标注的方法的方法名
                String methodName = method.getName();
                //key的生成规则：类名_方法名_参数
                return className + "_" + methodName + "_" + params;
            }
        };
    }
}
