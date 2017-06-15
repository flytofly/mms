package cn.mmdata.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfinal.plugin.activerecord.Record;
public class ReadExcelElsx {
    
	/**
	 * 读取加密状态的mdn
	 * 
	 * @param file 
	 * @return List<DecodeRecord> 记录数
	 * @throws IOException
	 */
	public List<Record> readXlsxDecodeRecord(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<Record> records = new ArrayList<Record>();
		// 循环工作表Sheet
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		int rowNums = xssfSheet.getLastRowNum();
		if (rowNums >= 1) {
			// 循环行Row ,从第二行取内容，第一行为标题列
			for (int rowNum = 1; rowNum <= rowNums; rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				// 跳过当前行为空或类型为String的情况
				if (hssfRow == null) {
					continue;
				}
				if(hssfRow.getCell(0) == null){
					continue;
				}
				if(hssfRow.getCell(0).getStringCellValue().length() == 22){
			        Record record = new Record();
			    	String column1 = hssfRow.getCell(0).getStringCellValue();
			    	record.set("mdnencode", column1);
			    	records.add(record);
				}
			}

		}
		return records;
    }
}