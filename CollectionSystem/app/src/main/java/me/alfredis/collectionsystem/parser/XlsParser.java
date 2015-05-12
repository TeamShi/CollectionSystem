package me.alfredis.collectionsystem.parser;
/**
 * Created by jishshi on 2015/5/9.
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * xls工具类
 *
 * @author hjn
 */
public class XlsParser {

    public static void read(String filePath) throws IOException {
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        InputStream stream = new FileInputStream(filePath);
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        } else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet sheet1 = wb.getSheetAt(0);
        for (Row row : sheet1) {
            for (Cell cell : row) {
                System.out.print(cell.getStringCellValue() + "  ");
            }
            System.out.println();
        }
        stream.close();
    }

    public static boolean write(String outPath, String[][] array, String sheetName) throws Exception {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook();
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook();
        } else {
            System.out.println("您的文档格式不正确！");
            return false;
        }
        Sheet sheet1 = wb.createSheet(sheetName);
        // 循环写入行数据
        for (int i = 0, rows = array.length; i < rows; i++) {
            Row row = sheet1.createRow(i);
            // 循环写入列数据
            for (int j = 0, cols = array[i].length; j < cols; j++) {
                Cell cell = row.createCell(j);
                String text = array[i][j].equals("null") ? "" : array[i][j];
                cell.setCellValue(text);
            }
        }
        // 创建文件流
        OutputStream stream = new FileOutputStream(outPath);
        // 写入数据
        wb.write(stream);
        // 关闭文件流
        stream.close();
        return true;
    }


    public static void main(String[] args) {
        try {
            XlsParser.write("D:" + File.separator + "model.xlsx", new String[5][6], "sheet name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            XlsParser.read("D:" + File.separator + "model.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}