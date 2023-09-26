package com.topview.TChainer.service;

import com.topview.TChainer.constant.ContractConstant;
import com.topview.TChainer.contract.util.ContractGenerator;
import com.topview.TChainer.contract.util.FileUtil;
import com.topview.TChainer.service.impl.DataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.topview.TChainer.constant.ContractConstant.ABI_FILE_PATH;
import static com.topview.TChainer.constant.ContractConstant.BIN_FILE_PATH;
import static com.topview.TChainer.contract.util.Scanner.findAnnotations;

/**
 * @author 刘家辉
 * @date 2023/09/25
 */
@Slf4j
public class TChainerSdk {

    public static TChainerSdk baseBuild(String fiscoConfigPath, String templatePackage, Boolean isDeploy) {
        BcosSDK sdk = BcosSDK.build(fiscoConfigPath);
        Client client = sdk.getClient("group0");
        AssembleTransactionProcessor transactionProcessor = null;
        if (isDeploy) {
            List<Class<?>> templates = null;
            try {
                templates = findAnnotations(templatePackage);
            } catch (IOException | ClassNotFoundException e) {
                log.error("scan template error", e);
            }
            if (templates != null) {
                for (Class<?> template : templates) {
                    //根据模板生成代码
                    ContractGenerator.generateContract(template);
                }
            }else {
                throw new RuntimeException("scan template error");
            }
            File privateKey = new File(ContractConstant.PRIVATE_KEY);
            CryptoKeyPair cryptoSuite = client.getCryptoSuite().getCryptoKeyPair();
            try {
                FileUtil.create(privateKey);
                FileUtil.write(privateKey, cryptoSuite.getHexPrivateKey());
            } catch (IOException e) {
                log.error("create file error", e);
            }
            try {
                transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client,
                        cryptoSuite, ABI_FILE_PATH,
                        BIN_FILE_PATH);
                for (Class<?> template : templates) {
                    TransactionResponse testData = null;
                    testData = transactionProcessor.deployByContractLoader(template.getSimpleName(), new ArrayList<>());
                    log.info("contract {}+{}", template.getSimpleName(), testData.getTransactionReceipt().getContractAddress());
                }
            } catch (IOException e) {
                log.error("create transactionProcessor error", e);
            } catch (TransactionBaseException | ContractCodecException e) {
                log.error("deploy contract error", e);
            }
        }
        return null;
    }


}
