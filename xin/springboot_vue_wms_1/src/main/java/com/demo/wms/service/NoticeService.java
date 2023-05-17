package com.demo.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.wms.entity.Notice;
import com.demo.wms.entity.User;

import java.util.List;

public interface NoticeService extends IService<Notice> {

    List<Notice> countUnread(User user);
}
