package com;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dai on 16-9-11.
 */
public class TypeChange {

    /**
     * mysql数据类型对应的java类型
     */
    private static Map<String, String> typeMap = new HashMap<String, String>();

    /**
     * 需要导入的jar包
     */
    private static Map<String, String> importMap = new HashMap<String, String>();

    static {
        typeMap.put("varchar", "String");
        typeMap.put("char", "String");
        typeMap.put("text", "String");
        typeMap.put("enum", "String");
        typeMap.put("blob", "byte[]");
        typeMap.put("int", "Long");
        typeMap.put("tinyint", "Integer");
        typeMap.put("smallint", "Integer");
        typeMap.put("mediumint", "Integer");
        typeMap.put("bit", "Boolean");
        typeMap.put("bigint", "BigInteger");
        typeMap.put("float", "Float");
        typeMap.put("double", "Double");
        typeMap.put("decimal", "BigDecimal");
        typeMap.put("boolean", "Integer");
        typeMap.put("id", "Long");
        typeMap.put("date", "Date");
        typeMap.put("time", "Time");
        typeMap.put("datetime", "Timestamp");
        typeMap.put("timestamp", "Timestamp");
        typeMap.put("year", "Date");

        importMap.put("bigint", "import java.math.BigInteger;\r\n");
        importMap.put("decimal", "import java.math.BigDecimal;\r\n");
        importMap.put("date", "import java.sql.Date;\r\n");
        importMap.put("time", "import java.sql.Time;\r\n");
        importMap.put("datetime", "import java.sql.Timestamp;\r\n");
        importMap.put("timestamp", "import java.sql.Timestamp;\r\n");
        importMap.put("year", "import java.sql.Date;\r\n");
    }

    /**
     * mysql类型转换为java类型，并且追加jar包
     *
     * @param importSet 已经需要导入的jar包
     * @param origin    mysql类型
     * @return java类型
     * @throws Exception
     */
    public static String get(Set<String> importSet, String origin) throws Exception {

        String type = typeMap.get(origin.toLowerCase());
        if (type == null) {
            throw new Exception("type cannot be recognized!");
        }

        String importPackage = importMap.get(origin);
        if (importPackage != null) {
            importSet.add(importPackage);
        }

        return type;
    }
}
