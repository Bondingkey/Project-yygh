package com.gzc.yygh.hosp.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/04  15:37  周一
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "aaa")//该注解用来表明mongodb要去找那个文档
public class User {

    //@Field(value = "username") //使用该注解使该属性与mongodb中的username对应
    private String username;
    @Id //该字段表示当前属性与mongodb中的_id对应
    private String pid;//id字段必须名叫id,这样才能和mongodb中的_id对应,否则就会变成普通字段
    private boolean gender;
    private Date data;

}
