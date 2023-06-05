package com.demo.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Workstype;
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
public interface WorkstypeMapper extends BaseMapper<Workstype> {
    IPage<Workstype> pageCC(IPage<Workstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
