package com;


import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dai on 16-9-11.
 */
public class BeanCreate extends DBBase {

    public static String tableName;

    public static void main(String[] args) throws Exception {
        getConnection();

        String sql = "SHOW TABLES";
        ResultSet set = runSql(sql);

        while (set.next()) {
            tableName = set.getString(1);

            createJava();
        }
        close();
        System.out.println("生成成功");
    }

    /**
     * 生成java类
     *
     * @throws Exception
     */
    public static void createJava() throws Exception {
        //将统一前缀去除(若没有统一前缀该代码需注释)
        String fileName = formatName(tableName.substring(tableName.indexOf("_")));

        //获取表信息
        String column = "SHOW FULL FIELDS FROM " + tableName;
        ResultSet rs = runSql(column);

        //检测目标文件夹是否存在，若不存在，生成
        File file = new File(targetFileRoot, fileName + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter writer = new FileWriter(file);
        StringBuffer head = new StringBuffer("package " + packageRoot + ";\r\n\r\n");
        StringBuffer sb = new StringBuffer("public class " + fileName + " {\r\n\r\n");

        //bean内的元素list
        List<ElementInfo> elements = new ArrayList<ElementInfo>();
        //需要导入的jar包
        Set<String> importPackages = new HashSet<String>();

        //备注
        String comment;
        //元素类型
        String type;
        //元素名称
        String element;

        while (rs.next()) {

            comment = rs.getString("comment");
            type = rs.getString("type");
            //将int(11)之类的转换为int
            type = type.substring(0, type.indexOf("(") > 0 ? type.indexOf("(") : type.length());
            //mysql类型转java并添加jar包
            type = TypeChange.get(importPackages, type);
            element = formatName(rs.getString("field"));

            ElementInfo info = new ElementInfo();
            info.setName(element).setType(type);
            elements.add(info);

            if (comment != null && !comment.equals("")) {
                sb.append("    /**\n" + "     * " + comment + "\n" + "     */\r\n");
            }
            sb.append("    private " + type + " " + element + ";\r\n\r\n");
        }

        if (importPackages.size() > 0) {
            for (String ip : importPackages) {
                head.append(ip);
            }
            head.append("\r\n");
        }

        //添加set/get方法
        for (ElementInfo e : elements) {
            element = e.getName();
            sb.append("    public " + fileName + " set" + element.substring(0, 1).toUpperCase() +
                    element.substring(1, element.length()) + "(" + e.getType() + " " + element + ") {\r\n        this."
                    + element + " = " + element);
            if (e.getType().equals("String")) {
                sb.append(" == null ? null : " + element + ".trim()");
            }
            sb.append(";\r\n");
            sb.append("        return this;\r\n");
            sb.append("    }\r\n\r\n");
            sb.append("    public " + e.getType() + " get" + element.substring(0, 1).toUpperCase() +
                    element.substring(1, element.length()) + "() {\r\n        return "
                    + element + ";\r\n    }\r\n\r\n");
        }

        sb.append("}");
        writer.write(head.append(sb).toString());
        writer.flush();
        writer.close();

    }

    /**
     * 将column名转换为小驼峰式
     *
     * @param origin column名
     * @return
     */
    public static String formatName(String origin) {

        String[] arrays = origin.split("_");
        StringBuffer sb = new StringBuffer(arrays[0]);
        for (int i = 1; i < arrays.length; i++) {
            sb.append(arrays[i].substring(0, 1).toUpperCase()).append(arrays[i].substring(1, arrays[i].length()));
        }
        return sb.toString();
    }

}
