package com.demo.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.wms.common.QueryPageParam;
import com.demo.wms.common.Result;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Works;
import com.demo.wms.service.CourseService;
import com.demo.wms.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/works")
public class WorksController {
    @Autowired
    private WorksService worksService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Works works) {

        return worksService.save(works)?Result.suc():Result.fail();//判断是否保存保存的话返回suc,否则返回fail
    }
    //更新

    @PostMapping("/update")
    public Result update(@RequestBody Works works){

        return worksService.updateById(works)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){

        return worksService.removeById(id)?Result.suc():Result.fail();
    }

    //查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query ) { //这是通过自用定义的QueryPageParam 的方式进行调用
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        String workstype=(String) param.get("workstype");
        String course=(String) param.get("course");
        System.out.println("name==="+(String)param.get("name"));

        Page<Works> page=new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        LambdaQueryWrapper<Works> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.like(Works::getName,name);
        }
        if(StringUtils.isNotBlank(workstype) && !"null".equals(workstype)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.eq(Works::getWorkstype,workstype);
        }
        if(StringUtils.isNotBlank(course) && !"null".equals(course)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.eq(Works::getCourse,course);
        }




//        IPage result= CourseService.pageC(page);//返回模糊查询结果
        IPage result= worksService.pageCC(page,lambdaQueryWrapper);//返回模糊查询结果


        return Result.success(result.getRecords(),result.getTotal());
    }
}
