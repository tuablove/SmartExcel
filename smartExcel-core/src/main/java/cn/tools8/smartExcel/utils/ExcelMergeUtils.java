package cn.tools8.smartExcel.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

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
