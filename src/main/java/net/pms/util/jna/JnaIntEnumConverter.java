package net.pms.util.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;

/**
 * Performs conversion between {@link JnaIntEnum} and native enums.
 *
 * @author Nadahar
 */

public class JnaIntEnumConverter implements TypeConverter{

    @Override
	public Object fromNative(Object input, FromNativeContext context) {
    	if (!JnaIntEnum.class.isAssignableFrom(context.getTargetType())) {
            throw new IllegalStateException("JnaIntEnumConverter can only convert objects implementing JnaIntEnum");
        }
		@SuppressWarnings("rawtypes")
		Class targetClass = context.getTargetType();
        Object[] enumValues = targetClass.getEnumConstants();
        return ((JnaIntEnum<?>) enumValues[0]).typeForValue((int) input);
    }

    @Override
	public Class<Integer> nativeType() {
        return Integer.class;
    }

    @Override
	public Integer toNative(Object input, ToNativeContext context) {
        return ((JnaIntEnum<?>) input).getValue();
    }
}
