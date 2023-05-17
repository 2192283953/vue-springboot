package com.demo.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.wms.common.QueryPageParam;
import com.demo.wms.common.Result;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Menu;
import com.demo.wms.service.CourseService;
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
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Course course) {

        return courseService.save(course)?Result.suc():Result.fail();//判断是否保存保存的话返回suc,否则返回fail
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Course course){

        return courseService.updateById(course)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){

        return courseService.removeById(id)?Result.suc():Result.fail();
    }

    //查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query ) { //这是通过自用定义的QueryPageParam 的方式进行调用
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));

        Page<Course> page=new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        LambdaQueryWrapper<Course> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.like(Course::getName,name);
        }




//        IPage result= CourseService.pageC(page);//返回模糊查询结果
        IPage result= courseService.pageCC(page,lambdaQueryWrapper);//返回模糊查询结果


        return Result.success(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list() {
        List list = courseService.lambdaQuery().list();
        return  Result.suc(list);
    }
}



