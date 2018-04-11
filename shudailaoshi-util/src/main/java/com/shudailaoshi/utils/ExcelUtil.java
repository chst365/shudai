package com.shudailaoshi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shudailaoshi.utils.exceptions.UtilException;
import com.shudailaoshi.utils.exceptions.UtilExceptionEnum;

/**
 * @author Liaoyifan
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class ExcelUtil {

	private static Workbook workBook;

	private static CellStyle headStyle; // 表头行样式
	private static Font headFont; // 表头行字体
	private static CellStyle contentStyle; // 内容行样式
	private static Font contentFont; // 内容行字体

	/**
	 * export2Excel
	 * 
	 * @param setInfo
	 * @param excelType
	 *            0 xls,1 xlsx,2 大数据导出
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void export2Excel(ExportSetInfo setInfo, int excelType)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		if (excelType == 0)
			workBook = new HSSFWorkbook();
		else if (excelType == 1)
			workBook = new XSSFWorkbook();
		else if (excelType == 2)
			workBook = new SXSSFWorkbook();

		init();

		Set<Entry<String, List>> set = setInfo.getObjsMap().entrySet();
		String[] sheetNames = new String[setInfo.getObjsMap().size()];

		int sheetNameNum = 0;
		for (Entry<String, List> entry : set) {
			sheetNames[sheetNameNum] = entry.getKey();
			sheetNameNum++;
		}

		Sheet[] sheets = getSheets(setInfo.getObjsMap().size(), sheetNames);
		createRowCell(set, setInfo, sheets);

		workBook.write(setInfo.getOut());
	}

	private static void init() {
		headStyle = workBook.createCellStyle();
		headFont = workBook.createFont();
		contentStyle = workBook.createCellStyle();
		contentFont = workBook.createFont();

		initHeadCellStyle();
		initHeadFont();
		initContentCellStyle();
		initContentFont();
	}

	public static void createRowCell(Set<Entry<String, List>> set, ExportSetInfo setInfo, Sheet[] sheets) {
		int sheetNum = 0;
		for (Entry<String, List> entry : set) {
			List objs = entry.getValue();
			createHeadRow(setInfo, sheets, sheetNum);
			String[] fieldNames = setInfo.getFieldNames().get(sheetNum);
			int rowNum = 1;
			for (Object obj : objs) {
				Row contentRow = sheets[sheetNum].createRow(rowNum);
				contentRow.setHeight((short) 300);
				Cell[] cells = getCells(contentRow, setInfo.getFieldNames().get(sheetNum).length);
				int cellNum = 0;// 去掉一列序号，因此从1开始
				if (fieldNames != null) {
					for (int num = 0; num < fieldNames.length; num++) {
						Object value = ReflectionUtil.invokeGetterMethod(obj, fieldNames[num]);
						cells[cellNum].setCellValue(value == null ? "" : value.toString());
						cellNum++;
					}
				}
				rowNum++;
			}
			// autoColumnSize(sheets, sheetNum, fieldNames); // 自动调整列宽
			sheetNum++;
		}
	}

	private static void createHeadRow(ExportSetInfo setInfo, Sheet[] sheets, int sheetNum) {
		Row headRow = sheets[sheetNum].createRow(0);
		headRow.setHeight((short) 350);
		for (int num = 0, len = setInfo.getHeadNames().get(sheetNum).length; num < len; num++) {
			Cell headCell = headRow.createCell(num);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue(setInfo.getHeadNames().get(sheetNum)[num]);
		}
	}

	private static Sheet[] getSheets(int num, String[] names) {
		Sheet[] sheets = new Sheet[num];
		for (int i = 0; i < num; i++) {
			sheets[i] = workBook.createSheet(names[i]);
		}
		return sheets;
	}

	private static Cell[] getCells(Row contentRow, int num) {
		Cell[] cells = new Cell[num];

		for (int i = 0, len = cells.length; i < len; i++) {
			cells[i] = contentRow.createCell(i);
			cells[i].setCellStyle(contentStyle);
		}
		return cells;
	}

	private static void autoColumnSize(Sheet[] sheets, int sheetNum, String[] fieldNames) {
		for (int i = 0; i < fieldNames.length + 1; i++) {
			sheets[sheetNum].autoSizeColumn(i, true);
		}
	}

	/***************************** 样式 *************************************/

	/**
	 * @Description: 初始化表头行样式
	 */
	private static void initHeadCellStyle() {
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headStyle.setFont(headFont);
		headStyle.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.index);
		headStyle.setBorderTop(CellStyle.BORDER_THIN);
		headStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headStyle.setBorderRight(CellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.index);
		headStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.index);
		headStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.index);
		headStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.index);
	}

	/**
	 * @Description: 初始化内容行样式
	 */
	private static void initContentCellStyle() {
		contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		contentStyle.setFont(contentFont);
		contentStyle.setBorderTop(CellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		contentStyle.setBorderRight(CellStyle.BORDER_THIN);
		contentStyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.index);
		contentStyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.index);
		contentStyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.index);
		contentStyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.index);
		contentStyle.setWrapText(true); // 字段换行
	}

	/**
	 * @Description: 初始化表头行字体
	 */
	private static void initHeadFont() {
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 10);
		headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headFont.setCharSet(Font.DEFAULT_CHARSET);
		headFont.setColor(IndexedColors.BLACK.index);
	}

	/**
	 * @Description: 初始化内容行字体
	 */
	private static void initContentFont() {
		contentFont.setFontName("宋体");
		contentFont.setFontHeightInPoints((short) 10);
		contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		contentFont.setCharSet(Font.DEFAULT_CHARSET);
		contentFont.setColor(IndexedColors.BLACK.index);
	}

	private static Object getCellValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
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
					cellValue = cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				cellValue = cell.getCellFormula();
				break;
			}
		}
		return cellValue;
	}

	/**
	 * @Description: 封装Excel导出的设置信息
	 */
	public static class ExportSetInfo {
		private Map<String, List> objsMap;
		private List<String[]> headNames;
		private List<String[]> fieldNames;
		private OutputStream out;

		public Map<String, List> getObjsMap() {
			return objsMap;
		}

		/**
		 * @param objMap
		 *            导出数据
		 * 
		 *            泛型 String : 代表sheet名称 List : 代表单个sheet里的所有行数据
		 */
		public void setObjsMap(Map<String, List> objsMap) {
			this.objsMap = objsMap;
		}

		public List<String[]> getFieldNames() {
			return fieldNames;
		}

		/**
		 * @param clazz
		 *            对应每个sheet里的每行数据的对象的属性名称
		 */
		public void setFieldNames(List<String[]> fieldNames) {
			this.fieldNames = fieldNames;
		}

		public List<String[]> getHeadNames() {
			return headNames;
		}

		/**
		 * @param headNames
		 *            对应每个页签的表头的每一列的名称
		 */
		public void setHeadNames(List<String[]> headNames) {
			this.headNames = headNames;
		}

		public OutputStream getOut() {
			return out;
		}

		/**
		 * @param out
		 *            Excel数据将输出到该输出流
		 */
		public void setOut(OutputStream out) {
			this.out = out;
		}
	}

	private static final String SHEET1 = "sheet1";

	/**
	 * 获取对应数据集合的Excel字节数组
	 * 
	 * @param headNameArray
	 *            标题名称
	 * @param fieldNameArray
	 *            字段名称
	 * @param lists
	 *            数据集合
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static byte[] getExcelByteArray(String[] headNameArray, String[] fieldNameArray, List<?> lists) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(headNameArray);
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(fieldNameArray);
			ExportSetInfo setInfo = new ExportSetInfo();
			setInfo.setHeadNames(headNames);
			setInfo.setFieldNames(fieldNames);
			Map<String, List> map = new HashMap<String, List>();
			map.put(SHEET1, lists);// 1个工作表情况
			setInfo.setObjsMap(map);
			setInfo.setOut(baos);
			ExcelUtil.export2Excel(setInfo, 0);
			return baos.toByteArray();
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.EXCEL_EXPROT_ERROR, e);
		}
	}

	/**
	 * 获取Excel数据List
	 * 
	 * @param excelFile
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 * @throws EncryptedDocumentException
	 */
	public static List<Object[]> getExcelObjectArrayList(InputStream excelFile) {
		try {
			workBook = WorkbookFactory.create(excelFile);
			Sheet sheet = workBook.getSheetAt(0);
			int rowsCount = sheet.getPhysicalNumberOfRows();
			List<Object[]> list = new ArrayList<Object[]>();
			for (int i = 0; i < rowsCount; i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				int cellsCount = row.getLastCellNum();
				Object[] data = new Object[cellsCount];
				for (int j = 0; j < cellsCount; j++) {
					data[j] = getCellValue(row.getCell(j));
				}
				list.add(data);
			}
			return list;
		} catch (Exception e) {
			throw new UtilException(UtilExceptionEnum.EXCEL_IMPORT_ERROR, e);
		}
	}

}