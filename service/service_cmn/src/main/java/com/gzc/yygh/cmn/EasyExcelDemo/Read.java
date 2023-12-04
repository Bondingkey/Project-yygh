package com.gzc.yygh.cmn.EasyExcelDemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/03  08:34  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class Read {
    public static void main(String[] args) {

//        //读单个sheet
//        EasyExcel.read("D:\\系统文件夹\\桌面\\aaa.xlsx", Student.class,new StudentListner())
//                .sheet(0)
//                .doRead();

        //读多个sheet
        ExcelReader build = EasyExcel.read("D:\\系统文件夹\\桌面\\aaa.xlsx").build();
        //定义多个sheet
        ReadSheet shheet1 = EasyExcel.readSheet(0).head(Student.class).registerReadListener(new StudentListner()).build();
        ReadSheet shheet2 = EasyExcel.readSheet(1).head(Student.class).registerReadListener(new StudentListner()).build();

        //读取
        build.read(shheet1,shheet2);

        //关闭资源
        build.finish();

    }
}
