package com.topview.TChainer;

import com.topview.TChainer.constant.ContractConstant;
import com.topview.TChainer.contract.util.ContractGenerator;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.topview.TChainer.contract.util.Scanner.findAnnotations;

/**
 * @author 刘家辉
 * @date 2023/09/22
 */
@Slf4j
public class GenerateAndCompileAndDeploy {
    public static final String CONFIG_FILE_PATH = "src/main/resources/config.toml";
    public static final String TEMPLATE_PACKAGE_NAME = "com.topview.TChainer.template";
    public static final String ABI_FILE_PATH = "src/main/resources/abi";
    public static final String BIN_FILE_PATH = "src/main/resources/bin";
    public static void main(String[] args) throws IOException, ClassNotFoundException, TransactionBaseException, ContractCodecException {
        //扫描带StorageTemplate注解的类
        //带有StorageTemplate注解表明该类为需要上链的模板
        List<Class<?>> templates = findAnnotations(TEMPLATE_PACKAGE_NAME);
        for (Class<?> template : templates) {
            //根据模板自动生成solidity合约代码
            ContractGenerator.generateContract(template);
        }

        //对生成的合约在链上进行部署 (未进行证书conf导入的,默认注释以下代码,防止编译报错,需要进行链上部署的请自行取消注释)
//        BcosSDK ddd = BcosSDK.build(CONFIG_FILE_PATH);
//        Client client = ddd.getClient("group0");
//        CryptoKeyPair cryptoSuite = client.getCryptoSuite().getCryptoKeyPair();
//        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client,
//                cryptoSuite, ABI_FILE_PATH,
//                BIN_FILE_PATH);
//        for (Class<?> template : templates) {
//            TransactionResponse testData = transactionProcessor.deployByContractLoader(template.getSimpleName(), new ArrayList<>());
//            log.info("contract {}+{}",template.getSimpleName(),testData.getTransactionReceipt().getContractAddress());
//        }
    }
}
