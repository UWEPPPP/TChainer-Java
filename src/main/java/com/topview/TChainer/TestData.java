package com.topview.TChainer;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘家辉
 * @date 2023/09/20
 */
@Data
public class TestData {
    private int id;
    private Date createTime;
    private Date updateTime;
    private int version;
}
