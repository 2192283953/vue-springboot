package com.demo.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.wms.entity.Notice;
import com.demo.wms.service.NoticeService;
import com.demo.wms.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice>
implements NoticeService{

}
