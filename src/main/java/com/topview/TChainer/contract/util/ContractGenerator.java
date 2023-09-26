package com.topview.TChainer.contract.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.fisco.solc.compiler.CompilationResult;
import org.fisco.solc.compiler.SolidityCompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.fisco.solc.compiler.SolidityCompiler.Options.*;


/**
 * @author 刘家辉
 * @date 2023/09/22
 */
@Slf4j
public class ContractGenerator {


    public static void generateContract(Class<?> beanClass) {
        StringBuilder sb = new StringBuilder();
        String contractName = beanClass.getSimpleName();
        //版本控制
        sb.append(ContractComposition.versionControl());
        //合约名
        sb.append("contract ").append(contractName).append(" is DataContract {\n");
        //常量
        sb.append("    uint256 private latestDataIndex = 0;\n\n");
        //结构体
        sb.append("    struct Data {\n");
        Map<Integer, StringBuilder> struct = ContractComposition.struct(beanClass);
        sb.append(struct.get(0));
        sb.append("    }\n\n");
        //映射
        sb.append(ContractComposition.mapping());
        //事件
        sb.append("    // @param place 用于解决同一区块中有相同事件问题\n");
        sb.append("    event execute").append(beanClass.getSimpleName()).append("Event(uint256 blockHeight,uint256 dataVersion, Data data);\n\n");
        String event = "execute" + beanClass.getSimpleName() + "Event";
        StringBuilder decodeParams = struct.get(1);
        StringBuilder decodeReceiveParams = struct.get(2);
        StringBuilder inputParams = struct.get(3);
        //add函数
        sb.append(ContractComposition.addFunction(decodeParams, decodeReceiveParams, inputParams, event));
        //get函数
        sb.append(ContractComposition.getFunction());
        //getEventsBlock函数
        sb.append(ContractComposition.getEventsBlockFunction());
        //set函数
        sb.append(ContractComposition.setFunction(decodeParams, decodeReceiveParams, inputParams, event));
        sb.append("}\n");
        String contractCode = sb.toString();
        String rootPath = "src/main/resources/contract/";
        writeContractToFile(contractName, contractCode, rootPath);

    }

    public static void writeContractToFile(String contractName, String contractCode, String rootPath) {
        try {
            String solPath = rootPath + "/" + contractName + ".sol";
            String abiPath = rootPath + "/abi/" + contractName + ".abi";
            String binPath = rootPath + "/bin/" + contractName + ".bin";
            File sol = new File(solPath);
            File abi = new File(abiPath);
            File bin = new File(binPath);
            FileUtil.create(sol);
            FileUtil.create(abi);
            FileUtil.create(bin);
            FileUtil.write(sol, contractCode);
            SolidityCompiler.Result res = SolidityCompiler.compile(sol, false, true, ABI, BIN, METADATA);
            if (!res.isFailed()) {
                // 编译成功，解析返回
                CompilationResult result = CompilationResult.parse(res.getOutput());
                // 获取HelloWorld合约的编译结果
                CompilationResult.ContractMetadata meta = result.getContract(contractName);
                System.out.printf("contractName: %s\n", meta.metadata);
                // abi
                FileUtil.write(abi, meta.abi);
                // bin
                FileUtil.write(bin, meta.bin);
                // 其他逻辑
            } else {
                log.error("compile contract error" + res.getErrors());
                // 编译失败，res.getError()保存编译失败的原因
            }
        } catch (IOException e) {
            log.error("write contract to file error", e);
        }
    }




}
