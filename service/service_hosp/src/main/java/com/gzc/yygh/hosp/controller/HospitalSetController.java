package com.gzc.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzc.yygh.common.result.R;
import com.gzc.yygh.hosp.service.HospitalSetService;
import com.gzc.yygh.common.utils.MD5;
import com.gzc.yygh.model.hosp.HospitalSet;
import com.gzc.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author gzc
 * @since 2023-11-27
 */
@RestController //添加了该属性代表该类中的所有方法返回的都是json
@Api(tags = "医院设置信息微服务") //当前微服务在Swagger中的说明信息
@RequestMapping("/admin/hosp/hospitalSet")
@Slf4j        //标注日志级别
@CrossOrigin  //局部解决跨域问题
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("解锁与上锁")
    @PutMapping("/status/{id}/{status}")
    public R lockSet(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        HospitalSet hospitalSet = new HospitalSet();
        hospitalSet.setId(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return  R.ok();
    }

    @ApiOperation("多选删除")
    @DeleteMapping("/deleteList")
    public R deleteList(@RequestBody List<Integer> ids){
        hospitalSetService.removeByIds(ids);
        return R.ok();
    }

    @ApiOperation("修改时的数据回显")
    @GetMapping("/toUpdatePage/{id}")
    public R toUpdatePage(@PathVariable("id") Integer id){
        HospitalSet byId = hospitalSetService.getById(id);
        return R.ok().data("items",byId);
    }

    @PutMapping("/update")
    @ApiOperation("修改医院设置")
    public R update(@RequestBody HospitalSet hospitalSet){
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation("新增医院设置")
    @PostMapping("/addHosp")//新增使用POST请求
    public R save(@RequestBody HospitalSet hospitalSet){
        //设置默认状态,0:可用;1不可用
        hospitalSet.setStatus(0);
        //设置指定密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(String.valueOf(System.currentTimeMillis()+ random.nextInt(1000))));
        hospitalSetService.save(hospitalSet);
        return R.ok();
    }

    @ApiOperation("分页查询的接口")
    @PostMapping("/findPage/{pageNum}/{size}")//接受前端传过来的分页查询信息,如果使用了@RequestBody则不能使用Get请求
    public R findPage(@ApiParam(name = "pageNum",value = "当前页") @PathVariable("pageNum") Integer pageNum,
                      @ApiParam(name = "size",value = "条数") @PathVariable("size") Integer size,
                      @RequestBody HospitalSetQueryVo hospitalSetQueryVo)//由于前端传来的是json数据用@RequestBody,VO对象用于前端和Controller层交互
    {
        //设置分页信息
        //此时使用HospitalSet,因为此时要进行的是数据库的搜索
        Page<HospitalSet> hospitalSetPage = new Page<HospitalSet>(pageNum,size);

        //设置要进行的条件查询的条件
        QueryWrapper<HospitalSet> objectQueryWrapper = new QueryWrapper<HospitalSet>();

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())){
            objectQueryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())){
            objectQueryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }

        //获得分页信息对象
        hospitalSetService.page(hospitalSetPage,objectQueryWrapper);

        return R.ok().data("total",hospitalSetPage.getTotal()).data("rows",hospitalSetPage.getRecords());
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有的医院设置信息")//在Swagger中对微服务中的接口做说明
    public R findAllHospital(){
        log.info("thread = "+Thread.currentThread().getName());//标明一个info的日志,改日志会被写到文件中
        //int i= 10/0;
        List<HospitalSet> list = hospitalSetService.list();
        log.info("result ="+list.toString());
        return R.ok().data("items",list);
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "删除医院信息")
    //可以使用@RequestParam注解方式接收入参,要求前端传来的参数和方法中的入参名字一致,不一致时可以使用value属性指定,使用时需要删除路径占位符中的变量
    public R deleteHospital(@ApiParam("要进行删除的id") @PathVariable("id") Long id){
        hospitalSetService.removeById(id);
        return R.ok();
    }
}
