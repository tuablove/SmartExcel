package cn.tools8.smartExcel.exception;

/**
 * Class<? extends WriteDataBase> 字段属性重复
 *
 * @author tuaobin 2023/6/16$ 10:54$
 */
public class DataFieldRepeatError extends SmartExcelError {

    public DataFieldRepeatError() {
        super();
    }

    public DataFieldRepeatError(String message) {
        super(message);
    }
}
