package com.demo.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.wms.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.wms.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-04-16
 */
public interface CourseService extends IService<Course> {
    IPage pageCC(IPage<Course> page, Wrapper Wrapper);

}
