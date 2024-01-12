package cn.tools8.smartExcel.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelCleanUtils {

    /**
     * 清理行
     *
     * @param excelFilePath
     * @param outExcelPath
     * @param cleanRowBeginIndex
     */
    public static void clean(String excelFilePath, String outExcelPath, Integer cleanRowBeginIndex) throws Exception {
        clean(excelFilePath, outExcelPath, 0, cleanRowBeginIndex, null);
    }

    /**
     * 清理行
     *
     * @param excelFilePath
     * @param outExcelPath
     * @param sheetIndex
     * @param cleanRowBeginIndex
     * @param cleanRowEndIndex
     */
    public static void clean(String excelFilePath, String outExcelPath, Integer sheetIndex, Integer cleanRowBeginIndex, Integer cleanRowEndIndex) throws Exception {
        File file = new File(outExcelPath);
        file.getParentFile().mkdir();
        Path tempPath = ExcelUtils.copyToTemp(excelFilePath);
        File tempFile = new File(tempPath.toString());
        try (Workbook workbook = WorkbookFactory.create(tempFile)) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            if (cleanRowEndIndex == null) {
                cleanRowEndIndex = sheet.getLastRowNum();
            }
            if (cleanRowEndIndex > sheet.getLastRowNum()) {
                cleanRowEndIndex = sheet.getLastRowNum();
            }
            for (int i = cleanRowBeginIndex; i <= cleanRowEndIndex; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            try (OutputStream stream = Files.newOutputStream(file.toPath())) {
                workbook.write(stream);
                stream.flush();
            }
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }

    }

    /**
     * 删除行
     * @param excelFilePath
     * @param sheetIndex
     * @param cleanRowBeginIndex
     * @param cleanRowEndIndex
     * @throws Exception
     */
    public static void clean(String excelFilePath, Integer sheetIndex, Integer cleanRowBeginIndex, Integer cleanRowEndIndex) throws Exception {
        Path path = Paths.get(excelFilePath);
        Workbook workbook = null;
        try (InputStream inp = Files.newInputStream(path)) {
            workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            if (cleanRowEndIndex == null) {
                cleanRowEndIndex = sheet.getLastRowNum();
            }
            if (cleanRowEndIndex > sheet.getLastRowNum()) {
                cleanRowEndIndex = sheet.getLastRowNum();
            }
            for (int i = cleanRowBeginIndex; i <= cleanRowEndIndex; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            try (OutputStream stream = Files.newOutputStream(path)) {
                workbook.write(stream);
                stream.flush();
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}
