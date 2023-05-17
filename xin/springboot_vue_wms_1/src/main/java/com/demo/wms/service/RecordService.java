package com.demo.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.wms.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.wms.entity.Works;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-04-19
 */
public interface RecordService extends IService<Record> {
    IPage pageCC(IPage<Record> page, Wrapper Wrapper);
}
