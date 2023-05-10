package com.demo.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.wms.common.QueryPageParam;
import com.demo.wms.common.Result;
import com.demo.wms.entity.Record;
import com.demo.wms.entity.Works;
import com.demo.wms.service.RecordService;
import com.demo.wms.service.WorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private WorksService worksService;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) { //这是通过自用定义的QueryPageParam 的方式进行调用
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String workstype = (String) param.get("workstype");
        String course = (String) param.get("course");
        System.out.println("name===" + (String) param.get("name"));

        Page<Record> page = new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.works=b.id and  b.course=c.id and b.worksType=d.id ");
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) { //如果传过来的数据不等null 才返回结果
            //  lambdaQueryWrapper.like(Record::getName,name);
            queryWrapper.like("b.name",name);
        }
        if (StringUtils.isNotBlank(workstype) && !"null".equals(workstype)) { //如果传过来的数据不等null 才返回结果
            queryWrapper.eq("d.id",workstype);

        }
        if (StringUtils.isNotBlank(course) && !"null".equals(course)) { //如果传过来的数据不等null 才返回结果
            //  lambdaQueryWrapper.eq(Record::getCourse,course);
            queryWrapper.eq("c.id",course);
        }
        IPage result= recordService.pageCC(page,queryWrapper);//返回模糊查询结果
        return Result.success(result.getRecords(),result.getTotal());

    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record) {
        Works works= worksService.getById(record.getWorks());
        int n=record.getCount();
        int num=works.getCount()+n;
        works.setCount(num);
        worksService.updateById(works);
        return recordService.save(record)?Result.suc():Result.fail();//判断是否保存保存的话返回suc,否则返回fail
    }
}
