package cn.tools8.smartExcelDemo.handler;

import cn.tools8.smartExcel.entity.CellOriginData;
import cn.tools8.smartExcel.handler.IWriteValueConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author tuaobin 2023/9/15$ 17:13$
 */
public class DateWriteConverter implements IWriteValueConverter {

    @Override
    public Object convert(CellOriginData cellValue) {
        if(cellValue.getValue()!=null){
            DateFormat format=new SimpleDateFormat("yyyyMMdd");
            return format.format(cellValue.getValue());
        }
        return cellValue.getValue();
    }
}
