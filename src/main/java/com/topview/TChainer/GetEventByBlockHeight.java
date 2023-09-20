package com.topview.TChainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通过块高获取事件
 * @author 刘家辉
 * @date 2023/09/17
 */
public class GetEventByBlockHeight {
    public static String abi ="[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"data\",\"type\":\"string\"}],\"name\":\"Event\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"getEventsBlock\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"hello\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    public static void main(String[] args) throws IOException, ContractCodecException {
        BcosSDK ddd = BcosSDK.build("src/main/resources/config.toml");
        Client client = ddd.getClient();
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite, client.isWASM());
        BcosBlock blockHashByNumber = client.getBlockByNumber(new BigInteger("445"),false,false);
        BcosBlock.Block block = blockHashByNumber.getBlock();
        List<BcosBlock.TransactionObject> transactions = block.getTransactionObject();
        List<Map<String, List<List<Object>>>> decode = new ArrayList<>();
        for (BcosBlock.TransactionObject object:
             transactions) {
            String hash1 = object.getHash();
            BcosTransactionReceipt transactionReceipt = client.getTransactionReceipt(hash1, false);
            Map<String, List<List<Object>>> stringListMap = decoder.decodeEvents(abi, transactionReceipt.getTransactionReceipt().getLogEntries());
            decode.add(stringListMap);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decode);
        File file = new File("output.json");
        FileWriter writer = new FileWriter(file);
        writer.write(s);
        writer.close();

    }
}
