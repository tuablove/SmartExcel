package cn.tools8.smartExcel.reader;

import cn.tools8.smartExcel.config.ExcelReaderWriteConfig;
import cn.tools8.smartExcel.utils.ExcelReaderConfigUtils;
import cn.tools8.smartExcel.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * excel读取追加写入类
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelReaderAppend<T> extends AbstractExcelReader {
    private static final Logger logger = LoggerFactory.getLogger(ExcelReaderAppend.class);

    /**
     * 目标类
     */
    private Class<T> clazz;


    /**
     * 初始化
     *
     * @param clazz
     */
    public ExcelReaderAppend(Class<T> clazz) {
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
    public List<T> read(InputStream is, ExcelReaderWriteConfig config) throws Exception {
        try {
            config= ExcelReaderConfigUtils.validateConfig(config);
            List<T> dataList = doRead(is, config,clazz);
            save(config);
            return dataList;
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.close(workbook);
            IOUtils.close(is);
        }
    }



    /**
     * 保存
     *
     * @param config
     * @throws Exception
     */
    private void save(ExcelReaderWriteConfig config) throws Exception {
        if (StringUtils.isBlank(config.getFilePath())) {
            return;
        }
        if (config.getPassword() != null && !config.getPassword().trim().equals("")) {
            try (POIFSFileSystem poifsFileSystem = new POIFSFileSystem();
                 OutputStream stream = new FileOutputStream(config.getFilePath())
            ) {
                EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
                Encryptor enc = info.getEncryptor();
                enc.confirmPassword(config.getPassword());
                try (OutputStream encryptOutPutStream = enc.getDataStream(poifsFileSystem)) {
                    workbook.write(encryptOutPutStream);
                    encryptOutPutStream.flush();
                }
                poifsFileSystem.writeFilesystem(stream);
                stream.flush();
            }
        } else {
            try (OutputStream stream = new FileOutputStream(config.getFilePath())) {
                workbook.write(stream);
                stream.flush();
            }
        }
    }


}
