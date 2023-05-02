package com.demo.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.wms.common.QueryPageParam;
import com.demo.wms.common.Result;
import com.demo.wms.entity.Workstype;
import com.demo.wms.entity.Workstype;
import com.demo.wms.service.WorkstypeService;
import com.demo.wms.service.WorkstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/workstype")
public class WorkstypeController {
    @Autowired
    private WorkstypeService workstypeService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Workstype workstype) {

        return workstypeService.save(workstype)?Result.suc():Result.fail();//判断是否保存保存的话返回suc,否则返回fail
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Workstype workstype){

        return workstypeService.updateById(workstype)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){

        return workstypeService.removeById(id)?Result.suc():Result.fail();
    }

    //查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query ) { //这是通过自用定义的QueryPageParam 的方式进行调用
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));

        Page<Workstype> page=new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        LambdaQueryWrapper<Workstype> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.like(Workstype::getName,name);
        }




//        IPage result= WorkstypeService.pageC(page);//返回模糊查询结果
        IPage result= workstypeService.pageCC(page,lambdaQueryWrapper);//返回模糊查询结果


        return Result.success(result.getRecords(),result.getTotal());
    }
    @GetMapping("/list")
    public Result list() {
        List list = workstypeService.lambdaQuery().list();
        return  Result.suc(list);
    }

}
