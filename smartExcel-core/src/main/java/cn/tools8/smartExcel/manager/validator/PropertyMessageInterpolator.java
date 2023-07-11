package cn.tools8.smartExcel.manager.validator;

import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

import javax.validation.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 属性名获取
 *
 * @author tuaobin 2023/7/11$ 10:09$
 */
public class PropertyMessageInterpolator extends ResourceBundleMessageInterpolator {
    private final String NAME = "name";
    private PropertyNameObtainHandler propertyNameObtainHandler;

    public PropertyMessageInterpolator(PropertyNameObtainHandler propertyNameObtainHandler) {
        this.propertyNameObtainHandler = propertyNameObtainHandler;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        Context wrapContext = wrap(context);
        return super.interpolate(messageTemplate, wrapContext);
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        Context wrapContext = wrap(context);
        return super.interpolate(messageTemplate, wrapContext, locale);
    }

    private Context wrap(Context context) {
        if (context instanceof HibernateMessageInterpolatorContext) {
            HibernateMessageInterpolatorContext interpolatorContext = context.unwrap(HibernateMessageInterpolatorContext.class);
            Path propertyPath = interpolatorContext.getPropertyPath();
            String property = propertyPath.toString();
            Map<String, Object> messageParameters = new HashMap<>();
            messageParameters.putAll(interpolatorContext.getMessageParameters());
            Map<String, Object> expressionVariables = new HashMap<>();
            expressionVariables.putAll(interpolatorContext.getExpressionVariables());
            if (property != null && !property.trim().equals("") && propertyNameObtainHandler != null) {
                String propertyName = propertyNameObtainHandler.obtain(property);
                messageParameters.put(NAME, propertyName);
                expressionVariables.put(NAME, propertyName);
            }
            return new PropertyMessageInterpolatorContext(context.getConstraintDescriptor(), context.getValidatedValue(), ((HibernateMessageInterpolatorContext) context).getRootBeanType(), ((HibernateMessageInterpolatorContext) context).getPropertyPath(), messageParameters, expressionVariables);
        }
        return context;
    }

    /**
     * 属性名称获取函数
     */
    public interface PropertyNameObtainHandler {
        /**
         * 获取属性的名称
         *
         * @param property
         * @return
         */
        String obtain(String property);
    }
}
