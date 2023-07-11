package cn.tools8.smartExcel.manager.validator;

import org.hibernate.validator.internal.engine.MessageInterpolatorContext;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * CONTEXT的包装类
 * @author tuaobin 2023/7/11$ 15:45$
 */
public class PropertyMessageInterpolatorContext extends MessageInterpolatorContext {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private final Map<String, Object> messageParameters;
    private final Map<String, Object> expressionVariables;

    public PropertyMessageInterpolatorContext(ConstraintDescriptor<?> constraintDescriptor, Object validatedValue, Class<?> rootBeanType, Path propertyPath, Map<String, Object> messageParameters, Map<String, Object> expressionVariables) {
        super(constraintDescriptor, validatedValue, rootBeanType, propertyPath, messageParameters, expressionVariables);
        this.messageParameters = messageParameters;
        this.expressionVariables = expressionVariables;
    }

    public Map<String, Object> getMessageParameters() {
        return this.messageParameters;
    }

    public Map<String, Object> getExpressionVariables() {
        return this.expressionVariables;
    }

}
