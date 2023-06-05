package com.demo.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wms
 * @since 2023-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Works对象", description = "")
public class Works implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作业名")
    private String name;

    @ApiModelProperty(value = "课程")
    private Integer course;

    @ApiModelProperty(value = "作业分类")
    @TableField("worksType")
    private Integer workstype;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("成绩")
    private Integer chengji;

    @TableField(exist = false)
    private List<FileEntity> files;
}
