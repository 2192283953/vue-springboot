package com.demo.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Works;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2023-04-17
 */
@Mapper
public interface WorksMapper extends BaseMapper<Works> {
    IPage<Works> pageCC(IPage<Works> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
