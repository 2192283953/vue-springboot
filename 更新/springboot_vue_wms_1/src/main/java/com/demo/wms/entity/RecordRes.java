package com.demo.wms.entity;

import lombok.Data;

@Data
public class RecordRes extends Record {
    private String username;
    private String adminname;
    private String worksname;
    private String coursename;
    private String workstypename;
}

