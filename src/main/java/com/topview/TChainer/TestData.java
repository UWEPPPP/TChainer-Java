package com.topview.TChainer;

import com.topview.TChainer.codeUtil.Uint;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘家辉
 * @date 2023/09/20
 */
@Data
public class TestData {
    @Uint(value = 256)
    private int  id;
    private Date createTime;
    private Date updateTime;
    @Uint(value = 256)
    private int version;
}
