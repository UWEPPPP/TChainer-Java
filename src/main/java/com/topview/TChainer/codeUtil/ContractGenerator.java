package com.topview.TChainer.codeUtil;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PipedReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ContractGenerator {
    private static final String UINT = "1";
    private static final String UNKNOWN = "unknown";
    private static final Map<String, String> TYPE_MAPPING;

    static {
        TYPE_MAPPING = new HashMap<>();
        TYPE_MAPPING.put("boolean", "bool");
        TYPE_MAPPING.put("byte", "int8");
        TYPE_MAPPING.put("short", "int16");
        TYPE_MAPPING.put("int", "int32");
        TYPE_MAPPING.put("long", "int64");
        // 注意这需要使用Solidity的定点数库或自行实现
        TYPE_MAPPING.put("float", "fixed");
        // 注意这需要使用Solidity的定点数库或自行实现
        TYPE_MAPPING.put("double", "fixed");
        TYPE_MAPPING.put("char", "string");
        TYPE_MAPPING.put("String", "string");
        TYPE_MAPPING.put("Date", "uint256");
        TYPE_MAPPING.put(UINT,"uint");
        // 这是Unix时间戳
    }


    public static void generateContract(Class<?> beanClass) {
        StringBuilder sb = new StringBuilder();
        String contractName = beanClass.getSimpleName();
        sb.append(ContractComposition.versionControl());
        sb.append("contract ").append(contractName).append(" is DataContract {\n");
        sb.append("    uint256 private latestDataIndex = 0;\n\n");
        sb.append("    struct Data {\n");
        StringBuilder decodeParams = new StringBuilder();
        StringBuilder decodeReceiveParams = new StringBuilder();
        StringBuilder inputParams = new StringBuilder();
        //根据beanClass生成对应数据模板的结构体
        try {
            int length = beanClass.getDeclaredFields().length;
            for (Field field : beanClass.getDeclaredFields()) {
                length--;
                String javaType = field.getType().getSimpleName();
                String solidityType = UNKNOWN;
                if(field.getAnnotations().length != 0){
                    for (Annotation annotation : field.getAnnotations()) {
                        if (annotation instanceof Uint) {
                            int value = ((Uint) annotation).value();
                            solidityType = "uint"+ value;
                            break;
                        }
                    }
                }else {
                    solidityType = TYPE_MAPPING.getOrDefault(javaType, UNKNOWN);
                }
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
            sb.append(ContractComposition.mapping());
            sb.append("    // @param place 用于解决同一区块中有相同事件问题\n");
            sb.append("    event execute").append(beanClass.getSimpleName()).append("Event(uint256 blockHeight,uint256 dataVersion, Data data);\n\n");
            String event = "execute"+beanClass.getSimpleName()+"Event";
            sb.append(ContractComposition.addFunction(decodeParams, decodeReceiveParams, inputParams, event));
            sb.append(ContractComposition.getFunction());
            sb.append(ContractComposition.getEventsBlockFunction());
            sb.append(ContractComposition.setFunction( decodeParams, decodeReceiveParams, inputParams,event));
            sb.append("}\n");
            String contractCode = sb.toString();
            String filePath = "src/main/resources/contract/"+beanClass.getSimpleName()+"/"+beanClass.getSimpleName()+".sol";
            writeContractToFile(contractCode, filePath);

        } catch (Exception e)  {
            log.error("generate contract error", e);
        }


    }


    public static void writeContractToFile(String contractCode, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().mkdirs() || !file.createNewFile()) {
                    log.error("create file error");
                }
            }
            FileWriter writer = new FileWriter(filePath);
            writer.write(contractCode);
            writer.close();
            System.out.println("Contract code written to file: " + filePath);
        } catch (IOException e) {
            log.error("write contract to file error", e);
        }
    }
}
