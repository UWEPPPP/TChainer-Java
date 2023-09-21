package com.topview.TChainer.codeUtil;

/**
 * @author 刘家辉
 * @date 2023/09/21
 */
public class ContractFunction {
    public  static  StringBuilder setFunction(StringBuilder sb, StringBuilder decodeParams, StringBuilder decodeReceiveParams, StringBuilder inputParams){
        sb.append("\n    function set(uint256 id, bytes memory data) public override returns (bool) {\n");
        sb.append("        Data memory inputData;\n");
        sb.append("        (").append(decodeReceiveParams).append(") = abi.decode(data, (").append(decodeParams).append("));\n");
        sb.append("        inputData.id = latestDataIndex;\n");
        sb.append(inputParams);
        sb.append("        dataMap[id] = inputData;\n");
        sb.append("        return true;\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder getEventsBlockFunction(StringBuilder sb){
        sb.append("\n    function getEventsBlock(uint256 id) public override view returns (uint256) {\n");
        sb.append("        return blockHeightMap[id];\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder getFunction(StringBuilder sb){
        sb.append("\n    function get(uint256 id) public override view returns (Data memory) {\n");
        sb.append("        return dataMap[id];\n");
        sb.append("    }\n");
        return sb;
    }

    public static StringBuilder addFunction(StringBuilder sb, StringBuilder decodeParams, StringBuilder decodeReceiveParams, StringBuilder inputParams){
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
        return sb;
    }
}
