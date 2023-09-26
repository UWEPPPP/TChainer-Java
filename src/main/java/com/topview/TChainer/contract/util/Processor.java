package com.topview.TChainer.contract.util;

import com.topview.TChainer.config.Property;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;

/**
 * @author 刘家辉
 * @date 2023/09/25
 */
@Slf4j
public class Processor {
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.toml";
    private static final String TEMPLATE_PACKAGE_NAME = "com.topview.TChainer.template";
    @Getter
    private static final Client client;
    @Getter
    private static final CryptoSuite cryptoSuite;
    @Getter
    private static AssembleTransactionProcessor assembleTransactionProcessor;

    static {
        client = BcosSDK.build(CONFIG_FILE_PATH).getClient(Property.getProperty("groupId"));
        cryptoSuite = client.getCryptoSuite();
    }

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
