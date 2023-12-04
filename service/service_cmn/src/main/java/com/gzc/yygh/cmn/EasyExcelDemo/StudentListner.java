package com.gzc.yygh.cmn.EasyExcelDemo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.Map;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/03  08:37  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class StudentListner extends AnalysisEventListener<Student> {


    //每读一行就封装一次进student中
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println(student);
    }

    //解析标题
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }

    //当excel解析完毕后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("我完事了");
    }
}
