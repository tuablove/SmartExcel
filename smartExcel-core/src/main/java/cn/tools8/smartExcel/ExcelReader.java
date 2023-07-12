package cn.tools8.smartExcel;

import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.reader.ExcelReaderMap;
import cn.tools8.smartExcel.reader.ExcelReaderObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * excel读取类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReader<T> extends AbstractExcel {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    /**
     * 目标类
     */
    private Class<T> clazz;

    /**
     * 初始化
     *
     * @param clazz
     */
    public ExcelReader(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 读取excel
     *
     * @param is excel文件数据流
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<T> read(InputStream is) throws Exception{
        return read(is, null);
    }

    /**
     * 读取excel
     *
     * @param is     excel文件数据流
     * @param config 读取文件配置
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<T> read(InputStream is, ExcelReaderConfig config) throws Exception {
        if (Map.class.isAssignableFrom(clazz)) {
            ExcelReaderMap readerMap = new ExcelReaderMap();
            return (List<T>) readerMap.read(is, config);
        } else {
            ExcelReaderObject<T> object = new ExcelReaderObject<>(clazz);
            return object.read(is, config);
        }
    }

}
