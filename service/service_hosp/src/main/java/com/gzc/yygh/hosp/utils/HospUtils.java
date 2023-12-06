package com.gzc.yygh.hosp.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/06  09:59  周三
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class HospUtils {
    public static Map<String, Object> switchMap(Map<String, String[]> parameterMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue()[0];
            map.put(key,value);
        }
        return map;
    }
}
