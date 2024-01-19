package cn.tools8.smartExcel.exception;

/**
 * Excel异常
 *
 * @author tuaobin 2023/6/16$ 10:54$
 */
public class SmartExcelError extends RuntimeException {

    public SmartExcelError() {
        super();
    }

    public SmartExcelError(String message) {
        super(message);
    }
    public SmartExcelError(String message,Throwable cause){
        super(message, cause);
    }
}
