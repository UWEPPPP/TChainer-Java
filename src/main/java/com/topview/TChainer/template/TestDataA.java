package com.topview.TChainer.template;

import com.topview.TChainer.contract.StorageTemplate;
import com.topview.TChainer.contract.Uint;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘家辉
 * @date 2023/09/20
 */
@StorageTemplate
@Data
public class TestDataA {
    @Uint(value = 256)
    private int  id;
    private Date createTime;
    private Date updateTime;
}
