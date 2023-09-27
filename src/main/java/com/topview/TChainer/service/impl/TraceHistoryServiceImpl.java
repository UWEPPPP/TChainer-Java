package com.topview.TChainer.service.impl;

import com.topview.TChainer.contract.util.Processor;
import com.topview.TChainer.entity.CnsContainer;
import com.topview.TChainer.service.CnsService;
import com.topview.TChainer.service.DataService;
import com.topview.TChainer.service.TChainerFactory;
import com.topview.TChainer.service.TraceHistoryService;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosBlock;
import org.fisco.bcos.sdk.v3.client.protocol.response.BcosTransactionReceipt;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderInterface;
import org.fisco.bcos.sdk.v3.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.v3.transaction.model.exception.TransactionBaseException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TraceHistoryServiceImpl implements TraceHistoryService {
    private static final CnsService cnsService = TChainerFactory.getCnsService();
    private static final DataService dataService = TChainerFactory.getDataService();
    @Override
    public <T> List<T> traceHistory(int id, Class<T> clazz) throws ContractCodecException, TransactionBaseException {
        Client client = Processor.getClient();
        TransactionDecoderInterface decoder = new TransactionDecoderService(Processor.getCryptoSuite(), client.isWASM());
        CnsContainer latest = cnsService.selectByNameAndVersion(clazz.getSimpleName(), "latest");
        String abi = latest.getAbi();
        String address = latest.getAddress();
        Integer eventsBlock = dataService.getEventsBlock(id, clazz);
        List<T> result = new ArrayList<>();
        Integer blockHeight = eventsBlock;
        do {
            BcosBlock blockHashByNumber = client.getBlockByNumber(BigInteger.valueOf(blockHeight),false,false);
            BcosBlock.Block block = blockHashByNumber.getBlock();
            List<BcosBlock.TransactionObject> transactions = block.getTransactionObject();
            for (BcosBlock.TransactionObject transaction :
                    transactions) {
                if (!transaction.getTo().equals(address)) {
                    continue;
                }
                BcosTransactionReceipt transactionReceipt = client.getTransactionReceipt(transaction.getHash(), false);
                Map<String, List<List<Object>>> stringListMap = decoder.decodeEvents(abi, transactionReceipt.getTransactionReceipt().getLogEntries());
                if (stringListMap.containsKey("execute" + clazz.getSimpleName() + "Event")) {
                    List<List<Object>> lists = stringListMap.get("execute" + clazz.getSimpleName() + "Event");
                    for (List<Object> objects :
                            lists) {
                        //版本问题未解决 再来个结构体？
                        if (Objects.equals(objects.get(1),id)) {
                            result.add((T) objects.get(3));
                            blockHeight = (Integer) objects.get(0);
                            break;
                        }
                }
                }else {
                    continue;
                }
                break;
            }
        }while (blockHeight > 0);
        return result;
    }
}
