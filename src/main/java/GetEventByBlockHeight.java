import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.model.JsonTransactionResponse;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosTransaction;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockHash;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class test {
    public static String abi ="[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"data\",\"type\":\"string\"}],\"name\":\"Event\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"hello\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    public static void main(String[] args) throws IOException, ContractCodecException {
        BcosSDK ddd = BcosSDK.build("src/main/resources/config.toml");
        Client client = ddd.getClient();
        CryptoSuite cryptoSuite = client.getCryptoSuite();
// 构造TransactionDecoderService实例，传入是否密钥类型参数。并且传入是否使用scale解码
        TransactionDecoderInterface decoder = new TransactionDecoderService(cryptoSuite, client.isWASM());
        BcosBlock blockHashByNumber = client.getBlockByNumber(new BigInteger("431"),false,false);
        BcosBlock.Block block = blockHashByNumber.getBlock();
        List<BcosBlock.TransactionObject> transactions = block.getTransactionObject();
        String hash = transactions.get(0).getHash();
        BcosTransactionReceipt transactionReceipt = client.getTransactionReceipt(hash,false);
        TransactionReceipt transactionReceipt1 = transactionReceipt.getTransactionReceipt();
        Map<String, List<List<Object>>> stringListMap = decoder.decodeEvents(abi, transactionReceipt1.getLogEntries());
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(stringListMap);
        File file = new File("output.json");
        FileWriter writer = new FileWriter(file);
        writer.write(s);
        writer.close();
    }
}
