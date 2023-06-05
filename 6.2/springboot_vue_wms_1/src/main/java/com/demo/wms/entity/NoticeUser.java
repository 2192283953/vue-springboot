package com.demo.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @date
 * @Description
 */
@Data
public class NoticeUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer nid;
    private Integer uid;
    private String status;
}
