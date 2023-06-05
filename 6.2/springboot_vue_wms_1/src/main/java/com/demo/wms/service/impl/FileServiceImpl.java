package com.demo.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.wms.entity.FileEntity;
import com.demo.wms.mapper.FileMapper;
import com.demo.wms.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date
 * @Description
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, FileEntity> implements FileService {
}
