package com.topview.TChainer.template;

import com.topview.TChainer.contract.StorageTemplate;
import com.topview.TChainer.contract.Uint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author 刘家辉
 * @date 2023/09/20
 */
@EqualsAndHashCode(callSuper = true)
@StorageTemplate
@Data
public class TestDataA extends BaseData {
    @Uint(value = 256)
    private int  id;
    private Date createTime;
    private Date updateTime;
}
