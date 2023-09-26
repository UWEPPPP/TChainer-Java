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

public class TraceHistoryServiceImpl implements TraceHistoryService {
    private static final CnsService cnsService = TChainerFactory.getCnsService();
    private static final DataService dataService = TChainerFactory.getDataService();
    @Override
    public <T> List<T> traceHistory(int id, Class<T> clazz) throws ContractCodecException, TransactionBaseException {
        Client client = Processor.getClient();
        TransactionDecoderInterface decoder = new TransactionDecoderService(Processor.getCryptoSuite(), client.isWASM());
        BcosBlock blockHashByNumber = client.getBlockByNumber(BigInteger.valueOf(id),false,false);
        BcosBlock.Block block = blockHashByNumber.getBlock();
        List<BcosBlock.TransactionObject> transactions = block.getTransactionObject();
        List<Map<String, List<List<Object>>>> decode = new ArrayList<>();
        CnsContainer latest = cnsService.selectByNameAndVersion(clazz.getSimpleName(), "latest");
        String abi = latest.getAbi();
        for (BcosBlock.TransactionObject object:
                transactions) {
            String hash1 = object.getHash();
            BcosTransactionReceipt transactionReceipt = client.getTransactionReceipt(hash1, false);
            Map<String, List<List<Object>>> stringListMap = decoder.decodeEvents(abi, transactionReceipt.getTransactionReceipt().getLogEntries());
            decode.add(stringListMap);
        }
        Integer eventsBlock = dataService.getEventsBlock(id, clazz);
        for (Map<String, List<List<Object>>> stringListMap:
                decode) {
            List<List<Object>> list = stringListMap.get("blockHeight");

        }
        return null;
    }
}
