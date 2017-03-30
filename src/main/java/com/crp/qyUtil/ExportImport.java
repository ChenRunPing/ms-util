package com.crp.qyUtil;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.WritableFont;

public class ExportImport {

	/**
	 * 
	 * 说明：将数据导出excel
	 * @param os
	 * @param toExcelList   需要导出list（map对象）
	 * @param sheet  		导出的名称
	 * @param columnNames 	对应的map的key
	 * @param showFields  	每列显示标题
	 * @throws Exception
	 */
	public static final int SHEET_SIZE = 50000;
	public static void exportExcel(OutputStream os, List toExcelList, String sheet, String[] columnNames,Map<String,String> showFields) throws Exception {
		try{
			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
			
			if(toExcelList != null) {
				int listSize = toExcelList.size();
				int sheetNumber = listSize/SHEET_SIZE;
				for(int x = 0; x <= sheetNumber;x++) {
					
					jxl.write.Label labelC = null;
					jxl.write.WritableSheet ws = wwb.createSheet(sheet+x, x);
					jxl.write.WritableFont wfc = new jxl.write.WritableFont(
							WritableFont.ARIAL, 16, WritableFont.BOLD, false,
							UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.WHITE);
					jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
					wcfFC.setBackground(jxl.format.Colour.ORANGE);

					// 写入标题
					for(int i = 0; i < columnNames.length; i++) {
						labelC = new jxl.write.Label(i, 0, showFields.get(columnNames[i]), wcfFC);
						ws.addCell(labelC);
						ws.setColumnView(i, 15);
					}
					// 按照数据分表
					ws.setRowView(0, 500);
					int sheetNum = SHEET_SIZE;
					int startNum = SHEET_SIZE*x;
					if(x == sheetNumber) { // 最后一个表数量
						sheetNum = listSize - SHEET_SIZE*x;
					}
					for (int i = 0; i < sheetNum; i++) {
						Map map = (Map) toExcelList.get(startNum + i);
						int colIndex = 0;
						for(int j = 0; j < columnNames.length; j++) {
							String s = String.valueOf(map.get(columnNames[j]));
							// 导出电话的时候，需要添加区号
							if("tel".equals(columnNames[j])) {
								if(map.get("areaCode") != null&&!"".equals(String.valueOf(map.get("areaCode")))){
									s = String.valueOf(map.get("areaCode")) + "-" + s;
								}
								if(map.get("extNumber") != null&&!"".equals(String.valueOf(map.get("extNumber")))){
									s = s + "-" +  String.valueOf(map.get("extNumber"));
								}
							}
							// 导出传真的时候，需要添加传真区号
							if("fax".equals(columnNames[j])) {
								if(map.get("areaFax") != null&&!"".equals(String.valueOf(map.get("areaFax")))){
									s = String.valueOf(map.get("areaFax")) + "-" + s;
								}
							}
							labelC = new jxl.write.Label(colIndex, i+1, "null".equals(s) ? "" : s);
							ws.addCell(labelC);
							colIndex++;
						}
						ws.setRowView(i+1, 400);
					}
				}
			}
			// 写入Excel工作表
			wwb.write();
			// 关闭Excel工作薄对象
			wwb.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(20001/20000);
	}
}
