package com.gzc.yygh.cmn.EasyExcelDemo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/02  16:03  周六
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class Student {

    @ExcelProperty("学生姓名")
    private  String name;
    @ExcelProperty("学生年龄")
    private  int age;
    @ExcelProperty("学生性别")
    private  String sex;
    @ColumnWidth(50)
    @ExcelProperty("学生电话")
    private  Long phone;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", phone=" + phone +
                '}';
    }

    public Student(String name, int age, String sex, Long phone) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phone = phone;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
