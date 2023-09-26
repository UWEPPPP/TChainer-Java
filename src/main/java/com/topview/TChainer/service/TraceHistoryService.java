package com.topview.TChainer.service;

import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.util.List;

public interface TraceHistoryService {
    /**
     * 追溯历史版本
     * @param id
     * @param clazz
     * @return {@link T}
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    <T> List<T> traceHistory(int id, Class<T> clazz) throws InstantiationException, IllegalAccessException, ContractCodecException, TransactionBaseException;
}
