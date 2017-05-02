package com.crp.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;

import java.io.FileOutputStream;

/**
 * ms-util - com.crp.excel
 *
 * @author superChen
 * @create 2017-04-14 19:59
 */
public class ExcelUtils {

    public static void main(String[] args) throws Exception
    {
        //创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建sheet页
        HSSFSheet sheet = workbook.createSheet("学生表");
        //创建单元格
        HSSFRow row = sheet.createRow(0);
        HSSFCell c0 = row.createCell(0);
        c0.setCellValue(new HSSFRichTextString("学号"));
        HSSFCell c1 = row.createCell(1);
        c1.setCellValue(new HSSFRichTextString("姓名"));
        HSSFCell c2 = row.createCell(2);
        c2.setCellValue(new HSSFRichTextString("性别"));
        HSSFCell c3 = row.createCell(3);
        c3.setCellValue(new HSSFRichTextString("年龄"));
        HSSFCell c4 = row.createCell(4);
        c4.setCellValue(new HSSFRichTextString("2015年分数"));
        HSSFCell c5 = row.createCell(7);
        c5.setCellValue(new HSSFRichTextString("2014年分数"));


        HSSFRow row1 = sheet.createRow(1);
        HSSFCell c6 = row1.createCell(4);
        c6.setCellValue(new HSSFRichTextString("语文"));
        HSSFCell c7 = row1.createCell(5);
        c7.setCellValue(new HSSFRichTextString("数学"));
        HSSFCell c8 = row1.createCell(6);
        c8.setCellValue(new HSSFRichTextString("外语"));
        HSSFCell c9 = row1.createCell(7);
        c9.setCellValue(new HSSFRichTextString("语文"));
        HSSFCell c10 = row1.createCell(8);
        c10.setCellValue(new HSSFRichTextString("数学"));
        HSSFCell c11 = row1.createCell(9);
        c11.setCellValue(new HSSFRichTextString("外语"));

        Region region1 = new Region(0, (short)0, 1, (short)0);
        Region region2 = new Region(0, (short)1, 1, (short)1);
        Region region3 = new Region(0, (short)2, 1, (short)2);
        Region region4 = new Region(0, (short)3, 1, (short)3);
        Region region5 = new Region(0, (short)4, 0, (short)6);
        Region region6 = new Region(0, (short)7, 0, (short)9);
        sheet.addMergedRegion(region1);
        sheet.addMergedRegion(region2);
        sheet.addMergedRegion(region3);
        sheet.addMergedRegion(region4);
        sheet.addMergedRegion(region5);
        sheet.addMergedRegion(region6);

        FileOutputStream stream = new FileOutputStream("d:/student.xls");
        workbook.write(stream);
    }

}
