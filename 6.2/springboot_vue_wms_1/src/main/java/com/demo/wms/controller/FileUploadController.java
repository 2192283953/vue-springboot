package com.demo.wms.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.demo.wms.common.Result;
import com.demo.wms.entity.FileEntity;
import com.demo.wms.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @Value("${file.upload}")
    private String uploadPath;

    @PostMapping("/upload")
    public Result fileupload(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> param) throws IOException {
        String originName = file.getOriginalFilename();

        String suffix = FileUtil.getSuffix(originName);
        String mainName = FileUtil.mainName(originName);
        if (!"pdf".equals(suffix)) {
            return Result.result(400, "文件格式不正确", 0L, null);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy-MM-dd/");
        String format = sdf.format(new Date());
        String realPath = uploadPath + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String newName = mainName + "_" + IdUtil.fastSimpleUUID()  +"." + suffix;
        file.transferTo(new File(folder, newName));
        FileEntity fileEntity = new FileEntity();
        fileEntity.setBusId(MapUtil.getInt(param, "id"));
        fileEntity.setFileName(originName);
        fileEntity.setFilePath("/upload" + format + newName);
        fileService.save(fileEntity);
        return Result.suc(fileEntity);
    }

    @GetMapping("/file/delete")
    public Result remove(Integer id) {
        fileService.removeById(id);
        return Result.suc();
    }
}
