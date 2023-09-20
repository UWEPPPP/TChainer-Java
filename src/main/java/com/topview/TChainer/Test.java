package com.topview.TChainer;

import com.topview.TChainer.codeUtil.ContractGenerator;

import java.io.FileWriter;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        String contractCode = ContractGenerator.generateContract(TestData.class);
        String filePath = "src/main/resources/TestContract.sol";
        writeContractToFile(contractCode, filePath);
    }
    public static void writeContractToFile(String contractCode, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(contractCode);
            writer.close();
            System.out.println("Contract code written to file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
