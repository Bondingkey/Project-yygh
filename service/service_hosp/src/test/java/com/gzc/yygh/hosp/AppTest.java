package com.gzc.yygh.hosp;

import com.gzc.yygh.hosp.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //测试新增:
    //1.insert可以用来新增,不能对主键重复的值进行修改
    //2.save不仅可以用来新增还可以用来对主键重复的修改数据,使用save修改时,是覆盖式的修改,要想保证其他数据不变,需要先查再改
    @Test
    public void testInsert(){
        //User user = new User("你好啊人类我不哈哈","2",true,new Date());

        User user1 = new User();
        user1.setPid("1");
        user1.setName("管志成1");
        mongoTemplate.save(user1);
    }

    //通过save来实现修改
    @Test
    public void testSaveToUpData(){
        User byId = mongoTemplate.findById("2", User.class);
        byId.setName("管志成3");
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
