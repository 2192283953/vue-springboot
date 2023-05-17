package com.demo.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author
 * @date
 * @Description
 */
@TableName("file")
@Data
public class FileEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer busId;
    private String fileName;
    private String filePath;

    public String getName(){
        return this.fileName;
    }
}
