package com.crp.qyUtil.upload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class CsvUtil {
	private static final String SEP1 = "#";
	private static final String SEP2 = "|";

	public static File createCSVFile(List exportData, Map rowMapper,
			String outPutPath, String filename) {

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(outPutPath + filename + ".csv");
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile), "GB2312"), 1024);
//			// 写入文件头部
//			for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator
//					.hasNext();) {
//				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
//						.next();
//				csvFileOutputStream.write("\""
//						+ propertyEntry.getValue().toString() + "\"");
//				if (propertyIterator.hasNext()) {
//					csvFileOutputStream.write(",");
//				}
//			}
//			csvFileOutputStream.newLine();

			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = rowMapper.entrySet()
						.iterator(); propertyIterator.hasNext();) {
					Map.Entry propertyEntry = (Map.Entry) propertyIterator
							.next();
					csvFileOutputStream.write((String) BeanUtils.getProperty(
							row, (String) propertyEntry.getKey()));
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	public static List getList(String ssss) {
		List<Map<String, String>> list = new ArrayList<>();
		String aaaa = ssss.replace(" ", "");
		String bbbb = aaaa.replace("},{", "#");
		System.out.println(bbbb);
		if (bbbb.length() > 13) {
			String ssss0 = bbbb.substring(10, bbbb.length() - 3);
			System.out.println(ssss0);
			String[] array0 = ssss0.split("#");
			for (int i = 0; i < array0.length; i++) {
				Map map = new HashMap<>();
				String[] array1 = array0[i].split(",");
				for (int j = 0; j < array1.length; j++) {
					String[] array2 = array1[j].split("=");
					if (array2.length == 2) {
						map.put(array2[0], array2[1]);
					}
					else if (array2.length == 1) {
						map.put(array2[0], "");
					}
					else if (array2.length == 0) {
						map.put("", "");
					}
				}
				list.add(map);
			}
		}
		return list;
	}

	   /**
     * format yyyyMMddHHmmss to yyyy-MM-dd HH:mm:ss
     *
     * @param str
     * @return
     */
    public static String format14StrTo19TimeStr(String str) {
        if (str != null && str.length() == 14) {
            return format8StrTo10DateStr(str.substring(0, 8)) + " " + format6StrTo8TimeStr(str.substring(8, 14));
        } else {
            return str;
        }
    }
    
    /**
     * format yyyyMMdd to yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static String format8StrTo10DateStr(String str) {
        if (str != null && str.length() == 8) {
            return str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
        } else {
            return str;
        }
    }
    
    /**
     * format HHmmss to HH:mm:ss
     *
     * @param str
     * @return
     */
    public static String format6StrTo8TimeStr(String str) {
        if (str != null && str.length() == 6) {
            return str.substring(0, 2) + ":" + str.substring(2, 4) + ":" + str.substring(4, 6);
        } else {
            return str;
        }
    }
    
	public static void main(String[] args) {

		List exportData = new ArrayList<Map>();
		Map row1 = new LinkedHashMap<String, String>();
		row1.put("1", "11");
		row1.put("2", "12");
		row1.put("3", "13");
		row1.put("4", "14");
		exportData.add(row1);
		row1 = new LinkedHashMap<String, String>();
		row1.put("1", "21");
		row1.put("2", "22");
		row1.put("3", "23");
		row1.put("4", "24");
		exportData.add(row1);
		List propertyNames = new ArrayList();
		LinkedHashMap map = new LinkedHashMap();
		map.put("1", "第一列");
		map.put("2", "第二列");
		map.put("3", "第三列");
		map.put("4", "第四列");
		CsvUtil.createCSVFile(exportData, map, "d:/aaaaaa/mmm2/", "活动目录");
	}

}
