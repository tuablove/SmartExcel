package cn.tools8.smartExcel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * 关闭对象流
 * @author tuaobin 2023/6/19$ 13:39$
 */
public class IOUtils {
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    /**
     * 关闭对象流
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
                logger.error("closeable object close error", ignore);
            }
        }
    }
}
