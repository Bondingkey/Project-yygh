package com.gzc.yygh.cmn.EasyExcelDemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/02  15:56  周六
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class Write {

    public static void main(String[] args) {
//方式一:往单个sheet中写数据
        List list = new ArrayList<>();
        list.add(new Student("管志成",23,"男",158663913655L));
        list.add(new Student("管志成1",231,"男",1563943655L));
        list.add(new Student("管志成2",232,"女",158639218655L));
        list.add(new Student("管志成3",233,"男",15863913655L));

        List list1 = new ArrayList<>();
        list1.add(new Student("管志成",23,"男",158663913655L));
        list1.add(new Student("管志成1",231,"男",1563943655L));

//        EasyExcel.write("D:\\系统文件夹\\桌面\\helloEasyExcel.xlsx", Student.class)
//                .sheet("学生列表")
//                .doWrite(list);

//方式二:往多个sheet中写入数据
        ExcelWriter build = EasyExcel.write("D:\\系统文件夹\\桌面\\aaa.xlsx", Student.class).build();
        //定义两个sheet
        WriteSheet sheet1 = EasyExcel.writerSheet(0,"学生列表1").build();
        WriteSheet sheet2 = EasyExcel.writerSheet(1,"学生列表2").build();
        //往两个sheet中写入
        build.write(list,sheet1);
        build.write(list1,sheet2);

        //关闭资源
        build.finish();
    }
}
