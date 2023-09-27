package com.topview.TChainer.contract.util;

import com.topview.TChainer.contract.Address;
import com.topview.TChainer.contract.Mapping;
import com.topview.TChainer.contract.Uint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.topview.TChainer.constant.ContractConstant.TYPE_MAPPING;
import static com.topview.TChainer.constant.ContractConstant.UNKNOWN;

public class FieldUtil {

    public static String typeTransferUitl(Field field, String javaType) {
        String solidityType = UNKNOWN;
        if (field.getAnnotations().length != 0) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation instanceof Uint) {
                    int value = ((Uint) annotation).value();
                    if (((Uint) annotation).isArray()) {
                        solidityType = "uint" + value + "[]";
                    } else {
                        solidityType = "uint" + value;
                    }
                    break;
                }
                if (annotation instanceof Mapping) {
                    //暂定,有待考虑
                    String key=((Mapping) annotation).key();
                    String value= ((Mapping) annotation).value();
                    solidityType="mapping("+ key+ " =>"+value+")";
                }

                if (annotation instanceof Address){
                    solidityType="address";
                }


            }
        } else {
            solidityType = TYPE_MAPPING.getOrDefault(javaType, UNKNOWN);
        }

        return solidityType;
    }
}

