package com.cloud.school.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.servlet.view.document.AbstractExcelView;


public class StudentExcelView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> modal,
			HSSFWorkbook workbook, HttpServletRequest arg2, HttpServletResponse response)
			throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"User_List.xls\"");
		HSSFSheet sheet = workbook.createSheet("Users sheet");
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list=(List<Map<String,Object>>)modal.get("userList");
		Row row = sheet.createRow(0);
		String array[]={"User Name","Campus Id","Status"};
		CellStyle style = workbook.createCellStyle();
		 Font font = workbook.createFont();
		 font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		 style.setFont(font);
	    for (int i = 0; i < array.length; i++) {
	    	Cell cellheding = row.createCell(i);
       	 	cellheding.setCellValue((String)array[i]);
       	    cellheding.setCellStyle(style);
		}
		int rownum = 1;
		for (Map<String,Object> customItemPlaceOrder : list) {
		    row = sheet.createRow(rownum++);
		    Object [] objArr = new Object[] { customItemPlaceOrder.get("user_name").toString(), customItemPlaceOrder.get("campus_id").toString(),customItemPlaceOrder.get("status").toString()};
		    int cellnum = 0;
		    for (Object obj : objArr) {
		        Cell cell = row.createCell(cellnum++);
		        if(obj instanceof Date) 
		            cell.setCellValue((Date)obj);
		        else if(obj instanceof Boolean)
		            cell.setCellValue((Boolean)obj);
		        else if(obj instanceof String)
		            cell.setCellValue((String)obj);
		        else if(obj instanceof Double)
		            cell.setCellValue((Double)obj);
		        else if(obj instanceof Integer)
		            cell.setCellValue((Integer)obj);
		        else if(obj instanceof Long)
		            cell.setCellValue((Long)obj);
		        else
		        	 cell.setCellValue("");
		        
		    }
		}
		
	}

}
