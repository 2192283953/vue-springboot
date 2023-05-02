package com.demo.wms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

@RestController
public class FileUploadController {

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @PostMapping("/upload")
    public Map<String, Object> fileupload(@RequestParam("file") MultipartFile file, HttpServletRequest req,
                                          @RequestParam(value = "F:\\eagedownload", required = false) String uploadPath) {
        Map<String,Object> result=new HashMap<>();
        String originName=file.getOriginalFilename();
        if(!originName.endsWith(".pdf")){
            result.put("status","error");
            result.put("msg","文件类型不对");
            return result;
        }

        String format =sdf.format(new Date());
        String realPath;
        if(uploadPath != null && !uploadPath.isEmpty()) {
            realPath = uploadPath + format;
        } else {
            realPath = req.getServletContext().getRealPath("/") + format;
        }
        File folder= new File(realPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String newName = UUID.randomUUID().toString() + ".pdf";
        try {
            file.transferTo(new File(folder,newName));
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + newName;
            result.put("status","success");
            result.put("url",url);
        } catch (IOException e) {
            result.put("status","error");
            result.put("mag",e.getMessage());
        }
        return result;
    }
}
