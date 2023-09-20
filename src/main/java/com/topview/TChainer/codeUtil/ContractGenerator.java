package com.topview.TChainer.codeUtil;

import com.sun.javafx.util.Logging;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class ContractGenerator {
    private static final Map<String, String> typeMapping;

    static {
        typeMapping = new HashMap<>();
        typeMapping.put("boolean", "bool");
        typeMapping.put("byte", "int8");
        typeMapping.put("short", "int16");
        typeMapping.put("int", "int32");
        typeMapping.put("long", "int64");
        // 注意这需要使用Solidity的定点数库或自行实现
        typeMapping.put("float", "fixed");
        // 注意这需要使用Solidity的定点数库或自行实现
        typeMapping.put("double", "fixed");
        typeMapping.put("char", "string");
        typeMapping.put("String", "string");
        typeMapping.put("Date", "uint");
        // 这是Unix时间戳
    }


    public static void generateContract(Class<?> beanClass) {
        StringBuilder sb = new StringBuilder();
        String contractName = beanClass.getSimpleName();
        sb.append("// SPDX-License-Identifier: MIT\n");
        sb.append("pragma solidity >=0.6.10 <0.8.11;\n\n");
        sb.append("import \"./DataContract.sol\";\n\n");
        sb.append("contract ").append(contractName).append(" is DataContract {\n");
        sb.append("uint256 private latestDataIndex = 0;\n\n");
        sb.append("    struct Data {\n");
        StringBuilder decodeParams = new StringBuilder();
        StringBuilder decodeReceiveParams = new StringBuilder();
        StringBuilder inputParams = new StringBuilder();
        try {
            int length = beanClass.getDeclaredFields().length;
            for (Field field : beanClass.getDeclaredFields()) {
                length--;
                String javaType = field.getType().getSimpleName();
                String solidityType = typeMapping.getOrDefault(javaType, "unknown");
                if (field.getName() != "id") {
                    if (length > 0) {
                        decodeReceiveParams.append(solidityType).append(" ").append(field.getName()).append(", ");
                        decodeParams.append(solidityType).append(", ");
                    } else {
                        decodeReceiveParams.append(solidityType).append(" ").append(field.getName());
                        decodeParams.append(solidityType);
                    }

                    inputParams.append("        inputData.").append(field.getName()).append("=").append(field.getName()).append(";\n");
                }
                String fieldName = field.getName();
                sb.append("        ").append(solidityType).append(" ").append(fieldName).append(";\n");
            }

            sb.append("    }\n\n");

            sb.append("    mapping(uint256 => Data) private dataMap;\n\n");
            sb.append("    mapping(uint256 => uint256) private blockHeightMap;\n\n");
            sb.append("    // @param place 用于解决同一区块中有相同事件问题\n");
            sb.append("    event execute").append(beanClass.getSimpleName()).append("Event(uint256 blockHeight,uint8 place, Data data);\n\n");

            // 添加自定义的函数
            sb.append("\n    function add(bytes memory data) public override returns (bool) {\n");
            sb.append("        Data memory inputData;\n");
            sb.append("        (").append(decodeReceiveParams).append(") = abi.decode(data, (").append(decodeParams).append("));\n");
            sb.append("        inputData.id = latestDataIndex;\n");
            sb.append(inputParams);
            sb.append("        latestDataIndex++;\n");
            sb.append("        dataMap[latestDataIndex] = inputData;\n");
            sb.append("        emit  executeEvent(block.number, inputData);\n");
            sb.append("        blockHeightMap[latestDataIndex] = block.number;\n");
            sb.append("        return true;\n");
            sb.append("    }\n");


        } catch (Exception e) {
            log.error("generate contract error", e);
        }

        sb.append("}\n");
        String contractCode = sb.toString();
        String filePath = "src/main/resources/"+beanClass.getSimpleName()+".sol";
        writeContractToFile(contractCode, filePath);

    }

    public static void writeContractToFile(String contractCode, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(contractCode);
            writer.close();
            System.out.println("Contract code written to file: " + filePath);
        } catch (IOException e) {
            log.error("write contract to file error", e);
        }
    }
}
