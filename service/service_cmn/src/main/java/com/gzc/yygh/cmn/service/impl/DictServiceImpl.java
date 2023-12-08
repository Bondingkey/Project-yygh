package com.gzc.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.cmn.Listner.DictListner;
import com.gzc.yygh.cmn.mapper.DictMapper;
import com.gzc.yygh.cmn.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzc.yygh.model.cmn.Dict;
import com.gzc.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author gzc
 * @since 2023-12-02
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void download(HttpServletResponse response) throws IOException {
        List<Dict> list = baseMapper.selectList(null);
        List<DictEeVo> dictEeVoList = new ArrayList<>(list.size());
        for (Dict dict : list) {
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoList.add(dictEeVo);
        }
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("字典文件", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), DictEeVo.class)
                .sheet("字典列表")
                .doWrite(dictEeVoList);
    }

    @Override
    @CacheEvict(value = "abc",allEntries = true) //该注解会清空redis缓存,同时包含abc文件夹及其子文件
    public void upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DictEeVo.class,new DictListner(baseMapper))
                .sheet(0)
                .doRead();
    }

    public boolean isExistChild(Long pid){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<Dict>();
        dictQueryWrapper.eq("parent_id",pid);
        Integer countChild = baseMapper.selectCount(dictQueryWrapper);
        return countChild>0;
    }

    @Override
    @Cacheable(value = "abc") //在查询方法中使用,表示先去缓存中查,缓存中没有再去数据库中查,value表示缓存中的key
    public List<Dict> getChildListByPid(Long pid) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<Dict>();
        dictQueryWrapper.eq("parent_id",pid);
        List<Dict> list = baseMapper.selectList(dictQueryWrapper);
        for (Dict dict : list) {
            dict.setHasChildren(isExistChild(dict.getId()));
        }
        return list;
    }

    @Override
    public String getNameByValue(Long value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        queryWrapper.eq("value",value);
        Dict dict = baseMapper.selectOne(queryWrapper);
        if (dict!=null){
            return dict.getName();
        }
        return null;
    }

    @Override
    public String getNameByValueAndCode(String code, Long value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>();
        queryWrapper.eq("dict_code",code);
        Dict dict = baseMapper.selectOne(queryWrapper);

        QueryWrapper<Dict> queryWrapper1 = new QueryWrapper<Dict>();
        queryWrapper1.eq("parent_id",dict.getId());
        queryWrapper1.eq("value",value);
        Dict dict1 = baseMapper.selectOne(queryWrapper1);

        return dict1.getName();
    }
}
