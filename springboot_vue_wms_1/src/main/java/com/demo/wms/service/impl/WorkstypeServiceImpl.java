package com.demo.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.demo.wms.entity.Course;
import com.demo.wms.entity.Workstype;
import com.demo.wms.mapper.CourseMapper;
import com.demo.wms.mapper.WorkstypeMapper;
import com.demo.wms.service.WorkstypeService;
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
public class WorkstypeServiceImpl extends ServiceImpl<WorkstypeMapper, Workstype> implements WorkstypeService {
    @Resource
    private WorkstypeMapper workstypeMapper;
    @Override
    public IPage pageCC(IPage<Workstype> page, Wrapper wrapper) {
        return workstypeMapper.pageCC(page,wrapper);
    }
}
