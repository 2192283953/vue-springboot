package com.demo.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Works;
import com.demo.wms.mapper.CourseMapper;
import com.demo.wms.mapper.WorksMapper;
import com.demo.wms.service.WorksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2023-04-17
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements WorksService {
    @Resource
    private WorksMapper worksMapper;
    @Override
    public IPage pageCC(IPage<Works> page, Wrapper wrapper) {
        return worksMapper.pageCC(page,wrapper);
    }
}
