package com.gzc.yygh.hosp;

import com.gzc.yygh.hosp.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/04  15:32  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@SpringBootTest
public class AppTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    //分页查询
    @Test
    public void testPage(){
        //前端传过来的信息
        int pageNum = 1;
        int size = 3;
        //设置查询条件
        Query query = new Query(Criteria.where("username").is("管志成"));
        //获取total
        long total = mongoTemplate.count(query, User.class);
        //跳过查询
        List<User> list = mongoTemplate.find(query.skip((pageNum-1)*size).limit(size), User.class);
        //自己手动创造Total和row
        Map<String,Object> map= new HashMap<>();
        map.put("total",total);
        map.put("row",list);
        //返回给前端
        //return map;
    }

    @Test
    public void testQuery(){
        //自动把查询结果封装到pojo中,数据库中比pojo中多余的字段不封装
//        User byId = mongoTemplate.findById("556", User.class);
//        System.out.println(byId);
        //查询所有
//        List<User> all = mongoTemplate.findAll(User.class);
//        for (User user : all) {
//            System.out.println("user = " + user);
//        }
        //带条件的查询
//        Query query = new Query(Criteria.where("age").is(23));
//        List<User> list = mongoTemplate.find(query, User.class);
//        for (User user : list) {
//            System.out.println("user = " + user);
//        }
        //模糊查询://此处的.*就相当于mysql中的%
        String formet = String.format("%s%s%s", ".*", "管志成", ".*");
        Pattern pattern = Pattern.compile(formet,Pattern.CASE_INSENSITIVE);//大小写不敏感
        Query query = new Query(Criteria.where("username").regex(pattern));
        List<User> list = mongoTemplate.find(query, User.class);
        for (User user : list) {
            System.out.println("user = " + user);
        }
    }

    @Test
    public void testUpdata(){
        //upsert:有数据时进行修改,查不到时做添加
//        Criteria criteria = new Criteria("username").is("管志成3");
//        Query query = new Query(criteria);
//        Update update = new Update();
//        update.set("username","管志成100");
//        mongoTemplate.upsert(query,update, User.class);

        //updateFirst:只修改第一个
        //updateMulti:所有的都修改
//        Criteria criteria = new Criteria("gender").is(true);
//        Query query = new Query(criteria);
//        Update update = new Update();
//        update.set("age",23);
//        mongoTemplate.updateMulti(query,update, User.class);
    }

    @Test
    public void testDelete(){
        //and
//        Query query = new Query(Criteria.where("username").is("人类").and("gender").is(true));
//        DeleteResult remove = mongoTemplate.remove(query, User.class);
//        System.out.println("remove = " + remove);
        //or
//        Criteria criteria = new Criteria();
//        criteria.orOperator(Criteria.where("_id").is("1002"),Criteria.where("username").is("管志成1"));
//        Query query = new Query(criteria);
//        DeleteResult remove = mongoTemplate.remove(query,User.class);
//        System.out.println("remove = " + remove);
    }

    //测试新增:
    //1.insert可以用来新增,不能对主键重复的值进行修改
    //2.save不仅可以用来新增还可以用来对主键重复的修改数据,使用save修改时,是覆盖式的修改,要想保证其他数据不变,需要先查再改
    @Test
    public void testInsert(){
        //User user = new User("你好啊人类我不哈哈","2",true,new Date());

        User user1 = new User();
        user1.setPid("1");
        user1.setUsername("管志成1");
        mongoTemplate.save(user1);
    }

    //通过save来实现修改
    @Test
    public void testSaveToUpData(){
        User byId = mongoTemplate.findById("2", User.class);
        byId.setUsername("管志成3");
        mongoTemplate.save(byId);

    }

    //save虽然能进行新增又能修改,但是只能一条一条的进行,insert可以进行批量插入
    @Test
    public void testBatchInsert(){
        List<User> list= new ArrayList<User>();
        list.add(new User("管志成9","55",true,new Date()));
        list.add(new User("管志成10","42",false,new Date()));
        list.add(new User("管志成11","43",true,new Date()));
        list.add(new User("管志成12","44",false,new Date()));
        mongoTemplate.insert(list, User.class);

    }
}
