package com.demo.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Works;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-04-17
 */
public interface WorksService extends IService<Works> {
    IPage pageCC(IPage<Works> page, Wrapper Wrapper);
}
