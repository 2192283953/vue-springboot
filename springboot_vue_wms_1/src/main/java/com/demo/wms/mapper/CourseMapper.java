package com.demo.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.demo.wms.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.wms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2023-04-16
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    IPage<Course> pageCC(IPage<Course> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
