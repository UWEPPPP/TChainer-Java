package com.topview.TChainer;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.io.IOException;
import java.util.ArrayList;
@Slf4j
public class Deploy {
    public static void main(String[] args) throws IOException, TransactionBaseException, ContractCodecException {
        BcosSDK ddd = BcosSDK.build("src/main/resources/config.toml");
        Client client = ddd.getClient("group0");
        CryptoKeyPair cryptoSuite = client.getCryptoSuite().getCryptoKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client,
                cryptoSuite, "src/main/resources/contract/TestData/contract",
                "src/main/resources/contract/TestData/contract");
        TransactionResponse testData = transactionProcessor.deployByContractLoader("TestData", new ArrayList<>());
        log.info("{}",testData.getTransactionReceipt().getContractAddress());
    }
}
