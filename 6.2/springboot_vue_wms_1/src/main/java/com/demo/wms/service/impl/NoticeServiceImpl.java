package com.demo.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.wms.entity.Notice;
import com.demo.wms.entity.User;
import com.demo.wms.mapper.NoticeMapper;
import com.demo.wms.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

    @Override
    public List<Notice> countUnread(User user) {
        return this.baseMapper.countUnread(user.getId());
    }
}
