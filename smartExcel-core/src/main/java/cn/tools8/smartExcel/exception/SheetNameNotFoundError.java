package cn.tools8.smartExcel.exception;

/**
 * 找不到指定名称的sheet
 *
 * @author tuaobin 2023/6/16$ 10:54$
 */
public class SheetNameNotFoundError extends SmartExcelError {

    public SheetNameNotFoundError() {
        super();
    }

    public SheetNameNotFoundError(String message) {
        super(message);
    }
}
