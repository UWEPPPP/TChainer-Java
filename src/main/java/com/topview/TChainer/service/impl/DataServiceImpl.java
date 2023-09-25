package com.topview.TChainer.service.impl;

import com.topview.TChainer.contract.util.Processor;
import com.topview.TChainer.service.CnsService;
import com.topview.TChainer.service.DataService;
import com.topview.TChainer.service.TChainerFactory;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DataServiceImpl implements DataService {

    private CnsService cnsService = TChainerFactory.getCnsService();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(int id, Class<T> clazz) throws InstantiationException, IllegalAccessException, TransactionBaseException, ContractCodecException {
        AssembleTransactionProcessor assembleTransactionProcessor = Processor.getAssembleTransactionProcessor();
        String contractAddress = cnsService.selectByNameAndVersion(clazz.getSimpleName(),"latest");
        List<Object> params = new ArrayList<>();
        params.add(id);
        TransactionResponse response = assembleTransactionProcessor.sendTransactionAndGetResponseByContractLoader(clazz.getSimpleName(), contractAddress, "get",params);
        List<Object> returnObject = response.getReturnObject();
        return (T) returnObject.get(0);
    }

    @Override
    public <T> Boolean set(int id, T data) {
        AssembleTransactionProcessor assembleTransactionProcessor = Processor.getAssembleTransactionProcessor();
        String contractAddress = cnsService.selectByNameAndVersion(data.getClass().getSimpleName(),"latest");
        List<Object> params = new ArrayList<>();
        params.add(id);
        params.add(data);
        return null;
    }

    @Override
    public <T> Boolean add(T data) {
        return null;
    }

    @Override
    public Integer getEventsBlock(Integer id) {
        return null;
    }
}
