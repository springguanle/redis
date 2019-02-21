package com.bjpowernode.test;


import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author zhangguanle
 * @create 2019-02-21 16:00
 */
public class TestJedis {

    private Jedis jedis;

    @Before
    public void before(){
        //对jedis初始化，创建jedis初始化对象，指定redis服务器的ip和端口号
        jedis = new Jedis("192.168.162.132", 6379);

    }

    @Test
    public void test01(){
        System.out.println(jedis.ping());
    }

    @Test
    public void test02(){
        jedis.set("city","上海");
        String city= jedis.get("city");
        System.out.println(city);
    }

}
