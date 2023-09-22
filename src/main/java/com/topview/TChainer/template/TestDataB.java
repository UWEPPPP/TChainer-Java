package com.topview.TChainer.template;

import com.topview.TChainer.contract.StorageTemplate;
import com.topview.TChainer.contract.Uint;
import lombok.Data;

/**
 * @author 刘家辉
 * @date 2023/09/22
 */
@StorageTemplate
@Data
public class TestDataB {
    @Uint(value = 256)
    private int  id;
    @Uint(value = 8)
    private int age;
    private String name;
    private String phoneNum;
}
