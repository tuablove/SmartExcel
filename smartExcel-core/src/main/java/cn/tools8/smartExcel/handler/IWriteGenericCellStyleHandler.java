package cn.tools8.smartExcel.handler;

import cn.tools8.smartExcel.enums.GenericStyleTypeEnum;
import cn.tools8.smartExcel.interfaces.IExcelCellStyleCreator;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author tuaobin 2023/6/20$ 17:48$
 */
public interface IWriteGenericCellStyleHandler {

    CellStyle onCreated(GenericStyleTypeEnum typeEnum, IExcelCellStyleCreator styleCreator);
}
