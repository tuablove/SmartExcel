package cn.tools8.convert.exception;

/**
 * 不支持的转换类型
 *
 * @author tuaobin 2023/6/16$ 10:54$
 */
public class UnsupportedClassTypeError extends RuntimeException {

    public UnsupportedClassTypeError() {
        super();
    }

    public UnsupportedClassTypeError(String message) {
        super(message);
    }
}
