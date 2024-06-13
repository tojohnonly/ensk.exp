package com.ensk.exp.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EasyExcelDemo {
 
    public static void main(String[] args) throws Exception {
 
        InputStream inputStream = new FileInputStream("C:\\Users\\Administrator\\Desktop\\123.xlsx");
        List<ExcelData> dataList = EasyExcel.read(inputStream).head(ExcelData.class).sheet().headRowNumber(1).doReadSync();
        Map<String, List<ExcelData>> dataMap = dataList.stream().collect(Collectors.groupingBy(ExcelData::getString1));

        String filePath = "C:\\Users\\Administrator\\Desktop\\数据库脚本.sql";
        BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
        for (Map.Entry<String, List<ExcelData>> entry : dataMap.entrySet()) {

            // 组装商品描述HTML
            StringBuilder desc = new StringBuilder();
            String head = "<html> <head></head> <body>  &nbsp; ";
            desc.append(head);
            entry.getValue().forEach(e -> {
                String imagePattern = "  <img alt=\\\"\\\" src=\\\"https://img1.tianhong.cn/upload/image/pd/text/2024/5/16/%s\\\">";
                desc.append(String.format(imagePattern, e.getString3()));
            });
            String tail = " </body></html>";
            desc.append(tail);

            // 组装SQL并写入文件
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE `product_text`  SET `web_description` = \"");
            sql.append(desc);
            sql.append("\", `mobile_description` = \"");
            sql.append(desc);
            sql.append("\" WHERE `product_id` = ");
            sql.append(entry.getKey());
            sql.append(";");
            System.out.println(sql);
            out.write(sql.toString());
            out.write(System.lineSeparator());
        }
        out.close();
    }
}