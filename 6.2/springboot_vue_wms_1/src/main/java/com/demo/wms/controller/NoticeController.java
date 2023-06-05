package com.demo.wms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.wms.common.QueryPageParam;
import com.demo.wms.common.Result;
import com.demo.wms.entity.Notice;
import com.demo.wms.entity.NoticeUser;
import com.demo.wms.entity.User;
import com.demo.wms.mapper.NoticeUserMapper;
import com.demo.wms.service.NoticeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author
 * @date
 * @Description
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @Resource
    private NoticeUserMapper noticeUserMapper;

    @GetMapping("page")
    Result page(Notice notice, QueryPageParam param) {
        Page<Notice> page = new Page<>(param.getPageNum(), param.getPageSize());
        LambdaQueryWrapper<Notice> wrapper = Wrappers.lambdaQuery(Notice.class);
        if (StrUtil.isNotBlank(notice.getTitle())) {
            wrapper.like(Notice::getTitle, notice.getTitle());
        }
        Page<Notice> noticePage = noticeService.page(page, wrapper);
        return Result.success(noticePage.getRecords(), noticePage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("save")
    Result save(@RequestBody Notice notice) {
        if (null == notice.getId()){
            notice.setCreateTime(new Date());
        }
        noticeService.saveOrUpdate(notice);
        return Result.suc();
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("delete")
    Result delete(Integer id) {
        noticeService.removeById(id);
        return Result.suc();
    }

    @GetMapping("countUnread")
    Result countUnread(User user){
        return Result.suc(noticeService.countUnread(user));
    }

    @PostMapping("read")
    Result read(@RequestBody NoticeUser notice){
        noticeUserMapper.insert(notice);
        return Result.suc();
    }

}
