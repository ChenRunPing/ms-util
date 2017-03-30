package com.crp.qyUtil.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 */
public class ExportExcel<T> {

	@Value("${shortName}")
	private String shortName;
	
	/**
	 * 根据数据生成excel流（xls类型文件）
	 * 
	 * @author wangtao Apr 1, 2014
	 * 
	 * @param fileTempletPath
	 * @param fields
	 * @param dataset
	 * @param out
	 * @throws Exception
	 */
	public void exportExcel(String fileTempletPath, String[] fields,
			Collection<T> dataset, OutputStream out) throws Exception {

		// 声明一个工作薄
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
				fileTempletPath));
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		// HSSFWorkbook workbook = new HSSFWorkbook( );

		// 取第一个表格
		HSSFSheet sheet = workbook.getSheetAt(0);

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = sheet.getPhysicalNumberOfRows();
		HSSFRow row = null;
		int rowNum = 1; // 序号
		while (it.hasNext()) {
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 序号
			row.createCell(0).setCellValue(rowNum++);

			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i + 1);
				String fieldName = fields[i];
				// 反射取值
				Object value = getMethodValue(t, fieldName);
				// 判断值的类型后进行强制类型转换
				Object textValue = getValue(value);

				setCellValue(cell, textValue);
			}
			index++;
		}

		workbook.write(out);

	}

	/**
	 * 根据数据生成excel流（xlsx类型文件） Apr 1, 2014
	 * 
	 * @param templet
	 * @param fields
	 * @param dataset
	 * @param out
	 * @throws Exception
	 */
	public void exportExcelToXlsx(String templet, String[] headers,
			String[] fields, Collection<T> dataset, OutputStream out)
			throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(templet);

		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);

		// 生成一个样式 表头样式
		HSSFCellStyle styleHeader = workbook.createCellStyle();
		// 设置这些样式
		styleHeader.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont fontHeader = workbook.createFont();
		fontHeader.setColor(HSSFColor.VIOLET.index);
		fontHeader.setFontHeightInPoints((short) 12);
		fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		styleHeader.setFont(fontHeader);
		// 生成并设置另一个样式
		HSSFCellStyle styleBody = workbook.createCellStyle();
		styleBody.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		styleBody.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleBody.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleBody.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleBody.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleBody.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont fontBody = workbook.createFont();
		fontBody.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		styleBody.setFont(fontBody);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor(shortName+"支付");

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(styleHeader);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = sheet.getPhysicalNumberOfRows();
		// int rowNum =1; //序号
		while (it.hasNext()) {
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 序号
			// row.createCell(0).setCellValue(rowNum++);

			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(styleBody);
				String fieldName = fields[i];
				// 反射取值
				Object value = getMethodValue(t, fieldName);
				// 判断值的类型后进行强制类型转换
				Object textValue = getValue(value);
				// 列赋值
				setCellValue(cell, textValue);

			}
			index++;
		}
		workbook.write(out);
	}

	/**
	 * 反射 取值 Apr 1, 2014
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	protected Object getMethodValue(Object obj, String fieldName)
			throws Exception {
		Object value = null;
		if (obj != null && obj instanceof Map) {
			value = ((Map) obj).get(fieldName);
		} else {
			String getMethodName = "get"
					+ fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			Class tCls = obj.getClass();
			Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
			value = getMethod.invoke(obj, new Object[] {});

		}
		return value;
	}

	protected Object getValue(Object value) {
		Object textValue = "";
		if (value != null) {
			if (value instanceof Boolean) {
				boolean bValue = (Boolean) value;
				textValue = "是";
				if (!bValue) {
					textValue = "否";
				}
			} else if (value instanceof Date) {
				Date date = (Date) value;
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				textValue = sdf.format(date);
			} else if (value instanceof String) {
				String val = (String) value;
				textValue = StringUtils.isBlank(val)
						|| "null".equalsIgnoreCase(val) ? "" : val;
			} else {
				// 其它数据类型都当作字符串简单处理
				textValue = value;
			}
		}
		return textValue;
	}

	/**
	 * XSSFCell
	 * 
	 * @date 2014-8-14 下午3:58:33
	 * @param cell
	 * @param value
	 */
	protected void setCellValue(XSSFCell cell, Object value) {
		if (value != null) {
			if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			} else if (value instanceof Float) {
				Float floatValue = (Float) value;
				cell.setCellValue(floatValue);
			} else if (value instanceof Double) {
				Double doubleValue = (Double) value;
				cell.setCellValue(doubleValue);
			} else if (value instanceof Long) {
				Long longValue = (Long) value;
				cell.setCellValue(longValue);
			} else if (value instanceof BigDecimal) {
				cell.setCellValue(((BigDecimal) value).doubleValue());
			} else {
				cell.setCellValue(value.toString());
			}
		}

	}

	/**
	 * HSSFCell
	 * 
	 * @date 2014-8-14 下午3:58:54
	 * @param cell
	 * @param value
	 */
	protected void setCellValue(HSSFCell cell, Object value) {
		if (value != null) {
			if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			} else if (value instanceof Float) {
				Float floatValue = (Float) value;
				cell.setCellValue(floatValue);
			} else if (value instanceof Double) {
				Double doubleValue = (Double) value;
				cell.setCellValue(doubleValue);
			} else if (value instanceof Long) {
				Long longValue = (Long) value;
				cell.setCellValue(longValue);
			} else if (value instanceof BigDecimal) {
				cell.setCellValue(((BigDecimal) value).doubleValue());
			} else {
				cell.setCellValue(value.toString());
			}
		}

	}

	/**
	 * @ClassName: StoreReportBean
	 * @Description: TODO
	 * @date 2014年6月9日
	 * 
	 */
	public void exportExcelByFields(String fileTempletPath, String[] fields,
			Collection<T> dataset, OutputStream out, String dateFormatter)
			throws Exception {

		// 声明一个工作薄
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
				fileTempletPath));
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		// HSSFWorkbook workbook = new HSSFWorkbook( );

		// 取第一个表格
		HSSFSheet sheet = workbook.getSheetAt(0);

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = sheet.getPhysicalNumberOfRows();
		HSSFRow row = null;
		int rowNum = 1; // 序号
		while (it.hasNext()) {
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 序号
			row.createCell(0).setCellValue(rowNum++);

			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i + 1);
				String fieldName = fields[i];
				// 反射取值
				Object value = getMethodValues(t, fieldName);
				// 判断值的类型后进行强制类型转换
				Object textValue = getValue(value, dateFormatter);
				setCellValue(cell, textValue);
			}
			index++;
		}

		workbook.write(out);

	}

	/**
	 * 反射 取值 如"name, code" 获得值"zhangshan00111"
	 * 
	 * @param o
	 * @param proNames
	 * @return
	 * @throws Exception
	 */
	public Object getMethodValues(Object o, String proNames) throws Exception {
		// Method m = o.getClass().getDeclaredMethod("set"+fieldName,new
		// Class[]{String.class}); //没有setName这样会报错
		String retValue = "";
		String fieldNames[] = proNames.split(",");
		for (String fieldName : fieldNames) {
			if (fieldName.length() == 1) {
				fieldName = fieldName.toUpperCase();
			} else if (fieldName.length() > 1) {
				fieldName = fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1, fieldName.length());
			}
			Object tempValue = fieldName;
			Method ms[] = o.getClass().getDeclaredMethods();
			if (null != ms && ms.length > 0) {
				for (Method m : ms) {
					if (m.getName().equals("get" + fieldName)) {
						Class c[] = m.getParameterTypes();
						if (c.length == 0) {
							tempValue = m.invoke(o);
							break;
						}
					}
				}
			}
			if (tempValue instanceof Date) {
				return tempValue;
			} else if (fieldNames.length == 1
					&& tempValue instanceof BigDecimal) {
				return tempValue;
			} else
				retValue += tempValue;
		}
		return retValue.replaceAll("null", "");
	}

	protected Object getValue(Object value, String dateFormatter) {
		Object textValue = "";
		if (value != null) {
			if (value instanceof Boolean) {
				boolean bValue = (Boolean) value;
				textValue = "是";
				if (!bValue) {
					textValue = "否";
				}
			} else if (value instanceof Date) {
				Date date = (Date) value;
				if (StringUtils.isNotEmpty(dateFormatter)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					textValue = sdf.format(date);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					textValue = sdf.format(date);
				}
			} else if (value instanceof String) {
				String val = (String) value;
				textValue = StringUtils.isBlank(val)
						|| "null".equalsIgnoreCase(val) ? "" : val;
			} else if (value instanceof BigDecimal) {
				textValue = value;
			} else {
				// 其它数据类型都当作字符串简单处理
				textValue = value;
			}
		}
		return textValue;
	}

	public static void main(String[] args) throws Exception {
		// 测试
		ExportExcel<Test> ex = new ExportExcel<Test>();
		String[] fields = { "id", "name" };
		String[] headers = { "id", "name" };
		List<Test> dataset = new ArrayList<Test>();
		dataset.add(new Test("10000001", "张三"));
		dataset.add(new Test("20000002", "1111"));
		dataset.add(new Test("30000003", "王五"));
		try {

			// OutputStream out = new FileOutputStream("/home/dev/temp/测试.xls");
			OutputStream out2 = new FileOutputStream("d://测试xlsx.xlsx");

			String path = "/home/dev/workspace/g2common/target/classes/测试.xls";//
			// String path =
			// Test.class.getClassLoader().getResource("/测试.xls").getPath();//"/测试.xls"
			// ;//
			// ex.exportExcel(path, fields, dataset, out);

			ex.exportExcelToXlsx("测试", headers, fields, dataset, out2);

			// out.close();
			out2.close();

			JOptionPane.showMessageDialog(null, "导出成功!");
			System.out.println("excel导出成功！");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}