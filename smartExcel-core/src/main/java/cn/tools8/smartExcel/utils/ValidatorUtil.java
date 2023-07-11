package cn.tools8.smartExcel.utils;

import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import javax.validation.groups.Default;
import java.util.*;

/**
 * 对象验证
 *
 * @author tuaobin 2023/7/7$ 15:22$
 */
public abstract class ValidatorUtil {

    /**
     * 验证
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<String>> validate(T obj) {
        return validate(obj, null, Default.class);
    }
    /**
     * 验证
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<String>> validate(T obj, MessageInterpolator messageInterpolator) {
        return validate(obj, messageInterpolator, Default.class);
    }

    /**
     * 验证
     *
     * @param obj
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<String>> validate(T obj, MessageInterpolator messageInterpolator, Class<?>... groups) {
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory();) {
            ValidatorContext validatorContext = validatorFactory.usingContext();
            if (messageInterpolator != null) {
                validatorContext.messageInterpolator(messageInterpolator);
            }
            Validator validator = validatorContext.getValidator();
            Map<String, List<String>> errorMap = null;
            Set<ConstraintViolation<T>> set = validator.validate(obj, groups);
            if (set != null && set.size() > 0) {
                errorMap = new HashMap<>();
                String property = null;
                for (ConstraintViolation<T> cv : set) {
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
}
