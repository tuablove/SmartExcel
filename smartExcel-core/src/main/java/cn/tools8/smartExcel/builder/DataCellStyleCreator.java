package cn.tools8.smartExcel.builder;

import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.handler.IWriteDataCellInitializeStyleHandler;
import cn.tools8.smartExcel.handler.IWriteTitleCellStyleHandler;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import cn.tools8.smartExcel.manager.ExcelWriteCellStyleManager;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;

/**
 * 数据样式创建
 *
 * @author tuaobin 2023/6/25$ 10:04$
 */
public class DataCellStyleCreator implements IExcelCellStyleCreator{

    private ExcelWriteCellStyleManager genericCellStyleManager;

    private IExcelCellStyleCreator creator;

    /**
     * 初始化
     *
     * @param creator
     * @param genericCellStyleManager
     * @param styleHandler
     * @return
     */
    public ExcelWriteCellStyleManager create(IExcelCellStyleCreator creator, ExcelWriteCellStyleManager genericCellStyleManager, IWriteDataCellInitializeStyleHandler styleHandler) {
        this.creator=creator;
        this.genericCellStyleManager=genericCellStyleManager;
        ExcelWriteCellStyleManager cellStyleManager = new ExcelWriteCellStyleManager();
        if (styleHandler != null) {
            styleHandler.onCreating(cellStyleManager, this);
        }
        return cellStyleManager;
    }

    @Override
    public CellStyle newCellStyle() {
        CellStyle style = this.genericCellStyleManager.getCellStyle(GenericStyleTypeEnum.CONTENT.getType());
        CellStyle cellStyle = creator.newCellStyle();
        cellStyle.cloneStyleFrom(style);
        return cellStyle;
    }

    @Override
    public Font newCellFont() {
        return creator.newCellFont();
    }

    @Override
    public DataFormat newDataFormat() {
        return creator.newDataFormat();
    }
}
