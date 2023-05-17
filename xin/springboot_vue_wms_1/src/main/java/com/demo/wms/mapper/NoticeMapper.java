package com.demo.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.wms.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {


    List<Notice> countUnread(Integer id);
}
