package cn.tools8.smartExcel.utils;

import cn.tools8.smartExcel.builder.WorkbookCreator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 合并单元格
 *
 * @author tuaobin 2023/6/20$ 14:01$
 */
public class ExcelMergeUtils {
    /**
     * 合并指定sheet中指定区域的表格
     *
     * @param sheet
     * @param range
     */
    public static void mergeRange(Sheet sheet, CellRangeAddress range) {
        Set<Position> positions = new HashSet<>();
        for (int row = range.getFirstRow(); row <= range.getLastRow(); row++) {
            for (int column = range.getFirstColumn(); column <= range.getLastColumn(); column++) {
                if (positions.contains(new Position(row, column))) {
                    continue;
                }
                Object cellValue = null;
                Cell cell = sheet.getRow(row).getCell(column);
                if (cell != null) {
                    positions.add(new Position(row, column));
                    cellValue = CellUtils.getCellValue(cell);
                    if (cellValue == null || cellValue.equals("")) {
                        continue;
                    }
                    boolean merged = false;
                    //开始合并列方向
                    for (int stepRow = row + 1; stepRow <= range.getLastRow(); stepRow++) {
                        Cell stepCell = sheet.getRow(stepRow).getCell(column);
                        Object stepCellValue = CellUtils.getCellValue(stepCell);
                        if (stepCellValue.equals(cellValue)) {
                            positions.add(new Position(stepRow, column));
                            if (stepRow == range.getLastRow()) {
                                sheet.addMergedRegion(new CellRangeAddress(row, stepRow, column, column));
                                merged = true;
                            }
                        } else {
                            if (stepRow != row + 1) {
                                sheet.addMergedRegion(new CellRangeAddress(row, stepRow - 1, column, column));
                                merged = true;
                            }
                            break;
                        }
                    }
                    //列没有合并，合并行
                    if (!merged) {
                        for (int stepColumn = column + 1; stepColumn <= range.getLastColumn(); stepColumn++) {
                            Cell stepCell = sheet.getRow(row).getCell(stepColumn);
                            Object stepCellValue = CellUtils.getCellValue(stepCell);
                            if (stepCellValue.equals(cellValue)) {
                                positions.add(new Position(row, stepColumn));
                                if (stepColumn == range.getLastColumn()) {
                                    sheet.addMergedRegion(new CellRangeAddress(row, row, column, stepColumn));
                                }
                            } else {
                                if (stepColumn != column + 1) {
                                    sheet.addMergedRegion(new CellRangeAddress(row, row, column, stepColumn - 1));
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 垂向合并指定sheet中指定区域的表格
     *
     * @param sheet
     * @param range
     */
    public static int mergeRangeVertical(Sheet sheet, CellRangeAddress range) {
        int mergedLastRow = -1;
        int mergeRowCount = -1;
        Object cellValue = null;
        for (int row = range.getFirstRow(); row <= range.getLastRow(); row++) {
            mergeRowCount = -1;
            for (int column = range.getFirstColumn(); column <= range.getLastColumn(); column++) {
                cellValue = getSheetCellValue(sheet, row, column);
                if (cellValue == null) {
                    continue;
                }
                int nextRow = row + 1;
                for (; nextRow <= Math.min(range.getLastRow(), mergeRowCount == -1 ? range.getLastRow() : row + mergeRowCount); nextRow++) {
                    Object nextCellValue = getSheetCellValue(sheet, nextRow, column);
                    if (!cellValue.equals(nextCellValue)) {
                        break;
                    }
                }
                if (mergeRowCount == -1) {
                    mergeRowCount = nextRow - row - 1;
                } else {
                    mergeRowCount = Math.min(mergeRowCount, nextRow - row - 1);
                }
            }
            if (mergeRowCount > 0) {
                for (int column = range.getFirstColumn(); column <= range.getLastColumn(); column++) {
                    mergedLastRow = row + mergeRowCount;
                    sheet.addMergedRegion(new CellRangeAddress(row, mergedLastRow, column, column));
                }
                row += mergeRowCount;
            }
        }
        return mergedLastRow;
    }

    private static Object getSheetCellValue(Sheet sheet, int row, int column) {
        Row dataRow = sheet.getRow(row);
        if (dataRow == null) {
            return null;
        }
        Cell cell = dataRow.getCell(column);
        Object cellValue = null;
        if (cell != null) {
            cellValue = CellUtils.getCellValue(cell);
        }
        return cellValue;
    }

    public static void main(String[] args) throws IOException {
        Workbook workbook = WorkbookFactory.create(new File("/Users/tobin/Downloads/送审汇总表_2023-11-23T11_21_21.536+08_00.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);
        mergeRangeVertical(sheet, new CellRangeAddress(1, sheet.getLastRowNum(), 0, 10));
        try (OutputStream stream = new FileOutputStream("/Users/tobin/Downloads/送审汇总表_2023-11-23T11_21_21.536+08_00_merge.xlsx")) {
            workbook.write(stream);
            stream.flush();
        }
    }

    /**
     * 单元格坐标
     */
    public static class Position {
        private int x;
        private int y;

        public Position() {
        }

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
