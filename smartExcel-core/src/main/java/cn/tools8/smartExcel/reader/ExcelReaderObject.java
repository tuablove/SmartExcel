package cn.tools8.smartExcel.reader;

import cn.tools8.smartExcel.config.ExcelReaderConfig;
import cn.tools8.smartExcel.utils.ExcelReaderConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * excel读取类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderObject<T> extends AbstractExcelReader {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderObject.class);

    /**
     * 目标类
     */
    private Class<T> clazz;
    /**
     * 初始化
     *
     * @param clazz
     */
    public ExcelReaderObject(Class<T> clazz) {
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
    public List<T> read(InputStream is) throws IOException, InstantiationException, IllegalAccessException {
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
    public List<T> read(InputStream is, ExcelReaderConfig config) throws IOException, InstantiationException, IllegalAccessException {
        try {
            config = ExcelReaderConfigUtils.validateConfig(config);
            List<T> dataList = doRead(is, config,clazz);
            return dataList;
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
            IOUtils.close(is);
        }
    }





}
