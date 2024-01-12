package cn.tools8.smartExcel.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * excel表格帮助类
 */
public class ExcelUtils {
    /**
     * 写入一个格
     *
     * @param excelFilePath
     * @param sheetIndex
     * @param rowIndex
     * @param columnIndex
     * @param cellData
     * @throws Exception
     */
    public static void writeCell(String excelFilePath, Integer sheetIndex, Integer rowIndex, Integer columnIndex, String cellData) throws Exception {
        Path filePath = Paths.get(excelFilePath);
        Workbook workbook = null;
        try (InputStream inp = Files.newInputStream(filePath)) {
            workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Row targetRow = sheet.getRow(rowIndex);
            if (columnIndex == null) {
                columnIndex = (int) targetRow.getLastCellNum();
            }
            Cell cell = targetRow.getCell(columnIndex);
            if (cell == null) {
                cell = targetRow.createCell(columnIndex);
            }
            cell.setCellValue(cellData);
            try (OutputStream stream = Files.newOutputStream(filePath)) {
                workbook.write(stream);
                stream.flush();
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

    }

    /**
     * 复制文件到临时文件夹
     * @param excelFilePath
     * @return
     * @throws IOException
     */
    public static Path copyToTemp(String excelFilePath) throws IOException {
        File source = new File(excelFilePath);
        Path tempPath = Paths.get(FileUtils.getTempDirectoryPath(), UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(source.getName()));
        Files.copy(Paths.get(excelFilePath), tempPath, StandardCopyOption.REPLACE_EXISTING);
        return tempPath;
    }

    /**
     * 复制文件
     * @param excelFilePath
     * @param targetPath
     * @throws IOException
     */
    public static void copyTo(String excelFilePath, String targetPath) throws IOException {
        Files.copy(Paths.get(excelFilePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
    }
}
