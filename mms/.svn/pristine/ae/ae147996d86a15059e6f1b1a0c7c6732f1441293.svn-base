package cn.mmdata.commons.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.jfinal.plugin.activerecord.Record;

public class WriteExcelEls {
	
	// 输出Excel
	public static void WriteExcel(List<Record> records) {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 得到工作簿的样式对象
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor((short) 13);// 设置背景色
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
		HSSFFont font = workbook.createFont();
		font.setFontName("仿宋_GB2312");
		font.setFontHeightInPoints((short) 13); //设置字体的大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		cellStyle.setFont(font);//选择需要用到的字体格式
		// 工作簿的格式对象
		HSSFSheet sheet = workbook.createSheet("sheet1");
		sheet.setColumnWidth(0, 180*50);
		sheet.setColumnWidth(1, 90*50);
		// 创建第一行
		Row rowFirst = sheet.createRow(0);
		Cell title0 = rowFirst.createCell(0); 
		Cell title1 = rowFirst.createCell(1); 
		title0.setCellValue("原始号码");
		title1.setCellValue("解密后号码");
		title0.setCellStyle(cellStyle);
		title1.setCellStyle(cellStyle);
	    for(int i=1; i<=records.size(); i++){   
			// 每个记录都创建一个行
	    	Row row = sheet.createRow(i); 
	    	Cell cell1 = row.createCell(0);
	    	Cell cell2 = row.createCell(1);
	    	cell1.setCellValue(records.get(i-1).getStr("mdnencode"));
	    	cell2.setCellValue(records.get(i-1).getStr("mdn"));
		   }
	    try (
	        FileOutputStream outputStream = new FileOutputStream("C://Users//Administrator//Desktop//Test.xls")){
            workbook.write(outputStream);
        }catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
}
