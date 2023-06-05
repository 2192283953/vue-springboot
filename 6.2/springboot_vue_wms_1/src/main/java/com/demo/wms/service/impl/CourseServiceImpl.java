package com.demo.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.User;
import com.demo.wms.mapper.CourseMapper;
import com.demo.wms.mapper.UserMapper;
import com.demo.wms.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2023-04-16
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private CourseMapper courseMapper;
    @Override
    public IPage pageCC(IPage<Course> page, Wrapper wrapper) {
        return courseMapper.pageCC(page,wrapper);
    }
}
