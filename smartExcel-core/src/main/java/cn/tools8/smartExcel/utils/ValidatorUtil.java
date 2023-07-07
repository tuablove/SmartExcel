package cn.tools8.smartExcel.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.*;

/**
 * 对象验证
 *
 * @author tuaobin 2023/7/7$ 15:22$
 */
public abstract class ValidatorUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 验证
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<String>> validate(T obj) {
        return validate(obj, Default.class);
    }

    /**
     * 验证
     * @param obj
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<String>> validate(T obj, Class<?>... groups) {
        Map<String, List<String>> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj, groups);
        if (set != null && set.size() > 0) {
            errorMap = new HashMap<>();
            String property = null;
            for (ConstraintViolation<T> cv : set) {
                //这里循环获取错误信息，可以自定义格式
                property = cv.getPropertyPath().toString();
                List<String> errorList = errorMap.get(property);
                if (errorList == null) {
                    errorList = new ArrayList<>();
                }
                errorList.add(cv.getMessage());
                errorMap.put(property, errorList);
            }
        }
        return errorMap;
    }
}
