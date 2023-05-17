package com.demo.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.demo.wms.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.wms.entity.Works;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2023-04-19
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage<Record> pageCC(IPage<Record> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
