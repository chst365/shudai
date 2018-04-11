package com.shudailaoshi.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * excel导入导出工具类
 * 
 * @author liujialin
 *
 */
public class ExcelUtil {

	/**
	 * 
	 * @param input
	 *            流信息
	 * @param map
	 *            对应关系
	 * @param className
	 *            类名
	 * @param exportExcel
	 *            实现接口 持久层操作
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public static boolean importExcel(InputStream input, Map<Integer, String> map, Class<?> className,
			ExportExcel exportExcel) throws Exception {
		List list = new ArrayList();
		HSSFWorkbook workbook = new HSSFWorkbook(input);
		HSSFSheet sheet = workbook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();
		if (rows > 0) {
			for (int i = 1; i < rows; i++) {
				HSSFRow row = sheet.getRow(i);
				Object obj = className.newInstance();
				for (int j = 0; j < map.size(); j++) {
					HSSFCell cell = row.getCell(j);
					if (cell == null) {
						return false;
					}

					BeanUtils.setProperty(obj, map.get(j), getCellValue(cell));
				}
				list.add(obj);
			}

		}

		exportExcel.insertDataSource(list);
		return true;
	}

	private static Object getCellValue(HSSFCell cell) {
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				cellValue = cell.getDateCellValue();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = formatter.format(cellValue);
			} else {
				cellValue = new Integer((int) cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
		}
		return cellValue;
	}

	/**
	 * 
	 * @param map
	 *            文件头
	 * @param fileName
	 *            文件名
	 * @param isUpload
	 *            是否下载
	 * @param className
	 *            类名
	 * @param list
	 *            数据集合
	 * @param maps
	 *            对应的属性
	 * @throws Exception
	 */

	@SuppressWarnings("rawtypes")
	public static void writeExcel(Map<Integer, String> map, String fileName, Boolean isUpload, List dataSourceList,
			Map<Integer, String> maps) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(0);
		int i = 0;
		for (Integer key : map.keySet()) {
			HSSFCell cell = row.createCell(i);
			cell = row.createCell(i);
			cell.setCellValue(new HSSFRichTextString(map.get(key)));
			i++;
		}
		if (isUpload) {
			for (int j = 0; j < dataSourceList.size(); j++) {
				HSSFRow rows = sheet.createRow(j + 1);
				for (int s = 0; s < maps.size(); s++) {
					HSSFCell cell = rows.createCell(s);
					Object obj = dataSourceList.get(j);
					String value = BeanUtils.getProperty(obj, maps.get(s));
					cell.setCellValue(value);
				}
			}
		}
		FileOutputStream os = new FileOutputStream(fileName);
		wb.write(os);
		wb.close();

	}

	/**
	 * 文件下载
	 * 
	 * @param map
	 *            文件头信息
	 * @param fileName
	 *            文件名
	 * @param resp
	 *            响应 返回流信息
	 * @param isUpload
	 *            是否需要下载头文件以外的
	 * @param className
	 *            类名
	 * @param mapDataName
	 *            数据源属性对应关系
	 * @param list
	 *            数据源
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "resource" })
	public static void downFileExcelModel(Map<Integer, String> map, String fileName, HttpServletResponse resp,
			boolean isUpload, Map<Integer, String> mapDataName, List dataSourceList) throws Exception {
		Properties pro = PropertiesLoaderUtils.loadAllProperties("config.properties");
		String path = (String) pro.get("excelupload");
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		String filePath = path + fileName + com.shudailaoshi.utils.DateUtil.getTimeNumber() + ".xls";
		ExcelUtil.writeExcel(map, filePath, isUpload, dataSourceList, mapDataName);
		fileName = fileName+com.shudailaoshi.utils.DateUtil.getTimeNumber()  + ".xls";
		fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		resp.reset();
		resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		FileInputStream fis = new FileInputStream(filePath);
		byte[] b = new byte[fis.available()];
		fis.read(b);
		resp.getOutputStream().write(b);
		resp.getOutputStream().flush();
	}

	public static void main(String[] args) {
		System.out.println(ExcelUtil.class.getResource("/"));
	}
}