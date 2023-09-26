package com.topview.TChainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topview.TChainer.constant.ContractConstant;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Test {

    public static String abi ="[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"data\",\"type\":\"string\"},{\"components\":[{\"internalType\":\"uint256\",\"name\":\"id\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"}],\"indexed\":false,\"internalType\":\"struct Test.Data\",\"name\":\"data1\",\"type\":\"tuple\"}],\"name\":\"Event\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"data\",\"type\":\"string\"},{\"components\":[{\"internalType\":\"uint256\",\"name\":\"id\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"}],\"indexed\":false,\"internalType\":\"struct Test.Data\",\"name\":\"data1\",\"type\":\"tuple\"}],\"name\":\"WTF\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"hello\",\"outputs\":[{\"components\":[{\"internalType\":\"uint256\",\"name\":\"id\",\"type\":\"uint256\"},{\"internalType\":\"string\",\"name\":\"name\",\"type\":\"string\"}],\"internalType\":\"struct Test.Data\",\"name\":\"\",\"type\":\"tuple\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    public static void main(String[] args) throws IOException, ContractCodecException {
        BcosSDK ddd = BcosSDK.build("src/main/resources/config.toml");
        Client client = ddd.getClient();
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite, client.isWASM());
        BcosBlock blockHashByNumber = client.getBlockByNumber(new BigInteger("456"),false,false);
        BcosBlock.Block block = blockHashByNumber.getBlock();
        List<BcosBlock.TransactionObject> transactions = block.getTransactionObject();
        List<Map<String, List<List<Object>>>> decode = new ArrayList<>();
        int id = 1;
        for (BcosBlock.TransactionObject object:
                transactions) {

                String hash1 = object.getHash();
                BcosTransactionReceipt transactionReceipt = client.getTransactionReceipt(hash1, false);
                Map<String, List<List<Object>>> stringListMap = decoder.decodeEvents(abi, transactionReceipt.getTransactionReceipt().getLogEntries());
                for(Map.Entry<String, List<List<Object>>> entry:
                stringListMap.entrySet()){
                    System.out.println(entry.getKey());
                    for (List<Object> objects:
                            entry.getValue()) {
                        System.out.println(objects);
                    }
                }
            }
     //   }
//        ObjectMapper objectMapper = new ObjectMapper();
//        //List<Map<String, List<List<Object>>>>
//        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transactions);
//        File file = new File("output.json");
//        FileWriter writer = new FileWriter(file);
//        writer.write(s);
//        writer.close();
        //Map<String, List<List<Object>>>
//        String s1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decode.get(0));
//        File file1 = new File("output1.json");
//        FileWriter writer1 = new FileWriter(file1);
//        writer1.write(s1);
//        writer1.close();
//        //List<List<Object>>
//        String s11 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decode.get(0).get("WTF"));
//        File file11 = new File("output2.json");
//        FileWriter writer11 = new FileWriter(file11);
//        writer11.write(s11);
//        writer11.close();
//        //List<Object>
//        String s111 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decode.get(0).get("WTF").get(0));
//        File file111 = new File("output3.json");
//        FileWriter writer111 = new FileWriter(file111);
//        writer111.write(s111);
//        writer111.close();
//        String s1111 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decode.get(0).get("WTF").get(0).get(2));
//        File file1111 = new File("output4.json");
//        FileWriter writer1111 = new FileWriter(file1111);
//        writer1111.write(s1111);
//        writer1111.close();

    }
}
