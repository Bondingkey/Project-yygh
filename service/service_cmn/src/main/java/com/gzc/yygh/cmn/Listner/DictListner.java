package com.gzc.yygh.cmn.Listner;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gzc.yygh.cmn.mapper.DictMapper;
import com.gzc.yygh.model.cmn.Dict;
import com.gzc.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @author: 拿破仑
 * @Date&Time: 2023/12/03  10:32  周日
 * @Project: yygh_parent
 * @Write software: IntelliJ IDEA
 * @Purpose: 在此处编辑
 */
public class DictListner extends AnalysisEventListener<DictEeVo> {

    private DictMapper DictMapper;

    public DictListner(DictMapper DictMapper){
        this.DictMapper = DictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        //插入前判断
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<Dict>();
        dictQueryWrapper.eq("id",dictEeVo.getId());
        Integer integer = this.DictMapper.selectCount(dictQueryWrapper);
        if (integer>0){
            this.DictMapper.updateById(dict);
        }else {
            this.DictMapper.insert(dict);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
