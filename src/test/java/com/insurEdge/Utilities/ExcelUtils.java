package com.insurEdge.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility class for reading Excel data.
 * Path configured for: C:\\Users\\2461365\\eclipse-workspace\\com.insurEdge\\customer_data.xlsx
 */
public class ExcelUtils {

    private static final String EXCEL_PATH = "C:\\Users\\2461365\\eclipse-workspace\\com.insurEdge\\customer_data.xlsx";

    /**
     * Reads all rows from a given sheet index.
     * @param sheetIndex index of the sheet (0 = first sheet)
     * @return List of rows, each row as a List<String>
     */
    public static List<List<String>> readSheet(int sheetIndex) {
        List<List<String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            DataFormatter formatter = new DataFormatter();

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(formatter.formatCellValue(cell).trim());
                }
                data.add(rowData);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }

        return data;
    }

    /**
     * Reads a single cell value.
     * @param sheetIndex sheet index (0 = first sheet)
     * @param rowIndex row index (0-based)
     * @param colIndex column index (0-based)
     * @return String value of the cell
     */
    public static String getCellValue(int sheetIndex, int rowIndex, int colIndex) {
        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row row = sheet.getRow(rowIndex);
            if (row == null) return "";

            Cell cell = row.getCell(colIndex);
            if (cell == null) return "";

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell).trim();

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel cell: " + e.getMessage(), e);
        }
    }
}
