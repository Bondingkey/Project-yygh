package com.gzc.yygh.hosp;

import com.gzc.yygh.hosp.pojo.User;
import com.gzc.yygh.hosp.repostitry.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/05  10:06  周二
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@SpringBootTest
public class ReportoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testMyfind(){
        List<User> byName = userRepository.findByUsernameLikeAndGender("管志成",false);
        for (User user : byName) {
            System.out.println("user = " + user);
        }
    }

    //测试查询
    @Test
    public void testQuery(){
        //测试根据id查询
//        Optional<User> byId = userRepository.findById("54");
//        User user = byId.get();
//        System.out.println("user = " + user);
        //带条件的查询
        //设置查询条件
//        User user = new User();
//        user.setGender(true);
//        //封装查询条件
//        Example<User> userExample = Example.of(user);
//        //查询
//        List<User> all = userRepository.findAll(userExample);
//        for (User user1 : all) {
//            System.out.println("user1 = " + user1);
//        }
        //模糊查询
//        //创建匹配器，即如何使用查询条件
//        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
//                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
//        User user = new User();
//        user.setUsername("管");
//        Example<User> userExample = Example.of(user, matcher);
//        List<User> userList = userRepository.findAll(userExample);
//        for (User user1 : userList) {
//            System.out.println("user1 = " + user1);
//        }
        //分页查询
        //前端传来的分页信息
        int PageNum = 1;
        int PageSize = 3;

        //分页条件
        User user = new User();
        user.setGender(true);
        Example<User> userExample = Example.of(user);

        //封装分页信息
        Pageable pageable = PageRequest.of(PageNum,PageSize, Sort.by("pid").descending());

        //查询
        Page<User> allpage = userRepository.findAll(userExample, pageable);
        System.out.println("总数"+allpage.getTotalElements());
        System.out.println("总页数"+allpage.getTotalPages());
        System.out.println("当前页列表数据");
        List<User> content = allpage.getContent();
        for (User user1 : content) {
            System.out.println("user1 = " + user1);
        }
    }

    //测试添加
    @Test
    public void testInsert(){
        //userRepository.insert(new User("管志成1","46",true,new Date()));//只能添加不能修改
        //userRepository.save(new User("管志成11","47",true,new Date()));

        //批量新增
        List<User> userList = new ArrayList<>();
        userList.add(new User("管志成1","52",true,new Date()));
        userList.add(new User("管志成1","53",true,new Date()));
        userList.add(new User("管志成1","54",true,new Date()));
        userList.add(new User("管志成1","55",true,new Date()));
        userRepository.saveAll(userList);
    }

    //测试删除
    @Test
    public void testDelete(){
        //userRepository.deleteById("556");
        //userRepository.delete(new User("管志成1","55",true,new Date()));//本质还是根据id删,没有id属性报错
    }

    //测试修改:直接改是覆盖式的修改,最好是先查后改
    @Test
    public void testUpdate(){
        //直接修改:覆盖式修改
        User user = new User();
        user.setPid("54");
        user.setUsername("哈哈哈");
        userRepository.save(user);
    }
}
