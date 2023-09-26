package com.topview.TChainer.service;

import com.topview.TChainer.entity.CnsContainer;

/**
 * @author 刘家辉
 * @date 2023/09/25
 */
public interface CnsService {
    /**
     * 插入合约信息
     * @param contractName 合约名字
     * @param version 版本
     * @param addr 地址
     * @param abi abi
     * @return {@link Boolean}
     */
    Boolean insert(String contractName, String version, String addr, String abi);

    /**
     * 返回该合约名的所有版本的记录
     * @param contractName 合约名
     * @return {@link String}
     */
    CnsContainer selectByName(String contractName);

    /**
     * 返回合约指定版本的地址
     * @param contractName 合约名
     * @param version 版本
     * @return {@link String}
     */
    CnsContainer selectByNameAndVersion(String contractName, String version);
}
