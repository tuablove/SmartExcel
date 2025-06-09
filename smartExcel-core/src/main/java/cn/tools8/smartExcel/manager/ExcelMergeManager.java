package cn.tools8.smartExcel.manager;

import cn.tools8.smartExcel.config.ExcelWriteConfig;
import cn.tools8.smartExcel.entity.definition.WriteDataFieldDefinition;
import cn.tools8.smartExcel.enums.ExcelMergeTypeEnum;
import cn.tools8.smartExcel.utils.ExcelMergeUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单元格合并管理
 * @author tuaobin 2023/11/23$ 17:42$
 */
public class ExcelMergeManager {
    private Map<ExcelMergeTypeEnum, List<int[]>> mergeTypeEnumListMap = new HashMap<>();

    private int mergedLastRow;

    public ExcelMergeManager(ExcelWriteConfig config, List<WriteDataFieldDefinition> mainDataFields, List<WriteDataFieldDefinition> childDataFields, int dataRowBegin) {
        List<int[]> verticalAll = parseVertical(mainDataFields, childDataFields);
        if (verticalAll != null && verticalAll.size() > 0) {
            mergeTypeEnumListMap.put(ExcelMergeTypeEnum.VERTICAL_ALL, verticalAll);
        }
        mergedLastRow = dataRowBegin;
    }

    /**
     * 合并数据
     * @param sheet
     * @param totalCount
     * @param rowIndex
     * @return
     * @throws IOException
     */
    public int merge(Sheet sheet, int totalCount, int rowIndex) throws IOException {
        if (((rowIndex != 0 && rowIndex % 100 == 0) || rowIndex == totalCount - 1)) {
            List<int[]> verticalAllColumns = mergeTypeEnumListMap.get(ExcelMergeTypeEnum.VERTICAL_ALL);
            if (verticalAllColumns != null && verticalAllColumns.size() > 0) {
                ExcelMergeUtils.mergeRangeVertical(sheet, new CellRangeAddress(mergedLastRow, sheet.getLastRowNum(), verticalAllColumns.get(0)[0], verticalAllColumns.get(0)[1]));
            }
            flushRows(sheet);
            mergedLastRow = sheet.getLastRowNum();
        }
        return mergedLastRow;
    }

    /**
     * 刷新数据到硬盘
     *
     * @param sheet
     * @throws IOException
     */
    private void flushRows(Sheet sheet) throws IOException {
        if (sheet instanceof SXSSFSheet) {
            SXSSFSheet sxssfSheet = (SXSSFSheet) sheet;
            sxssfSheet.flushRows(100);
        }
    }

    /**
     * 处理连续列垂向合并
     * @param mainDataFields
     * @param childDataFields
     * @return
     */
    private List<int[]> parseVertical(List<WriteDataFieldDefinition> mainDataFields, List<WriteDataFieldDefinition> childDataFields) {
        List<int[]> mainColumnRanges = parseVertical(mainDataFields, 0);
        List<int[]> childColumnRanges = parseVertical(childDataFields, mainDataFields.size());
        if (mainColumnRanges.size() == 0 && childColumnRanges.size() == 0) {
            return new ArrayList<>();
        }
        if (mainColumnRanges.size() > 1) {
            while (mainColumnRanges.size() > 1) {
                mainColumnRanges.remove(1);
            }
        }
        if (childColumnRanges.size() > 1) {
            while (childColumnRanges.size() > 1) {
                childColumnRanges.remove(1);
            }
        }
        if (mainColumnRanges.size() > 0 && childColumnRanges.size() == 0) {
            return mainColumnRanges;
        } else if (mainColumnRanges.size() == 0 && childColumnRanges.size() > 0) {
            return childColumnRanges;
        } else if (mainColumnRanges.size() > 0 && childColumnRanges.size() > 0 && (mainColumnRanges.get(0)[1] + 1) == childColumnRanges.get(0)[0]) {
            return new ArrayList<int[]>() {{
                add(new int[]{mainColumnRanges.get(0)[0], childColumnRanges.get(0)[1]});
            }};
        } else {
            return mainColumnRanges;
        }
    }

    /**
     * 处理连续列
     * @param dataFields
     * @param margin
     * @return
     */
    private List<int[]> parseVertical(List<WriteDataFieldDefinition> dataFields, int margin) {
        if (dataFields == null || dataFields.size() == 0) {
            return new ArrayList<>();
        }
        List<int[]> mergeCellRange = new ArrayList<>();
        int[] mergeColumns = new int[]{-1, -1};
        for (int j = 0; j < dataFields.size(); j++) {
            WriteDataFieldDefinition mainDataField = dataFields.get(j);
            if (ExcelMergeTypeEnum.VERTICAL_ALL.equals(mainDataField.getMergeType())) {
                if (mergeColumns[0] == -1) {
                    mergeColumns[0] = j;
                    mergeColumns[1] = j;
                } else {
                    mergeColumns[1] = j;
                }
            } else {
                if (mergeColumns[1] != -1) {
                    mergeCellRange.add(new int[]{margin+ mergeColumns[0],margin+ mergeColumns[1]});
                    mergeColumns[0] = -1;
                    mergeColumns[1] = -1;
                }
            }
        }
        return mergeCellRange;
    }
}
