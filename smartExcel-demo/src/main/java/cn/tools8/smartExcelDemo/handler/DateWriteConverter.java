package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.handler.IWriteValueConverter;
import org.apache.poi.ss.usermodel.Cell;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author tuaobin 2023/9/15$ 17:13$
 */
public class DateWriteConverter implements IWriteValueConverter {
    @Override
    public Object convert(Cell cell, Object cellValue, Class<?> valueType) {
        if(cellValue!=null){
            DateFormat format=new SimpleDateFormat("yyyyMMdd");
            return format.format(cellValue);
        }
        return cellValue;
    }
}
