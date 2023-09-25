package com.topview.TChainer.contract.util;

import com.topview.TChainer.constant.ContractConstant;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.io.IOException;
import java.util.ArrayList;

import static com.topview.TChainer.constant.ContractConstant.ABI_FILE_PATH;
import static com.topview.TChainer.constant.ContractConstant.BIN_FILE_PATH;

/**
 * @author 刘家辉
 * @date 2023/09/25
 */
@Slf4j
public class Processor {
    public static final String CONFIG_FILE_PATH = "src/main/resources/config.toml";
    public static final String TEMPLATE_PACKAGE_NAME = "com.topview.TChainer.template";
    public static AssembleTransactionProcessor assembleTransactionProcessor;

//    public static void init() throws IOException {
//        BcosSDK ddd = BcosSDK.build(CONFIG_FILE_PATH);
//        Client client = ddd.getClient("group0");
//        CryptoSuite cryptoSuite = client.getCryptoSuite();
//        CryptoKeyPair keyPair = cryptoSuite.getKeyPairFactory().createKeyPair(FileUtil.read(ContractConstant.PRIVATE_KEY));
//        assembleTransactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, ABI_FILE_PATH, BIN_FILE_PATH);
//    }
//
//    public static void resolve() throws TransactionBaseException, ContractCodecException {
//        TransactionResponse transactionResponse = assembleTransactionProcessor.sendTransactionAndGetResponseByContractLoader("resolve", "dd", "dada", new ArrayList<>());= assembleTransactionProcessor.sendTransactionAndGetResponseByContractLoader("resolve", "dd", "dada", new ArrayList<>());
//        transactionResponse.getTransactionReceipt().get
//    }
}
