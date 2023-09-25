package com.topview.TChainer.service;

import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

public interface DataService {

     <T>T get(int id,Class<T> clazz) throws InstantiationException, IllegalAccessException, TransactionBaseException, ContractCodecException;

     <T>Boolean set(int id, T data);

     <T>Boolean add(T data);

     Integer getEventsBlock(Integer id);
}
